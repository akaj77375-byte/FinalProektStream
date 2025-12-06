package service.serviceImpl;

import dao.HospitalDao;
import dao.daoImpl.HospitalDaoImpl;
import models.Department;
import models.Doctor;
import models.Hospital;
import models.Patient;
import service.HospitalService;

import java.util.List;
import java.util.Map;


    public class HospitalServiceImpl implements HospitalService {
        HospitalDao hospitalDao = new HospitalDaoImpl();

        @Override
        public String addHospital(Hospital hospital) {
            if (hospital == null) {
                return "Данные госпиталя не указаны!";
            }

            try {
                if (hospital.getHospitalName() == null || hospital.getHospitalName().isBlank()) {
                    return "Название госпиталя не должно быть пустым!";
                }
                if (hospital.getAddress() == null || hospital.getAddress().isBlank()) {
                    return "Адрес госпиталя не должен быть пустым!";
                }

                hospitalDao.addHospital(hospital);
                return "Успешно добавлен";

            } catch (Exception e) {
                System.out.println("Ошибка при добавлении госпиталя: " + e.getMessage());
                return "Не удалось добавить госпиталь";
            }
        }

        @Override
        public Hospital findHospitalById(Long id) {
            if (id == null || id <= 0) {
                System.out.println("Некорректный ID госпиталя!");
                return null;
            }

            try {
                Hospital hospital = hospitalDao.findHospitalById(id);
                if (hospital == null) {
                    System.out.println("Госпиталь не найден.");
                }
                return hospital;

            } catch (Exception e) {
                System.out.println("Ошибка поиска госпиталя: " + e.getMessage());
                return null;
            }
        }

        @Override
        public List<Hospital> getAllHospital() {
            try {
                List<Hospital> hospitals = hospitalDao.getAllHospital();
                if (hospitals == null || hospitals.isEmpty()) {
                    System.out.println("Госпиталей нет.");
                    return List.of();
                }
                return hospitals;
            } catch (Exception e) {
                System.out.println("Ошибка получения списка госпиталей: " + e.getMessage());
                return List.of();
            }
        }

        @Override
        public List<Patient> getAllPatientFromHospital(Long id) {
            if (id == null || id <= 0) {
                System.out.println("Некорректный ID госпиталя!");
                return List.of();
            }

            try {
                List<Patient> patients = hospitalDao.getAllPatientFromHospital(id);
                if (patients == null || patients.isEmpty()) {
                    System.out.println("В этом госпитале нет пациентов.");
                    return List.of();
                }
                return patients;

            } catch (Exception e) {
                System.out.println("Ошибка получения пациентов: " + e.getMessage());
                return List.of();
            }
        }

        @Override
        public String deleteHospitalById(Long id) {
            if (id == null || id <= 0) {
                return "Некорректный ID госпиталя!";
            }

            try {
                hospitalDao.deleteHospitalById(id);
                return "Успешно удален";
            } catch (Exception e) {
                return "Ошибка удаления: " + e.getMessage();
            }
        }

        @Override
        public String updateHospital(Long id, Hospital hospital) {
            if (id == null || id <= 0) {
                return "Некорректный ID госпиталя!";
            }
            if (hospital == null) {
                return "Данные госпиталя не указаны!";
            }

            try {
                hospitalDao.updateHospital(id, hospital);
                return "Успешно обновлен";
            } catch (Exception e) {
                return "Ошибка обновления: " + e.getMessage();
            }
        }

        @Override
        public Map<String, Hospital> getAllHospitalByAddress(String address) {
            if (address == null || address.isBlank()) {
                System.out.println("Адрес не должен быть пустым!");
                return Map.of();
            }

            try {
                Map<String, Hospital> map = hospitalDao.getAllHospitalByAddress(address);
                if (map == null || map.isEmpty()) {
                    System.out.println("По этому адресу госпиталей не найдено.");
                    return Map.of();
                }
                return map;
            } catch (Exception e) {
                System.out.println("Ошибка поиска по адресу: " + e.getMessage());
                return Map.of();
            }

        }

    }

