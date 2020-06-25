# community

### 🎓开发日志

#### 6.18 用户登陆注册后台功能完成

用户注册主要是根据邮箱注册，注册成功之后会发激活邮件，用户登陆邮箱激活，才能正常使用

登陆根据邮箱，密码进行登陆，验证码采用kaptcha包

#### 6.19 用户登陆注册前端页面提示，密码校验

注册登陆时候出错，页面会显示出错信息，密码必须是大于6位且小于15位。

#### 6.22 用户登陆凭证，退出功能，登陆拦截器

用户登陆的时候同时把凭证添加至cookie ，下一次访问的时候直接登陆。

退出功能，删除cookie，假删除，把status字段变成0

拦截器，用户登陆会把信息放在cookie里面，我下一次访问网页的时候，会通过拦截器的prehandler方法，先去查看cookie里面有没有ticket信息，有的话根据ticket信息获取userId，获取用户的信息。前端页面显示会根据cookie里面有没有用户，显示登陆注册按钮还是用户具体的信息。

#### 6.23 发表帖子

发帖之前会查看有没有登陆，没有登陆的情况下，点击发帖会直接跳转登陆页面

如果登陆了会直接跳转发帖页面

用户填写信息之后点击提交发表成功。

#### 6.25 页面查询 

发表帖子之后，返回首页，首页会显示所有的帖子。后端逻辑 和 前端页面显示

按照不同的帖子类型进行显示，周榜，月榜，最新，加精，热评。

### 🎓帮助文档

[1.0 Spring Boot整合 kaptcha 实现验证码的功能 ](https://blog.csdn.net/weixin_34221276/article/details/89657736)

[2.0 hutool官方文档](https://www.hutool.cn/docs/#/)

[3.0 Spring Boot 整合 Interceptor 实现拦截器的功能](https://blog.csdn.net/u012326462/article/details/80509718)

[4.0 集成富文本编辑器](https://www.layui.com/doc/modules/layedit.html)

[5.0 开发环境不出错，打成jar包发布出错](https://www.cnblogs.com/ming-blogs/archive/2019/01/18/10288579.html)

[6.0 CSS中 Important设置优先级](http://www.w3chtml.com/css3/rules/!important.html)