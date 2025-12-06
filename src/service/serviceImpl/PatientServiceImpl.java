package service.serviceImpl;

import dao.PatientDao;
import dao.daoImpl.PatientDaoImpl;
import models.Patient;
import service.PatientService;

import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements PatientService {
    PatientDao patientDao=new PatientDaoImpl();
    @Override
    public String addPatientsToHospital(Long hospitalId, List<Patient> patients) {
        if (hospitalId == null || hospitalId <= 0) {
            return "Некорректный ID госпиталя!";
        }
        if (patients == null || patients.isEmpty()) {
            return "Список пациентов пуст!";
        }

        try {
            patientDao.addPatientsToHospital(hospitalId, patients);
            return "Пациенты успешно добавлены";
        } catch (Exception e) {
            return "Ошибка при добавлении пациентов: " + e.getMessage();
        }
    }

    @Override
    public Patient getPatientById(Long hospitalId, Long id) {
        if (hospitalId == null || hospitalId <= 0) {
            System.out.println("Некорректный ID госпиталя!");
            return null;
        }
        if (id == null || id <= 0) {
            System.out.println("Некорректный ID пациента!");
            return null;
        }

        try {
            Patient patient = patientDao.getPatientById(hospitalId, id);
            if (patient == null) {
                System.out.println("Пациент не найден.");
            }
            return patient;
        } catch (Exception e) {
            System.out.println("Ошибка поиска пациента: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(Long hospitalId, Integer age) {
        if (hospitalId == null || hospitalId <= 0) {
            System.out.println("Некорректный ID госпиталя!");
            return Map.of();
        }
        if (age == null || age < 0) {
            System.out.println("Возраст не может быть отрицательным или пустым!");
            return Map.of();
        }

        try {
            Map<Integer, Patient> result = patientDao.getPatientByAge(hospitalId, age);
            if (result == null || result.isEmpty()) {
                System.out.println("Пациентов с таким возрастом не найдено.");
                return Map.of();
            }
            return result;
        } catch (Exception e) {
            System.out.println("Ошибка поиска по возрасту: " + e.getMessage());
            return Map.of();
        }
    }

    @Override
    public List<Patient> sortPatientsByAge(Long hospitalId, String ascOrDesc) {
        if (hospitalId == null || hospitalId <= 0) {
            System.out.println("Некорректный ID госпиталя!");
            return List.of();
        }
        if (ascOrDesc == null || ascOrDesc.isBlank()) {
            System.out.println("Не указан тип сортировки: asc или desc.");
            return List.of();
        }

        ascOrDesc = ascOrDesc.toLowerCase();

        if (!ascOrDesc.equals("asc") && !ascOrDesc.equals("desc")) {
            System.out.println("Тип сортировки должен быть 'asc' или 'desc'.");
            return List.of();
        }

        try {
            List<Patient> sorted = patientDao.sortPatientsByAge(hospitalId, ascOrDesc);
            if (sorted == null || sorted.isEmpty()) {
                System.out.println("Пациентов нет.");
                return List.of();
            }
            return sorted;
        } catch (Exception e) {
            System.out.println("Ошибка сортировки: " + e.getMessage());
            return List.of();
        }
    }
}

