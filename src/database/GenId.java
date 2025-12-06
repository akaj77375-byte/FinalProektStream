package database;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenId {
    private static Long hospital = 0L;



    private static final Map<Long, Long> departmentCounters = new LinkedHashMap<>();


    private static final Map<Long, Long> doctorCounters = new LinkedHashMap<>();


    private static final Map<Long, Long> patientCounters = new LinkedHashMap<>();




    public static Long getHospitalId() {
        return ++hospital;
    }



    public static Long getDepartmentId(Long hospitalId) {

        departmentCounters.putIfAbsent(hospitalId, 0L);

        Long newId = departmentCounters.get(hospitalId) + 1;

        departmentCounters.put(hospitalId, newId);

        return newId;
    }



    public static Long getDoctorId(Long hospitalId) {

        doctorCounters.putIfAbsent(hospitalId, 0L);

        Long newId = doctorCounters.get(hospitalId) + 1;

        doctorCounters.put(hospitalId, newId);

        return newId;
    }



    public static Long getPatientId(Long hospitalId) {

        patientCounters.putIfAbsent(hospitalId, 0L);

        Long newId = patientCounters.get(hospitalId) + 1;

        patientCounters.put(hospitalId, newId);

        return newId;
    }
}
