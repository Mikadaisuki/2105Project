package com.example.a2105project.Account;

public class Account {
    private String Role;
    private String Email;
    private String Pwd;

    public Account() {
    }

    public Account(String role, String email, String pwd) {
        this.Role = role;
        this.Email = email;
        this.Pwd = pwd;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setPwd(String pwd) {
        this.Pwd = pwd;
    }

    public String getRole() {return Role;}

    public String getEmail() {
        return Email;
    }

    public String getPwd() {
        return Pwd;
    }

    @Override
    public String toString() {
        return "Account{" +
                "role='" + Role + '\'' +
                ", Email='" + Email + '\'' +
                ", Pwd='" + Pwd + '\'' +
                '}';
    }
}
