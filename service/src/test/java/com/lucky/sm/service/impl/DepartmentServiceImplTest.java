package com.lucky.sm.service.impl;

import com.lucky.sm.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class DepartmentServiceImplTest {

    @Resource(name = "departmentService")
    DepartmentServiceImpl departmentService;

    @org.junit.Test
    public void add() {
    }

    @org.junit.Test
    public void remove() {
    }

    @org.junit.Test
    public void edit() {
    }

    @org.junit.Test
    public void get() {
        Department department = departmentService.get(1);
        System.out.println(department.getName());
    }

    @org.junit.Test
    public void getAll() {
    }
}