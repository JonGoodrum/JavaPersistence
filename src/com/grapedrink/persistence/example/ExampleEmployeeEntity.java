package com.grapedrink.persistence.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.grapedrink.persistence.entities.AbstractEntity;

@Entity
@Table(name = "EMPLOYEE_TABLE_78Ggd782gKd3L82dHh7")
public class ExampleEmployeeEntity extends AbstractEntity {

    private static final long serialVersionUID = -8441910130150669132L;
    private String employeeName;
    private Integer employeeId;
    private Integer salary;

    public ExampleEmployeeEntity() {
    }

    public ExampleEmployeeEntity(String name, Integer salary) {
        this.employeeName = name;
        this.salary = salary;
    }

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getEmployeeId() {
        return employeeId;
    }

    @Column(name = "EMPLOYEE_NAME", nullable = true)
    public String getEmployeeName() {
        return employeeName;
    }

    @Column(name = "SALARY", nullable = true)
    public Integer getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Salary: $%d", employeeId, employeeName, salary);
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
