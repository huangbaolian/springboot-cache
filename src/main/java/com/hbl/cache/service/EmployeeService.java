package com.hbl.cache.service;

import com.hbl.cache.bean.Employee;
import com.hbl.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames="emp")//抽取缓存的公共配置
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存
     * CacheManager管理多个Cache组件的，对缓存真正的CRUD操作在Cache组件在中，每个缓存组件有自己家的名字
     * 几个属性
     *  cacheNames/value:指定缓存组件的名字
     *  key：缓存数据使用的key,默认是使用敢发参数的值：1-方法的返回值（key-value）
     *      编写SpEL:#id;参数id的值 #a0 #p0 #root.args[0]
     *  keyGenerator：key的生成器，可以自己指定key的生成器的组件id
     *      key和keyGenerator二选一
     *  cacheManager：指定缓存管理器，或者cacheResolver指定获取解析器
     *  condition：指定符合条件的情况下才缓存
     *  unless否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存，可以获取到结果进行判断
     *      ,unless = "#result == null"
     *  sync：是否使用异步模式
     * @param id
     * @return
     */
    @Cacheable(/*cacheNames = {"emp"}*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee employee = employeeMapper.getEmpById(id);
        return employee;
    }

    //@CachePut:即调用方法有更新缓存数据：修改了数据库的某个数据，同时更新缓存
    //1.先调用目标方法
    //2.将目标方法的结果缓存起来
    //如果不放key = "#result.id"，测试的时候先查询1号员工，再update员工，再去查询结果没有变化，需要传入需要update的id去缓存中更新
    //@Cacheable中不能使用#result,因为方法还没运行之前就已经得到了缓存的key
    @CachePut(/*value = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     *
     *  @CacheEvict：缓存清除
     *  通过key指定清除数据
     *  ,allEntries = true:缓存中emp所有的数据都删除
     *  beforeInvocation = true缓存的清除是否在方法之前执行，默认是方法之后执行，如果出现异常缓存就不会被清除
     */
    @CacheEvict(value = "emp",key = "#id")
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp"+id);
        /*employeeMapper.deleteEmpById(id);*/
        return;
    }
    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp",key = "#result.id"),
                    @CachePut(value = "emp",key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }

}
