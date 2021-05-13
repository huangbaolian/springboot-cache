package com.hbl.cache.mapper;


import com.hbl.cache.bean.Department;
import org.apache.ibatis.annotations.*;

//指定这是一个操作数据库的mapper
@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id=#{id}")
    public Department getDeptByid(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(departmentName) values(#{departmentName}) ")
    public int insertDept(Department department);
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    public  int updateDept(Department department);
}
