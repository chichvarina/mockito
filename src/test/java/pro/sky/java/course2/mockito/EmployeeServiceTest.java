package pro.sky.java.course2.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.java.course2.mockito.model.Employee;
import pro.sky.java.course2.mockito.record.EmployeeRequest;
import pro.sky.java.course2.mockito.service.EmployeeService;

import java.util.ArrayList;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void init(){
        employeeService = new EmployeeService();

        EmployeeRequest req1 = new EmployeeRequest();
        req1.setFirstName("Иванов");
        req1.setLastName("Иван");
        req1.setDepartment(1);
        req1.setSalary(1000);

        EmployeeRequest req2 = new EmployeeRequest();
        req2.setFirstName("Петров");
        req2.setLastName("Петр");
        req2.setDepartment(1);
        req2.setSalary(2000);

        EmployeeRequest req3 = new EmployeeRequest();
        req3.setFirstName("Сидоров");
        req3.setLastName("Сидор");
        req3.setDepartment(1);
        req3.setSalary(3000);

        employeeService.addEmployee(req1);
        employeeService.addEmployee(req2);
        employeeService.addEmployee(req3);
    }


    @Test
    public void collectionTest(){//сравнение коллекций по содержанию
        Employee employee1 = new Employee("Иванов", "Иван", 1, 1000);
        Employee employee2 = new Employee("Петров", "Петр", 1, 2000);
        Employee employee3 = new Employee("Сидоров", "Сидор", 1, 3000);
        ArrayList<Employee> expectedlist = new ArrayList<>();
        expectedlist.add(employee1);
        expectedlist.add(employee2);
        expectedlist.add(employee3);

        Assertions.assertIterableEquals(expectedlist, employeeService.getAllEmploees());

    }

    @Test
    public void exceptionTest1() {//не задана фамилия
        EmployeeRequest req = new EmployeeRequest();
        req.setLastName("Василий");
        req.setDepartment(1);
        req.setSalary(4000);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> employeeService.addEmployee(req)
        );
    }

    @Test
    public void exceptionTest2() {//не задано имя
        EmployeeRequest req = new EmployeeRequest();
        req.setFirstName("Васильев");
        req.setDepartment(1);
        req.setSalary(4000);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> employeeService.addEmployee(req)
        );
    }

    @Test
    public void addEmployeeTest(){//тест добавления
        EmployeeRequest req = new EmployeeRequest();
        req.setFirstName("Кошкин");
        req.setLastName("Василий");
        req.setDepartment(1);
        req.setSalary(5000);
        employeeService.addEmployee(req);
        //проверяем наличие Кошкина в коллекции
        long count = employeeService.getAllEmploees().stream()
                .map(employee -> employee.getFirstName()+" "+employee.getLastName())
                .filter(fio->fio.equals("Кошкин Василий"))
                .count();
        Assertions.assertTrue(count>0);//Кошкин Василий добавился
    }

    @Test
    public void addDoubleEmployeeTest(){//тест НЕдобавления дубликата
        EmployeeRequest req = new EmployeeRequest();
        req.setFirstName("Иванов");
        req.setLastName("Иван");
        req.setDepartment(1);
        req.setSalary(1000);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> employeeService.addEmployee(req)//Иванов Иван уже добавлен в коллекцию
        );
    }

    @Test
    public void removeEmployeeTest(){//тест удаления
        employeeService.removeEmployee(1);//удаляем работника с id=1
        //поверяем что работник удален
        long count = employeeService.getAllEmploees().stream()
                .filter(employee -> employee.getId()==1)
                .count();
        Assertions.assertEquals(0, count);
    }

    @Test
    public void removeNotExistEmployeeTest(){//тест удаления не существующего работника
        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> employeeService.removeEmployee(9999999)//заведомо неверный id работникка
        );
    }

    @Test
    public void getSalarySumTest(){
        Assertions.assertEquals(6000, employeeService.getSalarySum());
    }

    @Test
    public void getSalaryMinTest(){
        Employee employee = new Employee("Иванов", "Иван", 1, 1000);
        ArrayList<Employee> expectedlist=new ArrayList<>();//ожидаемый спмсок сотрудников с минимальной зарплатой
        expectedlist.add(employee);
        Assertions.assertIterableEquals(expectedlist, employeeService.getSalaryMin());
    }

    @Test
    public void getSalaryMaxTest(){
        Employee employee = new Employee("Сидоров", "Сидор", 1, 3000);
        ArrayList<Employee> expectedlist=new ArrayList<>();//ожидаемый спмсок сотрудников с максимальной зарплатой
        expectedlist.add(employee);
        Assertions.assertIterableEquals(expectedlist, employeeService.getSalaryMax());
    }

    @Test
    public void getHighSalaryTest(){
        Employee employee = new Employee("Сидоров", "Сидор", 1, 3000);
        ArrayList<Employee> expectedlist=new ArrayList<>();//ожидаемый список сотрудников, зарплата которых больше средней
        expectedlist.add(employee);
        Assertions.assertIterableEquals(expectedlist, employeeService.getHighSalary());

    }


}
