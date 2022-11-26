package pro.sky.java.course2.mockito.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course2.mockito.model.Employee;
import pro.sky.java.course2.mockito.record.EmployeeRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees= new HashMap<>();

    public Collection<Employee> getAllEmploees(){
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Имя работника не задано");
        }

        //совпадение по имени и фамилии
        long count = employees.values().stream()
                .filter(employee -> employee.getFirstName().equals(employeeRequest.getFirstName())
                        && employee.getLastName().equals(employeeRequest.getLastName()))
                .count();
        //дубликат
        if (count>0) {
            throw new IllegalArgumentException("Такой работник уже добавлен");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(),
                employeeRequest.getDepartment(), employeeRequest.getSalary());

        employees.put(employee.getId(), employee);
        return employee;
    }

    public void removeEmployee(int id){
        if(employees.containsKey(id)) {
            employees.remove(id);
        }else{
            throw new IllegalArgumentException("Подлежащий удалению работник не найден");
        }
    }

    public int getSalarySum(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary).sum();
    }

    public List<Employee> getSalaryMin(){
        //ищем минимальную зарплату
        int minSalary =  employees.values().stream()
                .mapToInt(Employee::getSalary).min().orElse(0);
        //сотрудников с минимальной зарплатой может быть несколько
        return employees.values().stream()
                .filter(employee -> employee.getSalary()==minSalary)
                .collect(Collectors.toList());
    }

    public List<Employee> getSalaryMax(){
        //ищем максимальную зарплату
        int maxSalary =  employees.values().stream()
                .mapToInt(Employee::getSalary).max().orElse(0);
        //сотрудников с максимальной зарплатой может быть несколько
        return employees.values().stream()
                .filter(employee -> employee.getSalary()==maxSalary)
                .collect(Collectors.toList());
    }

    public List<Employee> getHighSalary(){
        //получаем среднюю зарплату
        double averageSalary = employees.values().stream()
                .mapToInt(Employee::getSalary).average().orElse(0);
        //список сотрудников, зарплата которых больше средней зарплаты
        return employees.values().stream()
                .filter(employee -> employee.getSalary() > averageSalary)
                .collect(Collectors.toList());
    }
}
