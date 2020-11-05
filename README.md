# Jeebase

技术交流群 [ qq群 121623237]

<a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=dp7qMx8ZbZGuZ8QtwnKXLzq8kttbzI1q&jump_from=webapi"><img border="0" src="http://img.jeebase.com/group.png" alt="JeeBase技术交流群" title="JeeBase技术交流群"></a>

#### 项目介绍

&nbsp;&nbsp;&nbsp;&nbsp;Jeebase是一款前后端分离的开源开发框架，基于springboot+vue（vue-element-admin/Ant Design Pro Vue）开发，一套SpringBoot后台，两套前端页面，可以自由选择基于ElementUI或者AntDesign的前端界面。二期会整合react前端框架（Ant Design React）。在实际应用中已经使用这套框架开发了CMS网站系统，社区论坛系统，微信小程序，微信服务号等，后面会逐步整理开源。
   本项目主要目的在于整合主流技术框架，寻找应用最佳项目实践方案，实现可直接使用的快速开发框架。

#### 软件架构

1. 使用Spring Boot + MyBatis + MyBatis-Plus + Shiro + Jwt + Druid + J2Cache + Ehcache + Redis + Vue2 + ElementUI/AntDesign + Swagger2等开源框架
2. 文件上传使用七牛云，发送短信使用阿里云
3. 权限设计使用RBAC模型，重写Shiro相关实现，结合Jwt实现前后端分离功能。
4. 使用J2Cache 实现二级缓存，便于小项目单机部署和大项目分布式部署之间的切换，当然主要是为了性能方面考虑。
5. 使用MyBatis-Plus实现后台带码的生成及简化相关sql代码的编写。
6. 前端使用开源前端框架[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)和[Ant Design Pro Vue](https://pro.loacg.com)。

#### 功能说明

1. 前后端分离，菜单权限使用vue-element-admin/Ant Design Pro Vue原生方法和后台资源配置两种方案
2. 支持权限控制到按钮，命名规则参考shiro权限标签
3. 权限使用基于角色的权限控制RBAC模型，使用shiro+jwt的方案，可以控制到菜单，按钮。
4. 支持数据权限控制，使用AOP+mybatis插件动态修改执行语句
5. 定时任务考虑到分布式部署，所以使用xxl-job
6. 系统日志使用分布式实时日志分析解决方案ELK部署架构

#### 目录说明

1. jeebase-service Java后台服务代码
2. jeebase-portal   vue-element-admin 4.x版本的后台管理前端页面代码
3. jeebase-portal-ant-design  vue-element-admin Ant Design Pro Vue版本的后台管理前端页面代码
4. jeebase-wechat  微信小程序及微信服务号前端页面代码
5. jeebase-wechat/jeebase-vant  以有赞vant为前端框架的微信服务号前端页面代码
6. jeebase-wechat/jeebase-color  以color为前端框架的微信小程序前端页面代码（因有赞vant的小程序前端框架侵入性太强，故选择ColorUI为前端框架）

#### 项目界面
一、微信小程序界面
<br/>
首页
<br>
![首页](https://images.gitee.com/uploads/images/2019/0611/210043_96850bbe_378796.png "1.png")
<br>
商品分类页
<br>
![分类页](https://images.gitee.com/uploads/images/2019/0611/210120_52a5640c_378796.png "2.png")
<br/>
购物车
<br>
![购物车](https://images.gitee.com/uploads/images/2019/0611/210145_69a32841_378796.png "3.png")
<br/>
二、微信服务号界面
<br>
首页
<br>
![首页](https://images.gitee.com/uploads/images/2019/0611/215848_ef0773ba_378796.png "1.png")
<br>
三、后台管理界面
登录
![登录](https://images.gitee.com/uploads/images/2019/0202/165652_6b0a5bf7_378796.png "登录.png")
首页
![首页](https://images.gitee.com/uploads/images/2019/0201/144428_de333e74_378796.png "首页.png")
用户管理
![用户管理](https://images.gitee.com/uploads/images/2019/0201/144648_be9a0024_378796.png "用户管理.png")
角色管理
![角色管理](https://images.gitee.com/uploads/images/2019/0201/144800_a68b13cd_378796.png "角色管理.png")
资源管理
![资源管理](https://images.gitee.com/uploads/images/2019/0201/145256_66795ea5_378796.png "资源管理.png")
组织管理
![组织管理](https://images.gitee.com/uploads/images/2019/0201/145641_9dd38ade_378796.png "组织管理.png")
数据字典
![数据字典](https://images.gitee.com/uploads/images/2019/0201/145759_ad60d277_378796.png "数据字典.png")
操作日志
![操作日志](https://images.gitee.com/uploads/images/2019/0201/145854_25f01616_378796.png "操作日志.png")

