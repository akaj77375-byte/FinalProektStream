package dao;

import models.Hospital;
import models.Patient;

import java.util.List;
import java.util.Map;

public interface HospitalDao {
    String addHospital(Hospital hospital);
    Hospital findHospitalById(Long id);
    List<Hospital> getAllHospital();
    List<Patient> getAllPatientFromHospital(Long id);
    String deleteHospitalById(Long id);
    String updateHospital(Long id,Hospital hospital);
    Map<Long, Hospital> getAllHospitalByAddress(String address);
}
