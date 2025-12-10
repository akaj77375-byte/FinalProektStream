package service;


import models.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService extends GenericService<Patient>{
    String addPatientsToHospital(Long id, List<Patient> patients);
    Patient getPatientById(Long hospitalId,Long id);
    Map<Long, Patient> getPatientByAge(Long hospitalId, Integer age);
    List<Patient> sortPatientsByAge(Long hospitalId,String ascOrDesc);
}

