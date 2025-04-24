package org.example.sis.model;

public class Subject {
    private int id;
    private String subjectCode;
    private String subjectName;
    private int credit;

    public Subject() {}

    public Subject(int id, String subjectCode, String subjectName, int credit) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
}
