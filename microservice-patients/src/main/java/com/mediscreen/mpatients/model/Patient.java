package com.mediscreen.mpatients.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer patientId;
    @Column(length = 50)
    @NotBlank(message = "Family name is mandatory")
    private String family;
    @Column(length = 50)
    @NotBlank(message = "Given name is mandatory")
    private String given;
    @NotNull
    private LocalDate dob;
    private boolean sex;
    @Column(length = 250)
    private String address;
    @Column(length = 20)
    private String phone;

    public Patient() {
    }

    public Patient(String family, String given, @NotNull LocalDate dob, char sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        setSex(sex);
        this.address = address;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public @NotNull LocalDate getDob() {
        return dob;
    }

    public void setDob(@NotNull LocalDate dob) {
        this.dob = dob;
    }

    public char getSex() {
        return sex ? 'M' : 'F';
    }

    public void setSex(char sex) {
        this.sex = sex != 'F';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
