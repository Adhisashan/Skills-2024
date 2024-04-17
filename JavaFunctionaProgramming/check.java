/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FunctionalProgrammingCustomclass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Dell
 */

    
    

class Employee {
    private String name;
    private int emp_id;
    private String department;
    private double salary;

    public Employee(String name, int emp_id, String department, double salary) {
        this.name = name;
        this.emp_id = emp_id;
        this.department = department;
        this.salary = salary;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", emp_id=" + emp_id +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class check {
    public static void main(String[] args) {
        // Sample list of Employee objects
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 101, "IT", 60000));
        employees.add(new Employee("Bob", 102, "HR", 55000));
        employees.add(new Employee("Alice", 103, "IT", 62000));
        employees.add(new Employee("Charlie", 104, "Finance", 70000));
        employees.add(new Employee("Bob", 105, "HR", 58000));

        // Filter and collect employees by department, excluding duplicates
        String departmentToFilter = "IT";
        List<Employee> filteredEmployees = employees.stream()
                .filter(emp -> emp.getDepartment().equals(departmentToFilter))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new ArrayList<>()),
                        list -> list.stream()
                                .filter(emp -> list.stream()
                                        .filter(innerEmp -> innerEmp.getName().equals(emp.getName()))
                                        .count() == 1)
                                .collect(Collectors.toList())
                ));

        // Print the filtered list
        filteredEmployees.forEach(System.out::println);
    }
}

    

