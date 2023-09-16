package com.example.assignment1.Model;
// Grade object class

// I had to structure grade to contain both module and student inside of it due to the fact that for the format I chose
// in the third tab I required a join across all three tables in the sqlDB. This object needed to be able to contain
// all of the information across all three tables unfortunately.
public class Grade{

    // instance variables
    private Student stud;
    private Module mod;

    // grade is a String to allow for NP value
    private String grade;

    /**
     * Default constructor for grade object.
     */
    // default constructor
    public Grade() {
        this.stud = new Student();
        this.mod = new Module();
        this.grade = null;
    }

    /**
     * Constructor for grade object.
     * @param code crn code for module.
     * @param stdID Student ID.
     * @param grade student grade for specific module.
     */
    // constructor
    public Grade(String stdID, String code, String grade) {
        this();
        this.stud.setStdID(stdID);
        this.mod.setCode(code);
        this.grade = grade;

    }

    /**
     * @return Student id.
     */
    // Getters
    public String getStdID() {
        return stud.getStdID();
    }

    public String getSemester(){ return stud.getSemester();}

    /**
     * @return module code.
     */
    public String getCode() {
        return mod.getCode();
    }

    /**
     * @return module name
     */
    public String getModuleName() {
        return mod.getModuleName();
    }


    /**
     * @return grade for specific student and module.
     */
    public String getGrade() {return grade;}

    /**
     * @param newID updated student ID.
     */
    public void setStdID(String newID) {
        stud.setStdID(newID);
    }

    /**
     * @param newSemester updated semester for module
     */
    public void setSemester(String newSemester) {
        stud.setSemester(newSemester);
    }

    /**
     * @param newCode updated module code.
     */
    public void setCode(String newCode) {
        mod.setCode(newCode);
    }

    /**
     * @param newModuleName updated module name
     */
    public void setModuleName(String newModuleName) {
        mod.setModuleName(newModuleName);
    }

    /**
     * @param newGrade updated grade for student's module.
     */
    public void setGrade(String newGrade) {grade = newGrade;}


    // Extras

    /**
     * @return Student object toString.
     */
    @Override
    public String toString() {
        return"Student ID: " + stud.getStdID() + "\nModule code: " + mod.getCode() + "\nGrade: " + grade + "\n\n\n";
    }
}