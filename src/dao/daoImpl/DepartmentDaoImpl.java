package dao.daoImpl;

import dao.DepartmentDao;
import dao.HospitalDao;
import database.DB;
import database.GenId;
import models.Department;
import models.Hospital;

import java.util.List;

    public class DepartmentDaoImpl implements DepartmentDao {
        HospitalDao hospitalDao=new HospitalDaoImpl();
        @Override
        public List<Department> getAllDepartmentByHospital(Long id) {
          Hospital dep= hospitalDao.findHospitalById(id);
            return dep.getDepartments();
        }

        @Override
        public Department findDepartmentByName(Long hospitalId, String name) {
            return DB.hospitals.stream()
                    .filter(h -> h.getId().equals(hospitalId))
                    .flatMap(h -> h.getDepartments().stream())
                    .filter(d -> d.getDepartmentName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public String add(Long hospitalId, Department department) {
          Hospital id=hospitalDao.findHospitalById(hospitalId);
            assert id != null;
            department.setId(GenId.getDepartmentId(hospitalId));
            id.getDepartments().add(department);
            return "";
        }

        @Override
        public void removeById(Long hospital,Long id) {
            Hospital remove =hospitalDao.findHospitalById(hospital);
            remove.getDepartments().removeIf(department -> department.getId().equals(id));
        }

        @Override
        public String updateById(Long hospital, Long id, Department department) {
            Hospital hospitals = hospitalDao.findHospitalById(id);

            Department dep = hospitals.getDepartments().stream()
                    .filter(d -> d.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (dep == null) {
                return "Department not found!";
            }

            dep.setDepartmentName(department.getDepartmentName());
            return "";
        }
}
