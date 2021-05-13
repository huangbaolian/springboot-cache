package com.hbl.cache.mapper;


import com.hbl.cache.bean.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM employee WHERE id=#{id}")
    public Employee getEmpById(Integer id);
    @Update("UPDATE employee SET lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{d_id},birth=#{birth}")
    public void updateEmp(Employee employee);
    @Delete("DELETE FROM employee WHERE id=#{id}")
    public void deleteEmpById(Integer id);
    @Insert("INSERT INTO employee(lastName,email,gender,d_id,birth) VALUES (#{lastName},#{email},#{gender},#{d_id},#{birth})")
    public void insertEmp(Employee employee);
}
