## 全局公共参数
#### 全局Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 全局Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 全局Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 全局认证方式
```text
noauth
```
#### 全局预执行脚本
```javascript
暂无预执行脚本
```
#### 全局后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人
```text
暂无描述
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/Steam用户登录
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /usr/status/login.do

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
url | - | String | 是 | 回调地址
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/Steam添加机器人
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /api/v1/2504460/bot

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
user_account | - | String | 是 | steam 账号
api_id | - | String | 是 | 游戏id
play_name | - | String | 是 | 名称
user_psw | - | String | 是 | steam 密码
steam_id | - | String | 是 | steam id
shared_secret | - | String | 是 | steam秘钥
identity_secret | - | String | 是 | steam秘钥
bot_id | - | String | 是 | 机器编号
callBackUrl | - | String | 是 | -
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/Steam修改机器人
```text
暂无描述
```
#### 接口状态
> 开发中

#### 接口URL
> /api/v1/2504460/bot

#### 请求方式
> PUT

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
user_account | - | String | 是 | steam 账号
api_id | - | String | 是 | 游戏id
play_name | - | String | 是 | 名称
user_psw | - | String | 是 | steam 密码
steam_id | - | String | 是 | steam id
shared_secret | - | String | 是 | steam秘钥
identity_secret | - | String | 是 | steam秘钥
bot_id | - | String | 是 | 机器编号
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/Steam删除机器人
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /api/v1/2504460/bot

#### 请求方式
> DELETE

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
api_id | 2504460 | String | 是 | 游戏id
bot_id | 2 | String | 是 | 机器编号
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/发送交易报价
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /api/v1/2504460111/trade

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
api_id | 2504460 | String | 是 | 游戏id
you_send_inventory | {"assetid":[]} | String | 是 | 对方的参数商品
he_send_inventory | {"assetid":["4253138158345719263"]} | String | 是 | 已方参数商品
steam_id | 76561199008923168 | String | 是 | steamId
url | https://steamcommunity.com/tradeoffer/new/?partner=1048657440&token=BynV924D | String | 是 | 交易连接
bot_id | 1 | String | 是 | 机器人id
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/手动取消交易报价
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /api/v1/2504460111/trade

#### 请求方式
> DELETE

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
api_id | 2504460 | String | 是 | 游戏id
offer_id | 1 | String | 是 | 交易id
bot_id | 1 | String | 是 | 机器人id
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /steam 机器人/获取库存
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> /api/v1/2504460/inventory/1

#### 请求方式
> GET

#### Content-Type
> form-data

#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
