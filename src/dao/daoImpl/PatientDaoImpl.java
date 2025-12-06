package dao.daoImpl;

import dao.HospitalDao;
import dao.PatientDao;
import database.GenId;
import models.Hospital;
import models.Patient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatientDaoImpl implements PatientDao {
    HospitalDao hospitalDao = new HospitalDaoImpl();

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        Hospital hospital = hospitalDao.findHospitalById(id);
        if (hospital == null) return "Hospital not found";


        patients.forEach(p -> p.setId(GenId.getPatientId(id)));

        hospital.getPatients().addAll(patients);

        return "Patients successfully added";
    }

    @Override
    public Patient getPatientById(Long hospitalId, Long id) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return null;

        return hospital.getPatients().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<Long, Patient> getPatientByAge(Long hospitalId, Integer age) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return Map.of();


        return hospital.getPatients().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toMap(
                        Patient::getId,
                        p -> p
                ));
    }

    @Override
    public List<Patient> sortPatientsByAge(Long hospitalId, String ascOrDesc) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return List.of();

        Stream<Patient> stream = hospital.getPatients().stream();

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            return stream.sorted(Comparator.comparing(Patient::getAge)).toList();
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            return stream.sorted(Comparator.comparing(Patient::getAge).reversed()).toList();
        }

        return List.of();
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return "Hospital not found";

        patient.setId(GenId.getPatientId(hospitalId));
        hospital.getPatients().add(patient);

        return "Patient added";
    }

    @Override
    public void removeById(Long hospitalId, Long id) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return;

        hospital.getPatients().removeIf(p -> p.getId().equals(id));
    }

    @Override
    public String updateById(Long hospitalId, Long id, Patient patient) {
        Hospital hospital = hospitalDao.findHospitalById(hospitalId);
        if (hospital == null) return "Hospital not found";

        Patient existing = hospital.getPatients().stream()
                .filter(p -> p.getId().equals(id))  // ✔ было p.getId()
                .findFirst()
                .orElse(null);

        if (existing == null) return "Patient not found";

        existing.setFirstName(patient.getFirstName());
        existing.setLastName(patient.getLastName());
        existing.setAge(patient.getAge());
        existing.setGender(patient.getGender());

        return "Patient updated";
    }
}
