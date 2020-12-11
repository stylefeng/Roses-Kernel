## Roses核心包

基于《Java开发手册（嵩山版）》

### 规则1 模块的分类（a类，d类，o类，s类，p类）

> a类排名第一，Advanced，为全模块的规则，本公司所有的代码都需要遵守的规则，包含枚举，异常，基础类等

> d类排名第二，Development，给开发人员用的快速开发工具，方便快速开发，例如日志，邮件，短信，缓存等

> o类排名第三，Operations，偏运维类的封装，例如监控，调用链记录，内网转发模块

> s类排名第四，Service，偏应用功能的封装，例如用户管理，角色管理，公司管理，每个模块是一个独立的业务

> p类排名第五，Pattern，设计模式或业务解决方案，例如高并发的解决方案，海量数据存储方案等

### 规则2 模块的建设标准

> 2.1 模块建立的基本思想是封装重用的代码，提高开发效率

> 2.2 由团队核心成员评估批准后进行模块的编写，模块的编写要遵守第3条规定

### 规则3 模块设计思想

> 3.1 每个模块内部分三类子模块

分别是api、sdk、business，api为对其他模块暴露的接口，sdk是对核心功能的封装，business是带业务逻辑的封装

以短信模块kernel-d-sms为例，sms-api模块是接口模块，是短信功能提供的所有接口。

sms-sdk-aliyun模块是阿里云短信的sdk封装。

sms-sdk-tencent模块是腾讯云短信的sdk封装。

sms-business-validation模块是带短信验证功能（业务）的模块。

api、sdk、business为三类模块，不是三个，一般api模块仅一个，sdk和business类模块可以无限拓展。

> 3.2 依赖接口不依赖实现

模块与模块之间的调用，通过api模块来调用（例如sms-api），而不直接依赖他的实现（sms-sdk或sms-business），具体的实现由business模块决定或者由具体项目决定。

> 3.3 每个模块要详细编写readme

每个kernel模块要编写对应的readme文档

每个kernel的子模块也要写清楚readme文档

> 3.4 所有api的实现都装入spring容器中，使用api时通过@Resource注入api

同一个项目，一个api的实现可以有两个，需要通过@Resource(name = "xxx")指定资源的名字。

### 规则4 模块中任何类均可拓展

利用@Primary注解来替换已经装载的spring容器中的bean

## 规则5 每个模块要有一个常量类

常量类用来存放模块名称和异常枚举的步进值，如果本模块异常较多，可以存放多个步进值

```java
public interface RuleConstants {

    /**
     * 规则模块的名称
     */
    String RULE_MODULE_NAME = "kernel-a-rule";

    /**
     * 异常枚举的步进值
     */
    String RULE_EXCEPTION_STEP_CODE = "00";

}
```

## 规则6 每个模块要有一个异常类

异常类要集成ServiceException

```java
public class DaoException extends ServiceException {

    public DaoException(AbstractExceptionEnum exception) {
        super(DbConstants.DB_MODULE_NAME, exception);
    }

}
```

## 规则7 强依赖

项目基于spring boot架构，所以对spring boot强依赖，另外对hutool工具类，lombok，fastjson强依赖，其他框架不强依赖

## 规则8 expander包是对配置表的拓展

kernel-d-config模块只负责对系统配置的初始化，新增，删除等操作，不进行对某个具体配置的维护，各个模块需要配置拓展属性时，在各个模块的api模块建立expander包维护

## 规则9 business可以依赖sdk层，sdk层可依赖api层，反之不行

## 规则10 高模块可依赖低模块的api，反之不行

s类的api模块可以依赖d类的api，反之不行，防止出现互相依赖（循环依赖）的情况

## 规则11 Bean的装配，尽量在类的构造函数，不要在类的内部用@Resource或者@Autowired

构造函数装配更灵活，如果直接用@Resource则会交给spring去装配，spring会去找到容器中的相关bean，不如手动的灵活

多出现在装配的是接口的情况，如果接口有多个实现，很明显用构造函数去装配更合适

## 规则12 pojo的分包结构

pojo下可以分为request（控制器层请求参数的封装），response（控制器层响应参数的封装），param（其他类下参数的封装）

其中request包下的类以Request结尾，response包下的类以Response结尾，param包下的类以Param结尾

request包下的类一般会加上参数校验注解，参数校验用的hibernate validator注解

一般情况，直接用实体返回，减少一些pojo的书写，复杂的返回对象还是要单独封装pojo

## 规则13 表的设计

表名不要用缩写，用全拼单词

排序字段用decimal带两位小数点，这样往里边插入数据的时候，不用改别人的排序，就可以通过小数来插入了，如果两位不够用的时候，还可以扩充为3位等等

表设计中，不要用mysql的关键字作为字段和表名

## 规则14 pom中的注释要写清楚，为什么引用这个模块写到每个依赖上边

## 规则15 像小白一样思考，像专家一样行动