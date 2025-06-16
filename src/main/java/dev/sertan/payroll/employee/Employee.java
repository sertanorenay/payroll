package dev.sertan.payroll.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@Data
public class Employee {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String role;

    public Employee() {}

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Following getName & setName methods are exist for possible clients
    // that might use 'name' attribute rather than firstName & lastName.

    public void setName(String string) {
        String[] name = string.split(" ");
        this.firstName = name[0];
        this.lastName = name[1];
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}
