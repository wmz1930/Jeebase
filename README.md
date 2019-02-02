# Jeebase

#### 项目介绍

&nbsp;&nbsp;&nbsp;&nbsp;Jeebase是一款前后端分离的开源开发框架，基于springboot+vue（vue-element-admin）开发，二期会整合react前端框架Ant Design React。在实际应用中已经使用这套框架开发了CMS网站系统，社区论坛系统，微信小程序，微信服务号等，后面会逐步整理开源。
   本项目主要目的在于整合主流技术框架，寻找应用最佳项目实践方案，实现可直接使用的快速开发框架。

#### 软件架构

1. 使用Spring Boot + MyBatis + MyBatis-Plus + Shiro + Jwt + Druid + J2Cache + Ehcache + Redis + Vue2 + ElementUI + Swagger2等开源框架
2. 文件上传使用七牛云，发送短信使用阿里云
3. 权限设计使用RBAC模型，重写Shiro相关实现，结合Jwt实现前后端分离功能。
4. 使用J2Cache 实现二级缓存，便于小项目单机部署和大项目分布式部署之间的切换，当然主要是为了性能方面考虑。
5. 使用MyBatis-Plus实现后台带码的生成及简化相关sql代码的编写。
6. 前端使用开源前端框架[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，在其基础上实现了后台配置菜单及权限。

#### 功能说明

1. 前后端分离，菜单权限使用vue-element-admin原生方法和后台资源配置两种方案
2. 权限使用基于角色的权限控制RBAC模型，使用shiro+jwt的方案，可以控制到菜单，按钮。
3. 定时任务考虑到分布式部署，所以使用xxl-job
4. 系统日志使用分布式实时日志分析解决方案ELK部署架构

#### 项目界面

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

#### 分享交流

欢迎入群交流，QQ群： 121623237