package service.serviceImpl;

import dao.PatientDao;
import dao.daoImpl.PatientDaoImpl;
import database.DB;
import database.GenId;
import models.Hospital;
import models.Patient;
import service.PatientService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class PatientServiceImpl implements PatientService {

    PatientDao patientDao = new PatientDaoImpl();

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        try {
            if (id == null) return "Hospital id is null!";
            if (patients == null || patients.isEmpty()) return "Patients list is empty!";

            return patientDao.addPatientsToHospital(id, patients);
        } catch (Exception e) {
            return "Error while adding patients: " + e.getMessage();
        }
    }

    @Override
    public Patient getPatientById(Long hospitalId, Long id) {
        try {
            if (hospitalId == null) {
                System.out.println("Hospital id is null");
                return null;
            }
            if (id == null) {
                System.out.println("Patient id is null");
                return null;
            }

            Patient p = patientDao.getPatientById(hospitalId, id);
            if (p == null) {
                System.out.println("Patient not found");
            }
            return p;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Long,Patient> getPatientByAge(Long hospitalId, Integer age) {
        try {
            if (hospitalId == null || age == null) {
                System.out.println("Hospital id or age is null");
                return Map.of();
            }

            Map<Long, Patient> result = patientDao.getPatientByAge(hospitalId, age);
            if (result == null || result.isEmpty()) {
                System.out.println("No patients with age: " + age);
                return Map.of();
            }

            return result;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Map.of();
        }
    }

    @Override
    public List<Patient> sortPatientsByAge(Long hospitalId, String ascOrDesc) {
        try {
            if (hospitalId == null) {
                System.out.println("Hospital id is null");
                return List.of();
            }

            if (ascOrDesc == null ||
                    (!ascOrDesc.equalsIgnoreCase("asc") &&
                            !ascOrDesc.equalsIgnoreCase("desc"))) {
                System.out.println("Sort type must be 'asc' or 'desc'");
                return List.of();
            }

            List<Patient> list = patientDao.sortPatientsByAge(hospitalId, ascOrDesc);
            if (list == null || list.isEmpty()) {
                System.out.println("No patients found");
                return List.of();
            }

            return list;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        try {
            if (hospitalId == null) return "Hospital id is null!";
            if (patient == null) return "Patient is null!";

            return patientDao.add(hospitalId, patient);

        } catch (Exception e) {
            return "Error while adding patient: " + e.getMessage();
        }
    }

    @Override
    public String removeById(Long hospitalId, Long id) {
        try {
            if (hospitalId == null) return "Hospital id is null!";
            if (id == null) return "Patient id is null!";

             patientDao.removeById(hospitalId, id);
return "успешно удален";
        } catch (Exception e) {
            return "Error while removing patient: " + e.getMessage();
        }
    }

    @Override
    public String updateById(Long hospitalId, Long id, Patient patient) {
        try {
            if (hospitalId == null) return "Hospital id is null!";
            if (id == null) return "Patient id is null!";
            if (patient == null) return "New patient data is null!";

            return patientDao.updateById(hospitalId, id, patient);

        } catch (Exception e) {
            return "Error while updating patient: " + e.getMessage();
        }
    }
}

