import database.GenId;
import enums.Gender;
import models.Department;
import models.Doctor;
import models.Hospital;
import models.Patient;
import service.DepartmentService;
import service.DoctorService;
import service.HospitalService;
import service.PatientService;
import service.serviceImpl.DepartmentServiceIMpl;
import service.serviceImpl.DoctorServiceImpl;
import service.serviceImpl.HospitalServiceImpl;
import service.serviceImpl.PatientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

        public static void main(String[] args) {
            Scanner sc=new Scanner(System.in);
            HospitalService hospitalService = new HospitalServiceImpl();
            DepartmentService departmentService = new DepartmentServiceIMpl();
            DoctorService doctorService = new DoctorServiceImpl();
            PatientService patientService = new PatientServiceImpl();

            boolean isWork = true;

            while (isWork) {
                System.out.println("""
                    \n=== MAIN MENU ===
                    1) Hospital
                    2) Department
                    3) Doctor
                    4) Patient
                    5) Exit
                    """);

                String choice = sc.nextLine();

                switch (choice) {


                    case "1" -> {
                        boolean hospitalMenu = true;
                        while (hospitalMenu) {
                            System.out.println("""
                                \n--- HOSPITAL MENU ---
                                1) Add hospital
                                2) Find hospital by ID
                                3) Get all hospitals
                                4) Get all patients from hospital
                                5) Delete hospital
                                6) Update hospital
                                7) Get hospitals by address
                                8) Back
                                """);

                            String c = sc.nextLine();

                            switch (c) {
                                case "1" -> {
                                    System.out.println("Название:");
                                    String name = safeString();
                                    System.out.println("Адрес:");
                                    String address = safeString();
                                    Hospital h = new Hospital(
                                            GenId.getHospitalId(),
                                            name, address,
                                            new ArrayList<>(),
                                            new ArrayList<>(),
                                            new ArrayList<>()
                                    );
                                    System.out.println(hospitalService.addHospital(h));
                                }

                                case "2" -> {
                                    System.out.println("Введите ID:");
                                    Long id = safeLong();
                                    System.out.println(hospitalService.findHospitalById(id));
                                }

                                case "3" -> {
                                    System.out.println(hospitalService.getAllHospital());
                                }

                                case "4" -> {
                                    System.out.println("ID больницы:");
                                    Long id = safeLong();
                                    System.out.println(hospitalService.getAllPatientFromHospital(id));
                                }

                                case "5" -> {
                                    System.out.println("Введите ID:");
                                    Long id = safeLong();
                                    System.out.println(hospitalService.deleteHospitalById(id));
                                }

                                case "6" -> {
                                    System.out.println("ID:");
                                    Long id = safeLong();
                                    System.out.println("Новое имя:");
                                    String n = safeString();
                                    System.out.println("Новый адрес:");
                                    String a = safeString();
                                    Hospital up = new Hospital();
                                    up.setHospitalName(n);
                                    up.setAddress(a);
                                    System.out.println(hospitalService.updateHospital(id, up));
                                }

                                case "7" -> {
                                    System.out.println("Введите адрес:");
                                    String a = safeString();
                                    System.out.println(hospitalService.getAllHospitalByAddress(a));
                                }

                                case "8" -> hospitalMenu = false;

                                default -> System.out.println("Неверный выбор!");
                            }
                        }
                    }


                    case "2" -> {
                        boolean depMenu = true;
                        while (depMenu) {
                            System.out.println("""
                                \n--- DEPARTMENT MENU ---
                                1) Add department
                                2) Remove department
                                3) Update department
                                4) Get all departments by hospital
                                5) Find department by name
                                6) Back
                                """);

                            String c = sc.nextLine();

                            switch (c) {
                                case "1" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Название отдела:");
                                    String name = safeString();
                                    Department d = new Department(null, name, new ArrayList<>());
                                    System.out.println(departmentService.add(hid, d));
                                }
                                case "2" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Department ID:");
                                    Long did = safeLong();
                                    System.out.println(departmentService.removeById(hid, did));
                                }
                                case "3" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Department ID:");
                                    Long did = safeLong();
                                    System.out.println("Новое имя:");
                                    String n = safeString();
                                    Department d = new Department();
                                    d.setDepartmentName(n);
                                    System.out.println(departmentService.updateById(hid, did, d));
                                }
                                case "4" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println(departmentService.getAllDepartmentByHospital(hid));
                                }
                                case "5" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Название:");
                                    String name = safeString();
                                    System.out.println(departmentService.findDepartmentByName(hid, name));
                                }
                                case "6" -> depMenu = false;

                                default -> System.out.println("Неверный выбор!");
                            }
                        }
                    }


                    case "3" -> {
                        boolean docMenu = true;

                        while (docMenu) {
                            System.out.println("""
                                \n--- DOCTOR MENU ---
                                1) Add doctor
                                2) Remove doctor
                                3) Update doctor
                                4) Find doctor by ID
                                5) Assign doctor to department
                                6) Get doctors by hospital
                                7) Get doctors by department
                                8) Back
                                """);

                            String c = sc.nextLine();

                            switch (c) {
                                case "1" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();

                                    System.out.println("Имя:");
                                    String fn = safeString();
                                    System.out.println("Фамилия:");
                                    String ln = safeString();
                                    System.out.println("Пол:");
                                    Gender g = safeGender();
                                    System.out.println("Опыт:");
                                    int exp = safeInt();

                                    Doctor d = new Doctor(null, fn, ln, g, exp);
                                    System.out.println(doctorService.add(hid, d));
                                }

                                case "2" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Doctor ID:");
                                    Long did = safeLong();
                                    System.out.println(doctorService.removeById(hid, did));
                                }

                                case "3" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Doctor ID:");
                                    Long did = safeLong();
                                    System.out.println("Имя:");
                                    String fn = safeString();
                                    System.out.println("Фамилия:");
                                    String ln = safeString();
                                    System.out.println("Пол:");
                                    Gender g = safeGender();
                                    System.out.println("Опыт:");
                                    int exp = safeInt();

                                    Doctor d = new Doctor(null, fn, ln, g, exp);
                                    System.out.println(doctorService.updateById(hid, did, d));
                                }

                                case "4" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Doctor ID:");
                                    Long did = safeLong();
                                    System.out.println(doctorService.findDoctorById(hid, did));
                                }

                                case "5" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Department ID:");
                                    Long did = safeLong();
                                    System.out.println("Количество докторов:");
                                    int count = safeInt();
                                    List<Long> ids = new ArrayList<>();
                                    for (int i = 0; i < count; i++) {
                                        System.out.println("Doctor ID:");
                                        ids.add(safeLong());
                                    }
                                    System.out.println(doctorService.assignDoctorToDepartment(hid, did, ids));
                                }

                                case "6" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println(doctorService.getAllDoctorsByHospitalId(hid));
                                }

                                case "7" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Department ID:");
                                    Long did = safeLong();
                                    System.out.println(doctorService.getAllDoctorsByDepartmentId(hid, did));
                                }

                                case "8" -> docMenu = false;

                                default -> System.out.println("Неверный выбор!");
                            }
                        }
                    }


                    case "4" -> {
                        boolean patMenu = true;

                        while (patMenu) {
                            System.out.println("""
                                \n--- PATIENT MENU ---
                                1) Add patient
                                2) Add multiple patients
                                3) Get patient by ID
                                4) Get patient by age
                                5) Sort patients by age
                                6) Back
                                """);

                            String c = sc.nextLine();

                            switch (c) {
                                case "1" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Имя:");
                                    String fn = safeString();
                                    System.out.println("Фамилия:");
                                    String ln = safeString();
                                    System.out.println("Возраст:");
                                    int age = safeInt();
                                    System.out.println("Пол:");
                                    Gender g = safeGender();

                                    Patient p = new Patient(0L, fn, ln, age, g);
                                    System.out.println(patientService.addPatientsToHospital(hid, List.of(p)));
                                }

                                case "2" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Сколько?");
                                    int count = safeInt();
                                    List<Patient> ps = new ArrayList<>();

                                    for (int i = 0; i < count; i++) {
                                        System.out.println("Имя:");
                                        String fn = safeString();
                                        System.out.println("Фамилия:");
                                        String ln = safeString();
                                        System.out.println("Возраст:");
                                        int age = safeInt();
                                        System.out.println("Пол:");
                                        Gender g = safeGender();

                                        ps.add(new Patient(null, fn, ln, age, g));
                                    }
                                    System.out.println(patientService.addPatientsToHospital(hid, ps));
                                }

                                case "3" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Patient ID:");
                                    Long pid = safeLong();
                                    System.out.println(patientService.getPatientById(hid, pid));
                                }

                                case "4" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("Возраст:");
                                    int age = safeInt();
                                    System.out.println(patientService.getPatientByAge(hid, age));
                                }

                                case "5" -> {
                                    System.out.println("Hospital ID:");
                                    Long hid = safeLong();
                                    System.out.println("ASC or DESC:");
                                    String sort = safeString();
                                    System.out.println(patientService.sortPatientsByAge(hid, sort));
                                }

                                case "6" -> patMenu = false;

                                default -> System.out.println("Неверный выбор!");
                            }
                        }
                    }

                    case "5" -> isWork = false;

                    default -> System.out.println("Неверный выбор!");
                }
            }
        }
    public static Scanner sc=new Scanner(System.in);
    public static Long safeLong() {

        while (true) {
            try {
                System.out.print("> ");
                return Long.parseLong(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Введите корректный ID (Long)!");
            }
        }
    }

    public static int safeInt() {
        while (true) {
            try {
                System.out.print("> ");
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    public static String safeString() {
        while (true) {
            String s = sc.nextLine();
            if (!s.isBlank()) return s;
            System.out.println("Поле не может быть пустым!");
        }
    }

    public static Gender safeGender() {
        while (true) {
            try {
                return Gender.valueOf(sc.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Введите MALE или FEMALE!");
            }
        }
    }

}