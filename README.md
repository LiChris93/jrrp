# jrrp
<h1>这只是我早期写的屎山，Codefactor B,没有任何参考价值</h1>

A bukkit plugin for Minecraft Servers about how lucky a player is.

插件名：jrrp（今日人品的拼音缩写）

功能：使机器人在QQ群内发送一名玩家的“人品值”（其实就是随机数生成），以此达到获取今日人品的效果。


命令：（MC内）

/jrrp reload                 重载配置

/jrrp addadmin <qqid>          加管理

/jrrp deladmin <qqid>          减管理

/jrrp isadmin <qqid>    判断是否是管理

(均需要OP权限)

添加管理之后在QQ群内可以发送需要管理权限的消息

QQ群内命令（以默认为准，这些命令可在config.yml中更改）：

.jrrp ----核心命令，发送此消息后机器人将回复一条关于发送者今日人品的信息

.jrrp-clear ----重置今日人品集合的消息（相当于清空数据）

.jrrp-send-map ----发送今日人品集合内所有内容的消息（内含发送者qq号，发送日期及那一天的人品值）

（后两者均需要在config.yml中添加管理权限才可以使用）



谢谢各位对一个新手developer的支持

Thanks

