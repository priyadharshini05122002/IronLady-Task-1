package com.ironlady.internal_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "learners")
public class Learner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int learnerId;

    private String fullName;
    private String email;
    private String phone;
    private String background;
    private String status;

    // Constructors
    public Learner() {
    }

    public Learner(String fullName, String email, String phone, String background, String status) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.background = background;
        this.status = status;
    }

    // Getters & Setters
    public int getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(int learnerId) {
        this.learnerId = learnerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}