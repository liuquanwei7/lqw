package com.lqw.base.test.mapper;

import com.lqw.base.test.entity.Employee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper {

    /**
     * 级联属性封装结果集
     *
     * @param id
     * @return
     */

    @Results({
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "lastName", property = "lastName"),
                    @Result(column = "email", property = "email"),
                    @Result(column = "gender", property = "gender"),
                    @Result(column = "did", property = "department.id"),
                    @Result(column = "deptName", property = "department.departmentName")
    })
    @Select("select e.id id, e.lastName lastName, e.email email, e.gender gender, d.id did, d.department_name deptName from department d, employee e where e.did = d.id and e.id = #{id}")
    Employee getById(Integer id);

    /**
     * 分步查询
     *
     * @param id
     * @return
     */
    @Results({
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "lastName", property = "lastName"),
                    @Result(column = "email", property = "email"),
                    @Result(column = "gender", property = "gender"),
                    @Result(column = "did", property = "department", one = @One(select = "com.lqw.springboot.mapper.DepartmentMapper.getDeptById")),
    })
    @Select("select * from department d, employee e where e.did = d.id and e.id = #{id}")
    Employee getById1(Integer id);

    @Select("select * from employee where did = #{did}")
    List<Employee> selectByDid(Integer did);
}
