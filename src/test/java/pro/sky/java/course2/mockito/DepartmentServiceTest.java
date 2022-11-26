package pro.sky.java.course2.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course2.mockito.model.Employee;
import pro.sky.java.course2.mockito.service.DepartmentService;
import pro.sky.java.course2.mockito.service.EmployeeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    EmployeeService employeeService;

    ArrayList<Employee> expectedAllEmployeesList;//ожидаемый список всех работников

    List<Employee> expectedAllEmployeesList1;//ожидаемый список всех работников первого отдела
    List<Employee> expectedAllEmployeesList2;//ожидаемый список всех работников второго отдела

    Map<Integer,List<Employee>> expextedMap; //ожидаемое распределение по отделам

    DepartmentService departmentService;

    @BeforeEach
    public void init(){
        departmentService = new DepartmentService(employeeService);

        Employee employee1 = new Employee("Иванов", "Иван", 1, 1000);
        Employee employee2 = new Employee("Петров", "Петр", 1, 2000);
        Employee employee3 = new Employee("Сидоров", "Сидор", 1, 3000);

        Employee employee4 = new Employee("Грозный", "Иван", 2, 10000);
        Employee employee5 = new Employee("Шуйский", "Василий", 2, 6000);
        Employee employee6 = new Employee("Годунов", "Борис", 2, 7000);

        expectedAllEmployeesList = new ArrayList<>();
        expectedAllEmployeesList.add(employee1);
        expectedAllEmployeesList.add(employee2);
        expectedAllEmployeesList.add(employee3);
        expectedAllEmployeesList.add(employee4);
        expectedAllEmployeesList.add(employee5);
        expectedAllEmployeesList.add(employee6);

        expectedAllEmployeesList1 = expectedAllEmployeesList.stream()
                .filter(employee -> employee.getDepartment() == 1).toList();

        expectedAllEmployeesList2 = expectedAllEmployeesList.stream()
                .filter(employee -> employee.getDepartment() == 2).toList();

        expextedMap = new HashMap<>();
        expextedMap.put(1, expectedAllEmployeesList1);
        expextedMap.put(2, expectedAllEmployeesList2);

        Mockito.when(employeeService.getAllEmploees()).thenReturn(expectedAllEmployeesList);//мокаем список всех сотрудников
    }

    //getDepartmentAllEmploees - возвращает список сотрудников по департаменту
    @Test
    public void getDepartmentAllEmploeesTest1(){//тест первого департамента
        Assertions.assertIterableEquals(expectedAllEmployeesList1, departmentService.getDepartmentAllEmploees(1));
    }
    @Test
    public void getDepartmentAllEmploeesTest2(){//тест второго департамента
        Assertions.assertIterableEquals(expectedAllEmployeesList2, departmentService.getDepartmentAllEmploees(2));
    }

    @Test
    public void getDepartmentAllEmploeesTest3(){//тест несуществующего департамента (сравнение с пустым списком)
        Assertions.assertIterableEquals(new ArrayList<Employee>(), departmentService.getDepartmentAllEmploees(3));
    }

    //getDepartmentSalarySum - возвращает сумму зарплат по департаменту
    @Test
    public void getDepartmentSalarySumTest1(){//тест первого департамента
        Assertions.assertEquals(6000, departmentService.getDepartmentSalarySum(1));
    }
    @Test
    public void getDepartmentSalarySumTest2(){//тест второго департамента
        Assertions.assertEquals(23000, departmentService.getDepartmentSalarySum(2));
    }
    @Test
    public void getDepartmentSalarySumTest3(){//тест несуществующего департамента
        Assertions.assertEquals(0, departmentService.getDepartmentSalarySum(3));
    }

    //getDepartmentSalaryMin возвращает минимальную зарплату по департаменту
    @Test
    public void getDepartmentSalaryMinTest1(){//тест первого департамента
        Assertions.assertEquals(1000, departmentService.getDepartmentSalaryMin(1));
    }
    @Test
    public void getDepartmentSalaryMinTest2(){//тест второго департамента
        Assertions.assertEquals(6000, departmentService.getDepartmentSalaryMin(2));
    }
    @Test
    public void getDepartmentSalaryMinTest3(){//тест несуществующего департамента
        Assertions.assertEquals(0, departmentService.getDepartmentSalaryMin(3));
    }

    //getDepartmentSalaryMax возвращает максимальную зарплату по департаменту
    @Test
    public void getDepartmentSalaryMaxTest1(){//тест первого департамента
        Assertions.assertEquals(3000, departmentService.getDepartmentSalaryMax(1));
    }
    @Test
    public void getDepartmentSalaryMaxTest2(){//тест второго департамента
        Assertions.assertEquals(10000, departmentService.getDepartmentSalaryMax(2));
    }
    @Test
    public void getDepartmentSalaryMaxTest3(){//тест несуществующего департамента
        Assertions.assertEquals(0, departmentService.getDepartmentSalaryMax(3));
    }

    //getEmployeesByDepartment() - возвращает сотрудников, сгруппированых по отделам в виде Map<Integer,List<Employees>>,
    //где ключ — это номер отдела, а значение — список сотрудников данного отдела.
    @Test
    public void getEmployeesByDepartmentTest(){
        Assertions.assertEquals(expextedMap, departmentService.getEmployeesByDepartment());
    }
}
