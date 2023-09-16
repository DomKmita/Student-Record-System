package com.example.assignment1.Model;
// Student object class

public class Student{

    // instance variables
    private String name;
    private String stdID;
    private String DOB;
    private String semester;

    /**
     * Default constructor for student object.
     */
    // default constructor
    public Student() {
        this.name = null;
        this.stdID = null;
        this.DOB = null;
        this.semester = null;
    }

    /**
     * Constructor for student object.
     * @param name name of student.
     * @param stdID Student ID.
     * @param DOB Student date of birth.
     */
    // constructor
    public Student(String name, String stdID, String DOB, String semester) {
        this();
        this.name = name;
        this.stdID = stdID;
        this.DOB = DOB;
        this.semester = semester;

    }

    /**
     * @return Student name.
     */
    // Getters
    public String getName() {
        return name;
    }

    /**
     * @return Student ID.
     */
    public String getStdID() {
        return stdID;
    }

    /**
     * @return Student date of birth.
     */
    public String getDOB() {
        return DOB;
    }


    /**
     * @return semester of student.
     */
    public String getSemester() {return semester;}

    /**
     * @param newName updated student name.
     */
    // Setters
    public void setName(String newName) {
        name = newName;
    }

    /**
     * @param newID updated student ID.
     */
    public void setStdID(String newID) {
        stdID = newID;
    }

    /**
     * @param newDOB updated student date of birth.
     */
    public void setDOB(String newDOB) {
        DOB = newDOB;
    }

    /**
     * @param newSemester updated semester of student.
     */
    public void setSemester(String newSemester) {semester = newSemester;}


    // Extras

    /**
     * @return Student object toString.
     */
    @Override
    public String toString() {
        return "Student name: " + name + "\nStudent ID: " + stdID + "\nStudent DOB: " + DOB + "\n" + semester + "\n\n\n";
    }
}