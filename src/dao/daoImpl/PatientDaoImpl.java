package dao.daoImpl;

import dao.HospitalDao;
import dao.PatientDao;
import database.DB;
import database.GenId;
import models.Hospital;
import models.Patient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatientDaoImpl implements PatientDao {
    HospitalDao hospitalDao = new HospitalDaoImpl();

    @Override
    public String addPatientsToHospital(Long hospitalId, List<Patient> patients) {
        Hospital hospital = DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElse(null);

        if (hospital == null) return "Hospital not found";
        patients.stream().forEach(patient -> patient.setId(GenId.getPatientId(hospitalId)));
        hospital.getPatients().addAll(patients);
        return "Patients added: " + patients.size();
    }

    @Override
    public Patient getPatientById(Long hospitalId, Long id) {
        return DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .flatMap(h -> h.getPatients().stream())
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<Long, Patient> getPatientByAge(Long hospitalId, Integer age) {
        if (hospitalId == null || age == null) {
            return Map.of();
        }

        return DB.hospitals.stream()
                .filter(h -> h.getId() != null && h.getId().equals(hospitalId))
                .flatMap(h -> {
                    if (h.getPatients() == null) return Stream.<Patient>empty();
                    return h.getPatients().stream();
                })
                .filter(p -> p.getAge() != null && p.getAge().equals(age))
                .collect(Collectors.toMap(
                        p -> (long) p.getId().intValue(),
                        p -> p,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    public List<Patient> sortPatientsByAge(Long hospitalId, String ascOrDesc) {
        return DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .flatMap(h -> h.getPatients().stream())
                .sorted((p1, p2) -> "desc".equalsIgnoreCase(ascOrDesc)
                        ? Integer.compare(p2.getAge(), p1.getAge())
                        : Integer.compare(p1.getAge(), p2.getAge()))
                .toList();
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        Hospital hospital = DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElse(null);


        assert hospital != null;
        hospital.getPatients().add(patient);
        return "Patient added";
    }

    @Override
    public void removeById(Long hospitalId, Long id) {
         DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElse(null);



    }

    @Override
    public String updateById(Long hospitalId, Long id, Patient patient) {
        DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .flatMap(h -> h.getPatients().stream())
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    p.setLastName(patient.getLastName());
                    p.setAge(patient.getAge());
                });
        return "";
    }
}
