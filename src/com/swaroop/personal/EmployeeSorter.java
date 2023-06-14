package com.swaroop.personal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {
    public static void main(String[] args) {
        // Creating example input values
        Department department1 = new Department(1, "2023-05-01T09:00:00");
        Department department2 = new Department(2, "2023-04-15T13:30:00");
        Department department3 = new Department(3, "2023-05-20T10:45:00");
        List<Department> departments1 = new ArrayList<>();
        departments1.add(department1);
        departments1.add(department2);
        departments1.add(department3);
        Employee employee1 = new Employee("John Doe", 5000.0, departments1);

        Department department4 = new Department(4, "2023-05-10T14:15:00");
        Department department5 = new Department(5, "2023-03-25T11:30:00");
        List<Department> departments2 = new ArrayList<>();
        departments2.add(department4);
        departments2.add(department5);
        Employee employee2 = new Employee("Jane Smith", 6000.0, departments2);

        // Creating the list of employees
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        // Sort employees based on department date
        employees.sort(Comparator.comparing(Employee::getLatestDepartmentDate));

        // Print the sorted list of employees
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}

class Employee {
    private String name;
    private double salary;
    private List<Department> departments;

    public Employee(String name, double salary, List<Department> departments) {
        this.name = name;
        this.salary = salary;
        this.departments = departments;
    }

    public String getLatestDepartmentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime latestDate = LocalDateTime.MIN;

        for (Department department : departments) {
            LocalDateTime departmentDate = LocalDateTime.parse(department.getDate(), formatter);
            if (departmentDate.isAfter(latestDate)) {
                latestDate = departmentDate;
            }
        }

        return latestDate.format(formatter);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", departments=" + departments +
                '}';
    }
}

class Department {
    private int id;
    private String date;

    public Department(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
