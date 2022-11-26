package pro.sky.java.course2.mockito.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course2.mockito.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    //возвращает список сотрудников по департаменту
    public Collection<Employee> getDepartmentAllEmploees(int department){
        return employeeService.getAllEmploees().stream()
                .filter(employee -> employee.getDepartment()==department)
                .collect(Collectors.toList());
    }

    //возвращает сумму зарплат по департаменту
    public int getDepartmentSalarySum(int department){
        return this.getDepartmentAllEmploees(department).stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    //возвращает минимальную зарплату по департаменту
    public int getDepartmentSalaryMin(int department){
        return this.getDepartmentAllEmploees(department).stream()
                .mapToInt(Employee::getSalary)
                .min()
                .orElse(0);
    }

    //возвращает максималььную зарплату по департаменту
    public int getDepartmentSalaryMax(int department){
        return this.getDepartmentAllEmploees(department).stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0);
    }

    //возвращает сотрудников, сгруппированых по отделам в виде Map<Integer,List<Employees>>,
    //где ключ — это номер отдела, а значение — список сотрудников данного отдела.
    public Map<Integer,List<Employee>> getEmployeesByDepartment(){
        Map<Integer,List<Employee>> map = this.employeeService.getAllEmploees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        return map;
    }

}
