package com.lqw.base.test.entity;

import java.util.List;

/**
 *  Department
 *
 * @author liuquanwei
 * @date 2020/6/15
 */
public class Department {

    private Integer id;
    private String departmentName;
    private List<Employee> employeeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
