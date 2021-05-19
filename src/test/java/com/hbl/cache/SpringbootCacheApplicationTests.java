package com.hbl.cache;

import com.hbl.cache.bean.Employee;
import com.hbl.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.xml.ws.Action;

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作k-v都是字符串的
    @Autowired
    RedisTemplate redisTemplate;//操作k-v都是对象的
    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;
    @Test
    void contextLoads() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee);
    }

    /**
     * String,List,Set,Hash,ZSet(有序集合)
     * stringRedisTemplate.opsForValue() [String]
     * stringRedisTemplate.opsForList()  [List]
     * stringRedisTemplate.opsForSet()   [Set]
     * stringRedisTemplate.opsForHash()  [Hash]
     * stringRedisTemplate.opsForZSet()  [ZSet]
     */
    @Test
    public void testRedis(){
        //给redis中保存数据
        //stringRedisTemplate.opsForValue().append("msg","hello");
        //stringRedisTemplate.opsForValue().get("msg");
        //stringRedisTemplate.opsForList().leftPush("mylist","1");
        //stringRedisTemplate.opsForList().leftPush("mylist","2");
        //如果保存对象，使用jdk序列化机制，将序列化后的数据保存到redis中
        //redisTemplate.opsForValue().set("emp-01",employeeMapper.getEmpById(1));
        //要将数据以json的方式保存
        //1.自己将对选哪个转为json
        //2.redisTemplate默认序列化规则.改变默认的序列化规则
        empRedisTemplate.opsForValue().set("emp-02",employeeMapper.getEmpById(1));
        System.out.println(">>>>>>"+empRedisTemplate.opsForValue().get("emp-02"));
    }

}
