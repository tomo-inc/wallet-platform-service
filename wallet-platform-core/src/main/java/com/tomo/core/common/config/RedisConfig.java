package com.tomo.core.common.config;


import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.metrics.MicrometerCommandLatencyRecorder;
import io.lettuce.core.metrics.MicrometerOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

@EnableCaching
@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.mode:standalone}")
    private String redisMode; // Redis running mode: cluster or standalone

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private Integer port;

    @Value("${spring.data.redis.username:default}")
    private String username;

    @Value("${spring.data.redis.password}")
    private String pwd;

    @Value("${spring.data.redis.timeout:15000}")
    private Integer redisTimeout;

    @Value("${spring.data.redis.database:0}")
    private Integer database;

    @Value("${spring.data.redis.ssl.enabled:false}")
    private boolean sslEnabled;

    @Value("${spring.data.redis.cluster.nodes:}")
    private String clusterNodes; // Cluster node list, format: host1:port1,host2:port2

    @Value("${spring.data.redis.cluster.max-redirects:5}")
    private Integer maxRedirects; // Maximum number of redirects, default 5

    @Value("${spring.data.redis.cluster.refresh-period:30}")
    private Integer refreshPeriod; // Topology refresh period, default 30 seconds

    @Value("${spring.data.redis.cluster.refresh-adaptive:true}")
    private Boolean refreshAdaptive; // Enable adaptive refresh, default true

    public static final String REDIS_MODE_CLUSTER = "cluster";

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources(MeterRegistry meterRegistry) {
        return DefaultClientResources.builder()
                .ioThreadPoolSize(10)
                .computationThreadPoolSize(5)
                .commandLatencyRecorder(
                        new MicrometerCommandLatencyRecorder(
                                meterRegistry, MicrometerOptions.builder().histogram(true).build()))
                .build();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        if (REDIS_MODE_CLUSTER.equals(redisMode)) {
            return createClusterConnectionFactory(clientResources);
        } else {
            return createStandaloneConnectionFactory(clientResources);
        }
    }

    /**
     * Create cluster mode connection factory
     */
    private LettuceConnectionFactory createClusterConnectionFactory(ClientResources clientResources) {
        // Cluster mode
        ClusterTopologyRefreshOptions topologyRefreshOptions =
                ClusterTopologyRefreshOptions.builder()
                        .enablePeriodicRefresh(
                                Duration.ofSeconds(refreshPeriod)) // Enable periodic topology refresh
                        .enableAdaptiveRefreshTrigger(
                                refreshAdaptive
                                        ? // Enable adaptive refresh trigger
                                        ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT
                                        : ClusterTopologyRefreshOptions.RefreshTrigger.ASK_REDIRECT)
                        .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30)) // Adaptive refresh timeout
                        .refreshPeriod(Duration.ofSeconds(refreshPeriod)) // Refresh period
                        .build();

        ClusterClientOptions clientOptions =
                ClusterClientOptions.builder()
                        .topologyRefreshOptions(topologyRefreshOptions) // Topology refresh options
                        .autoReconnect(true) // Auto-reconnect
                        .cancelCommandsOnReconnectFailure(true) // Cancel commands on reconnect failure
                        .build();

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .readFrom(ReadFrom.REPLICA_PREFERRED) // Prioritize reading from replicas
                        .commandTimeout(Duration.ofMillis(redisTimeout)) // Command timeout
                        .clientOptions(clientOptions) // Client options
                        .clientResources(clientResources) // Client resources
                        .useSsl() // Use SSL encryption
                        .build();

        // Redis connection configuration
        RedisClusterConfiguration clusterConfiguration =
                new RedisClusterConfiguration(Arrays.asList(host + ":" + port));
        clusterConfiguration.setUsername(username);
        clusterConfiguration.setPassword(pwd);
        return new LettuceConnectionFactory(clusterConfiguration, clientConfig);
    }

    /**
     * Create standalone mode connection factory
     */
    private LettuceConnectionFactory createStandaloneConnectionFactory(
            ClientResources clientResources) {
        // Standalone mode
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(pwd);
        redisStandaloneConfiguration.setUsername(username);
        redisStandaloneConfiguration.setDatabase(database);

        if (sslEnabled) {
            LettuceClientConfiguration clientConfig =
                    LettuceClientConfiguration.builder().clientResources(clientResources).useSsl().build();
            return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        } else {
            LettuceClientConfiguration clientConfig =
                    LettuceClientConfiguration.builder().clientResources(clientResources).build();
            return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        }
    }

    @Primary
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
