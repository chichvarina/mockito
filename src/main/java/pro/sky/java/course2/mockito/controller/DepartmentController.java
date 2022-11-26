package pro.sky.java.course2.mockito.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course2.mockito.model.Employee;
import pro.sky.java.course2.mockito.service.DepartmentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //возвращает список сотрудников по департаменту
    @GetMapping("/department/{id}/employees")
    public Collection<Employee> getDepartmentAllEmploees(@PathVariable("id") int department) {
        //System.out.println("A department = "+department);
        return departmentService.getDepartmentAllEmploees(department);
    }

    //возвращает сумму зарплат по департаменту
    @GetMapping("/department/{id}/salary/sum")
    public int getDepartmentSalarySum(@PathVariable("id") int department) {
        return departmentService.getDepartmentSalarySum(department);
    }

    //возвращает минимальную зарплату по департаменту
    @GetMapping("/department/{id}/salary/min")
    public int getDepartmentSalaryMin(@PathVariable("id") int department) {
        return departmentService.getDepartmentSalaryMin(department);
    }

    //возвращает максималььную зарплату по департаменту
    @GetMapping("/department/{id}/salary/max")
    public int getDepartmentSalaryMax(@PathVariable("id") int department) {
        return departmentService.getDepartmentSalaryMax(department);
    }

    //возвращает сотрудников, сгруппированых по отделам в виде Map<Integer,List<Employees>>,
    //где ключ — это номер отдела, а значение — список сотрудников данного отдела.
    @GetMapping("/department/employees")
    public Map<Integer, List<Employee>> getEmployeesByDepartment() {
        return departmentService.getEmployeesByDepartment();
    }

}