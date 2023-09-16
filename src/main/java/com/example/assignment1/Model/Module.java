package com.example.assignment1.Model;
// Student object class

import java.io.Serializable;
import java.util.Comparator;

public class Module{

    // instance variables
    private String name;
//    private double grade;
    private String code;

    private String semester;

    /**
     *  default constructor for module.
     */
    public Module() {
        this.name = null;
//        this.grade = 0.0;
        this.code = null;
        this.semester = null;
    };

    /** constructor for module.
     * @param name module name.
     * @param code module code.
     * @param semester module semester.
     */
    public Module(String name, String code, String semester) {
        this.name = name;
//        this.grade = grade;
        this.code = code;
        this.semester = semester;
    }

    // Getters
    /**
     * @return module name.
     */
    public String getModuleName() {
        return name;
    }

    /**
     * @return module code.
     */
    public String getCode() {return code;}

    /**
     * @return module semester.
     */
    public String getSemester() {return semester;}

    // Setters
    /**
     * @param newName updated module name.
     */
    public void setModuleName(String newName) {
        name = newName;
    }

    /**
     * @param newCode updated module code.
     */
    public void setCode(String newCode) {code = newCode;}

    /**
     * @param newSemester updated module semester.
     */
    public void setSemester(String newSemester) {semester = newSemester;}

    /**
     * @return module object toString.
     */
    // Extras
    @Override
    public String toString() {
        return "Module name: " + name + "Module code: " + code + " " + semester + "%\n" ;
    }
}