## 分布式
需要系统之间配合才能完成整个业务逻辑，就是分布式系统。

## Maven除了依赖管理，项目构建功能之外，还能对工程进行聚合、继承、依赖

- 分布式架构，父工程应该是一个`pom`工程，其中定义依赖的Jar包的版本信息、Maven插件的版本信息。（只有版本信息，没有实际的Jar包/插件）

- 创建Common`Jar`工程，存放通用工具类

## 数据库
项目的实际运行如果是**高并发**的，对数据库的查询最好是**单表查询**，提高数据处理效率。
对数据库表进行**冗余存储**，解决表的关联需求。（业务量增大到一定程度后，需要对数据库进行**分库分表**，单表查询就很方便）

## 前端显示EasyUI

### datagrid

### 异步tree
树控件读取URL，**子节点的加载依赖于父节点的状态**。
- 当展开一个封闭的节点，如果节点没有加载子节点，它将会把节点id的值作为http请求参数并命名为`id`，通过URL发送到服务器上检索子节点。
- 响应结果是如下格式的json数据
```
[{
	"id": 1,
	"text": "Node 1",
	"state": "closed"
}
...
]
```
如果当前节点为父节点，state为`closed`；叶子节点则为`open`


## Dubbo管理服务层和表现层的通信


### Dubbo监控中心（war包）
Linux中，放到tomcat的webapps目录下

如果监控中心和注册中心在同一台服务器上，无需任何配置。如果不在同一台服务器，需要修改配置文件：
`tomcat安装目录/webapps/dubbo-admin/WEB-INF/dubbo.properties`


## Nginx做反向代理

## FastDFS存储图片、文件

## KindEditor替换为最新的版本，不然网页的图片上传按钮无法显示


## dubbo不支持 file,inputStream 这种文件、流的传输
- 直接在Controller中处理
- 改用hessian协议进行传输
参考：
- [文件上传](https://blog.csdn.net/qq315737546/article/details/52792037)
- [dubbo上传文件](https://www.jianshu.com/p/b0841d6fa830)

## 规格参数模板

### 规格参数列表
- 从`tb_item_param`中查询数据展示到jsp，单表查询，实现分页

### 实现规格参数模板的添加和删除

## 新增商品，展示规格参数模板，提交时，一并提交规格参数

## 完成具体商品的规格参数html页面展示编写

## 创建portal工程（门户网站）

- 根据首页轮播图的数据结构，设计一张表，进行增删改查管理；其他部分的展示内容同理
    - 存在问题
        - 首页信息大量堆积，发布显得异常繁琐沉重
        - 内容繁杂，管理效率低下
        - 需要技术人员之间配合完成
        - 改版工作量大，可维护性差
        
**使用内容管理系统解决上述问题**。


### 新增内容分类，需要返回主键id（自增），在mapper的xml文件中，insert的方法中添加如下
order="AFTER" ：插入之后

```xml
<insert id="insert" parameterType="com.taotao.pojo.TbContentCategory">
    <selectKey keyProperty="id" order="AFTER" resultType="long">
        SELECT LAST_INSERT_ID()
    </selectKey>
    ...
</insert>
```
	

