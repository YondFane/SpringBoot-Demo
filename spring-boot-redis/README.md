# Spring Boot集成Redis

## 主要依赖

```html
<!--Spring Boot 1.5版本会使用的是spring-boot-starter-data-redis-->
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.5.6</version>
</dependency>
```

## 常用注解

### @Cacheable 缓存

### @CachePut 更新缓存

### @CacheEvict 删除缓存

| 属性名           | 解释                                                         |
| :--------------- | :----------------------------------------------------------- |
| value            | 缓存的名称。可定义多个（至少需要定义一个）                   |
| cacheNames       | 同value属性                                                  |
| keyGenerator     | key生成器。字符串为：beanName                                |
| key              | 缓存的 key。可使用SpEL。优先级大于keyGenerator               |
| cacheManager     | 缓存管理器。填写beanName                                     |
| cacheResolver    | 缓存处理器。填写beanName                                     |
| condition        | 缓存条件。若填写了，返回true才会执行此缓存。可使用SpEL       |
| unless           | 否定缓存。false就生效。可以写SpEL                            |
| sync             | true：所有相同key的同线程顺序执行。默认值是false             |
| allEntries       | 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存 |
| beforeInvocation | 是否在方法执行前就清空，缺省为 false，如果指定为 true        |

### @Caching

用于定制复杂的缓存规则,

### @CacheConfig

用于标注在类上，可以存放该类中所有缓存的公有属性，比如设置缓存的名字。

## Redis 的数据结构类型

Redis 可以存储键与5种不同数据结构类型之间的映射，这5种数据结构类型分别为String（字符串）、List（列表）、Set（集合）、Hash（散列）和 Zset（有序集合）。

下面来对这5种数据结构类型作简单的介绍：

| 结构类型 | 结构存储的值                                                 | 结构的读写能力                                               |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| String   | 可以是字符串、整数或者浮点数                                 | 对整个字符串或者字符串的其中一部分执行操作；对象和浮点数执行自增(increment)或者自减(decrement) |
| List     | 一个链表，链表上的每个节点都包含了一个字符串                 | 从链表的两端推入或者弹出元素；根据偏移量对链表进行修剪(trim)；读取单个或者多个元素；根据值来查找或者移除元素 |
| Set      | 包含字符串的无序收集器(unorderedcollection)，并且被包含的每个字符串都是独一无二的、各不相同 | 添加、获取、移除单个元素；检查一个元素是否存在于某个集合中；计算交集、并集、差集；从集合里卖弄随机获取元素 |
| Hash     | 包含键值对的无序散列表                                       | 添加、获取、移除单个键值对；获取所有键值对                   |
| Zset     | 字符串成员(member)与浮点数分值(score)之间的有序映射，元素的排列顺序由分值的大小决定 | 添加、获取、删除单个元素；根据分值范围(range)或者成员来获取元素 |