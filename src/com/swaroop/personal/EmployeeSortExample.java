import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee {
    private int id;
    private String name;
    private List<Department> departments;

    public Employee(int id, String name, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.departments = departments;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}

class Department {
    private int departmentId;
    private String departmentDate;

    public Department(int departmentId, String departmentDate) {
        this.departmentId = departmentId;
        this.departmentDate = departmentDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentDate() {
        return departmentDate;
    }
}

public class EmployeeSortExample {
    public static void main(String[] args) {
        // Create a list of employees
        List<Employee> employees = new ArrayList<>();

        // Create departments for employee 1
        List<Department> departments1 = new ArrayList<>();
        departments1.add(new Department(1, "2022-05-15T08:30:00"));
        departments1.add(new Department(2, "2021-08-10T13:45:00"));
        departments1.add(new Department(3, "2023-01-20T09:15:00"));
        employees.add(new Employee(1, "John Doe", departments1));

        // Create departments for employee 2
        List<Department> departments2 = new ArrayList<>();
        departments2.add(new Department(4, "2023-02-10T11:00:00"));
        departments2.add(new Department(5, "2023-01-05T14:20:00"));
        employees.add(new Employee(2, "Jane Smith", departments2));

        // Create departments for employee 3
        List<Department> departments3 = new ArrayList<>();
        departments3.add(new Department(6, "2023-04-20T16:10:00"));
        departments3.add(new Department(7, "2022-11-30T10:05:00"));
        departments3.add(new Department(8, "2023-06-01T13:30:00"));
        employees.add(new Employee(3, "Michael Johnson", departments3));

        // Sort employees based on the departments' date in descending order
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Collections.sort(employees, Comparator.comparing((Employee e) -> {
            List<Department> eDepartments = e.getDepartments();
            if (!eDepartments.isEmpty()) {
                // Sort departments in descending order by date and get the first (recent) department
                return eDepartments.stream()
                        .map(d -> LocalDateTime.parse(d.getDepartmentDate(), formatter))
                        .max(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.parse("1970-01-01T00:00:00"));
            }
            // If employee has no departments, return a default date in the past to place them at the beginning
            return LocalDateTime.parse("1970-01-01T00:00:00");
        }).reversed());

        // Display the sorted list of employees
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getId());
            System.out.println("Employee Name: " + employee.getName());
            System.out.println("Recent Department Date: " +
                    employee.getDepartments().stream()
                            .map(d -> LocalDateTime.parse(d.getDepartmentDate(), formatter))
                            .max(LocalDateTime::compareTo)
                            .orElse(null));
            System.out.println();
        }
    }
}
