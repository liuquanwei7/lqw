package com.lqw.base.test.mapper;

import com.lqw.base.test.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

// @Mapper (可以在Main使用@MapperScan)
public interface DepartmentMapper {

    /**
     * 单个参数查询
     *
     * #{} 是以预编译的形式，将参数设置到sql中 防止sql注入
     * ${} 直接拼装在sql中会有安全问题
     */
    @Select("select * from department where id = #{id}")
    Department getDeptById(Integer id);

    /**
     * 返回List
     * @return
     */
    @Select("select * from department")
    List<Department> selectAll();

    /**
     * 返回单列的map
     * key===>列名
     * value ===> 值
     * @return
     */
    @Select("select * from department where id = #{id}")
    Map<String, Object> selectAllMapById(int id);

    /**
     * 返回多列的map
     * MapKey指定key的值
     * @return
     */
    @MapKey("id")
    @Select("select * from department")
    Map<Integer, Department> selectAllMap();

    /**
     *  多个参数查询
     *
     *   错误 @Select("select * from department where id = #{id} and department_name = #{departmentName}")
     *   多个参数会封装成一个map key为param1、param2。。。paramN   value为传入的值
     *
     * @param id
     * @param departmentName
     * @return
     */
    @Select("select * from department where id = #{param1} and department_name = #{param2}")
    Department getDeptByIdAndDeptName(Integer id, String departmentName);

    /**
     * 命名参数
     *
     * @param id
     * @param departmentName
     * @return
     */
    @Select("select * from department where id = #{id} and department_name = #{departmentName}")
    Department getDeptByIdAndDeptNameByParam(@Param("id") Integer id, @Param("departmentName") String departmentName);

    /**
     * map参数
     *
     * @param map
     * @return
     */
    @Select("select * from department where id = #{id} and department_name = #{departmentName}")
    Department getDeptByIdAndDeptNameByMap(Map<String, Object> map);


    /**
     * List参数
     *
     * Collection（List, Set） 和数组也会封装成map
     * Key Collection ==> collection  List ===> list  数组 ===> array
     * @param ids
     * @return
     */
    @Select("select * from department where id = #{list[0]} and department_name = #{list[1]}")
    Department getDeptByIdAndDeptNameByList(List<String> ids);

    @Results({
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "department_name", property = "departmentName"),
                    @Result(column = "id", property = "employeeList", many = @Many(select = "com.lqw.springboot.mapper.EmployeeMapper.selectByDid"))
    })
    @Select("select * from department where id = #{id}")
    Department getDeptByidPlus(Integer id);

    /**
     * 删除
     */
    @Delete("delete from department where id = #{id}")
    int deleteDeptById(Integer id);

    /**
     * 插入
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //@SelectKey(statement = "select id", keyProperty = "id",  before = true, resultType = Integer.class)
    @Insert("insert into department(department_name) values(#{departmentName})")
    int insertDept(Department department);

    /**
     * 更新
     */
    @Update("update department set department_name = #{departmentName} where id = #{id}")
    int updateDept(Department department);
}
