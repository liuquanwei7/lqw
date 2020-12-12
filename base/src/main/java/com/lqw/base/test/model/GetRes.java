package com.lqw.base.test.model;

import com.lqw.base.test.entity.Employee;

import java.util.List;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class GetRes {

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
