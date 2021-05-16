一、springboot与缓存
1.JSR107
	java caching定义了5个核心基础：CashingProvider,CacheManager,Cache,Entry,Expiry
	CashingProvider:定义了创建，配置，获取，管理和控制多个CacheManger,一个应用可以在运行期间访问多个CachingProvider
	CacheManager:定义了创建，配置，获取，管理和控制多个唯一命名的Cache,这些Cache存在与CacheManager的上下文中，一个CacheManager仅被一个CachingProcider所拥有
	Cache:是一个类似Map的数据结构并临时存储以key为索引的值，一个Cache仅被一个CacheManager所拥有
	Entry:是一个存储在Cache中的key-value对
	Expiry:每个存储在Cache中的条目有一个定义的有效期。缓存有效期可以通过ExpiryPolicy设置
2.Spring缓存抽象
    搭建一个springboot+mybatis的基本环境为学习做准备
    @Cacheable开启缓存步骤：
        (1).开启基于注解的缓存-标注缓存注解:@EnableCaching
            @Cacheable:将方法的运行结果进行缓存,CacheManager管理多个Cache组件的，对缓存真正的CRUD操作在Cache组件在中，每个缓存组件有自己家的名字
                a.几个属性
                    cacheNames/value:指定缓存组件的名字
                    key：缓存数据使用的key,默认是使用敢发参数的值：1-方法的返回值（key-value）
                        编写SpEL:#id;参数id的值 #a0 #p0 #root.args[0]
                    keyGenerator：key的生成器，可以自己指定key的生成器的组件id
                         key和keyGenerator二选一
                    cacheManager：指定缓存管理器，或者cacheResolver指定获取解析器
                    condition：指定符合条件的情况下才缓存
                    unless否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存，可以获取到结果进行判断
                        ,unless = "#result == null"
                    sync：是否使用异步模式
                b.原理
                    1.自动配置类：CacheAutoConfiguration
                    2.缓存的配置类，有很多
                    3.默认SimpleCacheConfiguration生效
                    4.给容器中注册了一个CacheManager：ConcurrentMapCacheManager，可以获取和创建一个ConcurrentMapCache类型的缓存组件，作用是将数据保存在ConcurrentMap中

                    @Cacheable运行流程:
                    1.方法运行之前，先去查询Cache，按照CacheName指定的名字获取
                        (CacheManager先获取响应的缓存)，第一次获取缓存如果没有Cache组件会自动创建
                    2.去Cache中查找缓存内容，使用一个key,默认使用方法参数
                        key是按照某种策略生产，默认是使用keyGenerator,默认使用simpleKeyGenerator生成
                        simpleKeyGenerator生产key的默认策略：
                            如果没有参数:key=new SimpleKey();
                            如果有一个参数：key=参数值
                            如果有多个参数：key=new SimpleKey(params);
                    3.没有查到缓存就调用目标方法
                    4.将目标方法返回的结果放进缓存中
                    @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，如果没有目标方法就将结果放入缓存
            @CachePut:即调用方法有更新缓存数据：修改了数据库的某个数据，同时更新缓存
                1.先调用目标方法
                2.将目标方法的结果缓存起来
                如果不放key = "#result.id"，测试的时候先查询1号员工，再update员工，再去查询结果没有变化，需要传入需要update的id去缓存中更新
                @Cacheable中不能使用#result,因为方法还没运行之前就已经得到了缓存的key
            @CacheEvict：缓存清除
                 通过key指定清除数据
                 allEntries = true:缓存中emp所有的数据都删除
                 beforeInvocation = true缓存的清除是否在方法之前执行，默认是方法之后执行，如果出现异常缓存就不会被清除
            @Caching:定义复杂的缓存规则
            @CacheConfig
                @CacheConfig(cacheNames="emp")//抽取缓存的公共配置
3.整合redis作为缓存
    Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件


二、springboot与消息


三、springboot与任务

四、springboot与安全