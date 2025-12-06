package service.serviceImpl;

import dao.DoctorDao;
import dao.daoImpl.DoctorDaoImpl;
import models.Doctor;
import service.DoctorService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService {
    DoctorDao doctorDao=new DoctorDaoImpl();
    @Override
    public Doctor findDoctorById(Long hospitalId, Long id) {
        if (hospitalId == null || hospitalId <= 0) {
            throw new IllegalArgumentException("Некорректный ID госпиталя!");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID доктора!");
        }

        try {
            Doctor doctor = doctorDao.findDoctorById(hospitalId, id);
            if (doctor == null) {
                System.out.println("Доктор не найден.");
            }
            return doctor;
        } catch (Exception e) {
            System.out.println("Ошибка поиска доктора: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String assignDoctorToDepartment(Long hospitalId, Long departmentId, List<Long> doctorsId) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (departmentId == null || departmentId <= 0) {
            return "Некорректный ID департамента!";
        }
        if (doctorsId == null || doctorsId.isEmpty()) {
            return "Список докторов пуст!";
        }

        try {
            doctorDao.assignDoctorToDepartment(hospitalId, departmentId, doctorsId);
            return "Доктора успешно добавлены в департамент";
        } catch (Exception e) {
            return "Ошибка при назначении докторов: " + e.getMessage();
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long hospitalId) {
        if (hospitalId == null || hospitalId <= 0) {
            System.out.println("Некорректный ID госпиталя!");
            return List.of();
        }

        try {
            List<Doctor> doctors = doctorDao.getAllDoctorsByHospitalId(hospitalId);
            if (doctors == null || doctors.isEmpty()) {
                System.out.println("В этом госпитале нет докторов.");
                return List.of();
            }
            return doctors;
        } catch (Exception e) {
            System.out.println("Ошибка получения докторов: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long hospitalId, Long departmentId) {
        if (hospitalId == null || hospitalId <= 0) {
            System.out.println("Некорректный ID госпиталя!");
            return List.of();
        }
        if (departmentId == null || departmentId <= 0) {
            System.out.println("Некорректный ID департамента!");
            return List.of();
        }

        try {
            List<Doctor> doctors = doctorDao.getAllDoctorsByDepartmentId(hospitalId, departmentId);
            if (doctors == null || doctors.isEmpty()) {
                System.out.println("В этом департаменте нет докторов.");
                return List.of();
            }
            return doctors;
        } catch (Exception e) {
            System.out.println("Ошибка получения докторов по департаменту: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (doctor == null) {
            return "Данные доктора не указаны!";
        }

        try {
            doctorDao.add(hospitalId, doctor);
            return "Доктор успешно добавлен";
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
            return "Некорректный ID доктора!";
        }

        try {
            doctorDao.removeById(hospitalId, id);
            return "Доктор успешно удален";
        } catch (Exception e) {
            return "Ошибка при удалении: " + e.getMessage();
        }
    }

    @Override
    public String updateById(Long hospitalId, Long id, Doctor doctor) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (id == null || id <= 0) {
            return "Некорректный ID доктора!";
        }
        if (doctor == null) {
            return "Данные доктора не указаны!";
        }

        try {
            doctorDao.updateById(hospitalId, id, doctor);
            return "Доктор успешно обновлен";
        } catch (Exception e) {
            return "Ошибка обновления: " + e.getMessage();
        }
    }
}
