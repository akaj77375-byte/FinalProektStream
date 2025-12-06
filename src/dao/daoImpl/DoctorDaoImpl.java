package dao.daoImpl;

import dao.DoctorDao;
import dao.HospitalDao;
import database.DB;
import database.GenId;
import models.Department;
import models.Doctor;
import models.Hospital;

import java.util.List;



    public class DoctorDaoImpl implements DoctorDao {
        HospitalDao hospitalDao=new HospitalDaoImpl();
        @Override
        public Doctor findDoctorById(Long hospitalId, Long id) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital == null) return null;

            return hospital.getDoctors().stream()
                    .filter(d -> d.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public String assignDoctorToDepartment(Long hospitalId, Long departmentId, List<Long> doctorsId) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital == null) return "Hospital not found";

            Department department = hospital.getDepartments().stream()
                    .filter(d -> d.getId().equals(departmentId))
                    .findFirst()
                    .orElse(null);

            if (department == null) return "Department not found";

            List<Doctor> doctorsToAdd = hospital.getDoctors().stream()
                    .filter(doc -> doctorsId.contains(doc.getId()))
                    .toList();

            if (doctorsToAdd.isEmpty()) return "Doctors not found";


            doctorsToAdd.stream()
                    .filter(doc -> !department.getDoctors().contains(doc))
                    .forEach(department.getDoctors()::add);

            return "Doctors successfully assigned";
        }

        @Override
        public List<Doctor> getAllDoctorsByHospitalId(Long id) {
            Hospital hospital = hospitalDao.findHospitalById(id);
            return hospital != null ? hospital.getDoctors() : List.of();
        }

        @Override
        public List<Doctor> getAllDoctorsByDepartmentId(Long hospitalId, Long departmentId) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital == null) return List.of();

            return hospital.getDepartments().stream()
                    .filter(dep -> dep.getId().equals(departmentId))
                    .findFirst()
                    .map(Department::getDoctors)
                    .orElse(List.of());
        }

        @Override
        public String add(Long hospitalId, Doctor doctor) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital == null) return "Hospital not found";

            doctor.setId(GenId.getDoctorId(hospitalId));
            hospital.getDoctors().add(doctor);

            return "Doctor added successfully";
        }

        @Override
        public void removeById(Long hospitalId, Long id) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital != null) {
                hospital.getDoctors().removeIf(doc -> doc.getId().equals(id));
            }
        }

        @Override
        public String updateById(Long hospitalId, Long id, Doctor doctor) {
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            if (hospital == null) return "Hospital not found";

            Doctor update = hospital.getDoctors().stream()
                    .filter(d -> d.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (update == null) return "Doctor not found";

            update.setGender(doctor.getGender());
            update.setLastName(doctor.getLastName());
            update.setFirstName(doctor.getFirstName());
            update.setExperienceYear(doctor.getExperienceYear());

            return "";
        }
    }

