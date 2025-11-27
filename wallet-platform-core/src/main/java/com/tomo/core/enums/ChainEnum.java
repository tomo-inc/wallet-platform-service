package com.tomo.core.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
public enum ChainEnum {
    BTC(0L, 0L, 0L, "Bitcoin", "BITCOIN", true, false, 1, 2, "btc"),
    ETH(100L, 1L, 1L, "Ethereum", "ETH", true, true, 1, 1, "evm"),
    BSC(5600L, 56L, 56L, "BNB Chain", "BSC", true, true, 1, 1, "evm"),
    ARBITRUM(4216100L, 42161L, 42161L, "Arbitrum One", "ARBITRUM", true, true, 1, 2, "evm"),
    OPTIMISM(1000L, 10L, 10L, "Optimism", "OPTIMISM", true, true, 1, 2, "evm"),
    POLYGON(13700L, 137L, 137L, "Polygon", "POLYGON_POS", true, true, 1, 2, "evm"),
    BASE(845300L, 8453L, 8453L, "Base", "BASE", true, true, 1, 1, "evm"),
    SOL(50100L, 501L, 501L, "Solana", "SOLANA", true, false, 2, 1, "solana"),
    TRON(1948400L, 19484L, 195L, "Tron", "TRON", true, false, 1, 2, "tron"),
    DOGE(300L, 3L, 3L, "Dogecoin", "DOGECOIN", true, false, 1, 1, "doge"),
    DOGE_TESTNET(628197100L, 6281971L, 302L, "DogeOS Testnet", "DOGE_TESTNET", false, false, 2, 2, "doge"),

    UNKNOWN(-9999L, -9999L, -9999L, "Unknown", "Unknown", false, false, 1, 4, "evm");

    private final Long chainIndex;
    private final Long chainId;
    private final Long okxChainId;
    private final String chainName;
    private final String oldChainName;
    private final Boolean okxSupport;
    private final Boolean isEVM;
    private final Integer type;  // 1: mainnet   2: testnet   3: devnet
    private final Integer level;
    private final String addressType;

    ChainEnum(final Long chainIndex, final Long chainId, final Long okxChainId, final String chainName,
              String oldChainName,
              final Boolean okxSupport,
              final Boolean isEVM, final Integer type, final Integer level, String addressType) {
        this.chainIndex = chainIndex;
        this.chainId = chainId;
        this.okxChainId = okxChainId;
        this.chainName = chainName;
        this.oldChainName = oldChainName;
        this.okxSupport = okxSupport;
        this.isEVM = isEVM;
        this.type = type;
        this.level = level;
        this.addressType = addressType;
    }




    public static ChainEnum getChanByIndex(final Long chainIndex) {
        Long normalizedIndex = normalizeChainIndex(chainIndex);
        if (normalizedIndex == null) {
            throw new RuntimeException("chainIndex:" + chainIndex + " not match any value");
        }
        ChainEnum[] values = ChainEnum.values();
        for (ChainEnum value : values) {
            if (Objects.equals(value.getChainIndex(), normalizedIndex)) {
                return value;
            }
        }
        throw new RuntimeException("chainIndex:" + chainIndex + " not match any value");
    }

    public static ChainEnum getChainById(final Long chainId) {
        ChainEnum[] values = ChainEnum.values();
        for (ChainEnum value : values) {
            if (Objects.equals(value.getChainId(), chainId)) {
                return value;
            }
        }
        throw new RuntimeException("chainId:" + chainId + " not match any value");
    }

    public static ChainEnum getByChainIndex(Long chainIndex) {
        Long normalizedIndex = normalizeChainIndex(chainIndex);
        if (normalizedIndex == null) return null;
        for (ChainEnum chainEnum : ChainEnum.values()) {
            if (chainEnum.getChainIndex().equals(normalizedIndex)) {
                return chainEnum;
            }
        }
        return null;
    }

    public static ChainEnum getByChainId(final Long chainId) {
        for (ChainEnum chainEnum : ChainEnum.values()) {
            if (chainEnum.getChainId().equals(chainId)) {
                return chainEnum;
            }
        }
        return null;
    }

    public static ChainEnum getByOkxChainId(Long okxChainId) {
        for (ChainEnum chainEnum : ChainEnum.values()) {
            if (chainEnum.getOkxChainId() == null) {
                continue;
            }
            if (chainEnum.getOkxChainId().equals(okxChainId)) {
                return chainEnum;
            }
        }
        return UNKNOWN;
    }


    private static Long normalizeChainIndex(Long chainIndex) {
        return chainIndex;
    }
}
