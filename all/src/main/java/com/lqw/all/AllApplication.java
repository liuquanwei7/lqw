package com.lqw.all;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
@MapperScan(value = {"com.lqw.base.**.mapper", "com.lqw.system.**.mapper"})
@ComponentScans(value = {@ComponentScan(basePackages = {"com.lqw.core","com.lqw.base","com.lqw.system","com.lqw.all"})})
@SpringBootApplication
public class AllApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllApplication.class, args);
	}

}
