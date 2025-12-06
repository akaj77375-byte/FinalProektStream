package service;

import models.Department;

import java.util.List;

public interface DepartmentService extends GenericService<Department>{

    List<Department> getAllDepartmentByHospital(Long hospitalId);
    Department findDepartmentByName(Long hospitalId,String name);

}