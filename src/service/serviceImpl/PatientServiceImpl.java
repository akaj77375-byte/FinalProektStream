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
    PatientDao patientDao=new PatientDaoImpl();
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
    public Map<Integer, Patient> getPatientByAge(Long hospitalId, Integer age) {
        return DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .flatMap(h -> h.getPatients().stream())
                .filter(p -> p.getAge().equals(age))
                .collect(Collectors.toMap(
                        p -> p.getId().intValue(),
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

        if (hospital == null) return "Hospital not found";

        hospital.getPatients().add(patient); // ID генерируется автоматически
        return "Patient added";
    }

    @Override
    public String removeById(Long hospitalId, Long id) {
        Hospital hospital = DB.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElse(null);

        if (hospital == null) return "Hospital not found";

        boolean removed = hospital.getPatients().removeIf(p -> p.getId().equals(id));
        return removed ? "Patient removed" : "Patient not found";
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

