### steam-开箱回收模式介绍

解决csgo开箱网业务风险问题，完善用户取回体验流程，提升体验度方式，针对饰品交易平台开发对应接口的发货接口（以下以zbt举例，或者igxe等，使用机器人交易模 锚定货物 主动索取物品，通过如下步骤即可完成所有操作：

  1.开发机器人交易配置steam账户和密码 

  2.实现steam bot的自动化 索取 玩家物品的操作

  3.通过锚定货物 物品价值 实现站内上账

以下为饰品锚定的交易流程图:

![CSOL交易锚定流程图](https://raw.githubusercontent.com/Tanxing1024/steam-boot/main/doc/csol.png)

​	用户选择背包内的CSOL饰品在系统内进行锚定，整笔交易无需人工干预、所有操作为自动化。除开第三方平台提供steam饰品外，其中steam交易机器人是此次交易中的核心，用户发起后由内部系统向托管机器人索取用户CSOL饰品且发起steam交易、用户在steam中收到交易信息确认后即可完成锚定动作，确认锚定后系统根据业务规则选择饰品或内部代币进行补偿，从而完成整个锚定流程。



### 项目介绍及支持接口

项目主要包含机器人登陆、添加机器人、发送stem游戏饰品报价、steam报价自动确认、取消报价、获取双方库存、报价确认列表详情、根据交易链接获取steamId等接口，能够直接无需任何成本使用，具体文档地址《[点击查看文档](https://console-docs.apipost.cn/preview/27335e38170ec71b/c3556e44b622302f)》 。需要提前准备steam账号，解除steam手机app令牌再绑定需要等待7天才开放市场， 添加机器人时需要依赖令牌中的shared secret（共享密钥）identity secret（身份密钥） 用于令牌随机数生成和自动确认和发起交易，具体获取方法参考：《[点击查看教程](https://zhuanlan.zhihu.com/p/542180497)》，项目内提供SDA1.0 windows的软件包、参考提供的教程或者自行google，另外有其他steam接口需求可提Issues。



### 常见问题

1.steam目前有限流策略，在请求量较大的情况下部分接口会限流导致无法使用，解决方案为：实现外部代理IP池的方式进行处理。

2.如何获取steam账号的shared_secret和identity_secret参数，绑定SDA桌面验证器后会自动生成，在SDA根目录下会有maFiles文件夹，里面文件格式为 steamId.maFile文件，打开即可看到该参数，具体操作方式可google。

3.steam 账号 apiKey获取方式：https://steamcommunity.com/dev/apikey，点击后输入机器人部署的服务网址后生成即可。

4.steam最近调整了登陆策略，截至到现在java 还未适配登陆接口，暂采用nodejs 提供的登陆接口，克隆仓库后解压 node-cookies.zip，运行如下

```
npm install
```

运行：

```
node index.js 
```

建议后台运行，pm2或 supervisord 工具守护。



4.CSOl所有支持交易物品白名单在/csol饰品.xls 中，请注意辨别真伪，名单外的饰品不符合规则。
