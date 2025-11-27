# wallet-platform-service

### Examples

-  query token info , aggregate both core info and project data
```request
curl --request GET \
  --url 'http://localhost:28081/api/core/token/token-info?tokenContractAddress=0xdAC17F958D2ee523a2206206994597C13D831ec7&chainId=1' \
  --header 'Accept: */*' \
  --header 'Accept-Encoding: gzip, deflate, br' \
  --header 'Connection: keep-alive' \
  --header 'User-Agent: PostmanRuntime-ApipostRuntime/1.1.0' \
  --header 'projectId: WLFI'
```
```response
{
	"chainId": 1,
	"address": "0xdAC17F958D2ee523a2206206994597C13D831ec7",
	"isNative": false,
	"name": "Tether USD",
	"displayName": "Tether USD",
	"symbol": "USDT",
	"logo": "usdt.png",
	"decimals": 6,
	"tokenPrice": "1.00",
	"priceChangeH24": "0.0001",
	"volumeH24": "123456789",
	"extInfo": {
		"coinType": "stable-coin",
		"safeTag": "safe"
	}
}
// extInfo : comes from project data

```
