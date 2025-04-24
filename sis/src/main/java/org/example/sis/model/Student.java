package org.example.sis.model;

public class Student {
    private int id;
    private String studentCode;
    private String fullName;
    private String address;

    public Student() {}

    public Student(int id, String studentCode, String fullName, String address) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.address = address;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
