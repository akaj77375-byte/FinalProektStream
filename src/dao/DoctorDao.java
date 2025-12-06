package dao;

import models.Doctor;

import java.util.List;

public interface DoctorDao extends GenericDao<Doctor>{
    Doctor findDoctorById(Long hospitalId, Long id);
    String assignDoctorToDepartment(Long hospitalId,Long departmentId, List<Long> doctorsId);
    List<Doctor> getAllDoctorsByHospitalId(Long id);
    List<Doctor> getAllDoctorsByDepartmentId(Long hospitalId,Long departmentId);
}
