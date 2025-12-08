package dao.daoImpl;

import dao.HospitalDao;
import database.DB;
import database.GenId;
import models.Hospital;
import models.Patient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HospitalDaoImpl implements HospitalDao {

    @Override
    public String addHospital(Hospital hospital) {

        DB.hospitals.add(hospital);
        return "Hospital successfully added";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        return DB.hospitals.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Hospital> getAllHospital() {
        return DB.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        Hospital hospital = findHospitalById(id);
        return hospital != null ? hospital.getPatients() : List.of();
    }

    @Override
    public String deleteHospitalById(Long id) {
        boolean removed = DB.hospitals.removeIf(h -> h.getId().equals(id));
        return removed ? "Hospital deleted" : "Hospital not found";
    }

    @Override
    public String updateHospital(Long id, Hospital hospital) {
        Hospital old = findHospitalById(id);
        if (old == null) return "Hospital not found";

        old.setAddress(hospital.getAddress());
        old.setHospitalName(hospital.getHospitalName());

        return "Hospital updated";
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        return DB.hospitals.stream()
                .filter(h -> h.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toMap(
                        Hospital::getAddress,
                        h -> h
                ));
    }
}

