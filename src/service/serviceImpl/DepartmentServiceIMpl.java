package service.serviceImpl;

import dao.DepartmentDao;
import dao.daoImpl.DepartmentDaoImpl;
import models.Department;
import service.DepartmentService;

import java.util.List;

public class DepartmentServiceIMpl implements DepartmentService {
    DepartmentDao departmentDao=new DepartmentDaoImpl();

    @Override
    public  List<Department> getAllDepartmentByHospital(Long hospitalId) {
        if (hospitalId == null || hospitalId <= 0) {
            throw new IllegalArgumentException("Некорректный ID госпиталя!");
        }

        try {
            List<Department> departments = departmentDao.getAllDepartmentByHospital(hospitalId);
            if (departments == null || departments.isEmpty()) {
                System.out.println("В этом госпитале нет департаментов.");
                return List.of();
            }
            return departments;
        } catch (Exception e) {
            System.out.println("Ошибка при получении департаментов: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Department findDepartmentByName(Long hospitalId, String name) {
        if (hospitalId == null || hospitalId <= 0) {
            throw new IllegalArgumentException("Некорректный ID госпиталя!");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название департамента не может быть пустым!");
        }

        try {
            Department department = departmentDao.findDepartmentByName(hospitalId, name);
            if (department == null) {
                System.out.println("Департамент не найден.");
            }
            return department;
        } catch (Exception e) {
            System.out.println("Ошибка поиска департамента: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String add(Long hospitalId, Department department) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (department == null) {
            return "Данные департамента не указаны!";
        }

        try {
            departmentDao.add(hospitalId, department);
            return "Успешно добавлен";
        } catch (Exception e) {
            return "Ошибка при добавлении: " + e.getMessage();
        }
    }

    @Override
    public String removeById(Long hospitalId, Long id) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (id == null || id <= 0) {
            return "Некорректный ID департамента!";
        }

        try {
            departmentDao.removeById(hospitalId, id);
            return "Успешно удален";
        } catch (Exception e) {
            return "Ошибка при удалении: " + e.getMessage();
        }
    }

    @Override
    public String updateById(Long hospitalId, Long id, Department department) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (id == null || id <= 0) {
            return "Некорректный ID департамента!";
        }
        if (department == null) {
            return "Данные департамента не указаны!";
        }

        try {
            departmentDao.updateById(hospitalId, id, department);
            return "Успешно обновлен";
        } catch (Exception e) {
            return "Ошибка обновления: " + e.getMessage();
        }
    }
}