package com.example.a2105project.Entity;

public class Account {
    private String ID;
    private String Role;
    private String Email;
    private String Pwd;
    private String Status = "Active";

    public Account() {
    }

    public Account(String role, String email, String pwd) {
        this.Role = role;
        this.Email = email;
        this.Pwd = pwd;
    }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

    public String getStatus() {return Status;}

    public void setStatus(String status) {this.Status = status;}

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
                " Role='" + Role + '\'' +
                ", Email='" + Email + '\'' +
                ", Pwd='" + Pwd + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
