package com.lqw.base.test.controller;

import com.lqw.base.test.entity.Department;
import com.lqw.base.test.mapper.DepartmentMapper;
import com.lqw.base.test.model.GetRes;
import com.lqw.core.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
@RestController
public class TestController {

	@Autowired
	DepartmentMapper mapper;

	/**
	 * test
	 * @param id
	 * @return
	 */
	@GetMapping("/dept/{id}")
	public Result<GetRes> getDepartment(@PathVariable Integer id) {
		Department data = mapper.getDeptById(id);
		GetRes res = new GetRes();
		res.setId(data.getId());
		res.setDepartmentName(data.getDepartmentName());
		res.setEmployeeList(data.getEmployeeList());

		return Result.valueOfOk(res);
	}

	@GetMapping("/dept/plus/{id}")
	public Department getDepartmentPlus(@PathVariable Integer id) {
		return mapper.getDeptByidPlus(id);
	}

	@GetMapping("/dept/all")
	public List<Department> getAllDepartment() {
		return mapper.selectAll();
	}

	@GetMapping("/dept/all/map/{id}")
	public Map<String, Object> getAllDepartmentMap(@PathVariable int id) {
		return mapper.selectAllMapById(id);
	}

	@GetMapping("/dept/all/map")
	public Map<Integer, Department> getAllDepartmentMap() {
		return mapper.selectAllMap();
	}

	@GetMapping("/dept/{id}/{departmentName}")
	public Department getDepartment(@PathVariable Integer id, @PathVariable String departmentName) {
//        return mapper.getDeptByIdAndDeptName(id, departmentName);
		//       return mapper.getDeptByIdAndDeptNameByParam(id, departmentName);
		Map map = new HashMap();
		map.put("id", id);
		map.put("departmentName", departmentName);
		return mapper.getDeptByIdAndDeptNameByMap(map);
	}

	@GetMapping("/dept")
	public Department insertDepartment(Department department) {
		mapper.insertDept(department);
		return department;
	}
}
