package com.example.assignment1;

import com.example.assignment1.Model.Grade;
import com.example.assignment1.Model.Student;
import com.example.assignment1.Model.Module;
import com.example.assignment1.Validator.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {

    // instance variables
    View view;

    Validator validator;

    /**
     * @param view GUI object needs to be passed to controller as both view and controller have references to each-other.
     */
    // constructor
    // no parameters as all we need are the methods
    public Controller(View view) {
        this.view = view;
        this.validator = new Validator();
        // seeing as semesters are static we add once and never update
        this.view.semesterChoiceTab1.getItems().addAll("Semester 1",
                "Semester 2", "Semester 3", "Semester 4", "Semester 5",
                "Semester 6", "Semester 7", "Semester 8");
        this.view.semesterChoiceTab2.getItems().addAll("Semester 1",
                "Semester 2", "Semester 3", "Semester 4", "Semester 5",
                "Semester 6", "Semester 7", "Semester 8");

        this.loadChoice("students");
        this.loadChoice("modules");
    }


    /**
     * function that adds student to list in memory. Currently, does not save automatically to allow user to backtrack
     *      changes they made.
     */
    public void add() {

        String name = view.nameField.getText();
        String stdID = view.IDField.getText();
        String DOB = view.DOBField.getText();
        String semester = view.semesterChoiceTab1.getValue();

        // make sure student not in list
        try {
            if (!validator.studentExists(stdID)) {
                view.alert.setContentText("Student already in database");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        }

        // make sure date is valid
        if (!validator.valiDate(DOB)) {
            view.alert.setContentText("invalid date");
            view.alert.show();
            return;
        }

        // make sure an option from semester choiceBox is used
        if (semester == null) {
            view.alert.setContentText("Please choose a semester");
            view.alert.show();
            return;
        }

        // add student to db
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("INSERT INTO students (id, name, dob, semester) VALUES (?,?,?,?)");
                st.setString(1, stdID);
                st.setString(2, name);
                st.setString(3, DOB);
                st.setString(4, semester);
                st.executeUpdate();
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
        } finally {
            // I have a lot of try catches here. Could be avoided if the method threw the sql exception but then it
            // would need to be handled in the view and I think that may be an issue for mvc.
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * function that removes student from memory. Currently, it doesn't save to database and waits for user to
     * save their changes. This was a deliberate choice.
     */
    public void remove() {
        String id = view.IDField.getText();
        PreparedStatement st = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("DELETE FROM students WHERE id=?");
                st.setString(1, id);
                st.executeUpdate();
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            view.alert.setContentText("File not found");
            view.alert.show();
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param table table to connect to and load all data from it
     *
     */
    public void load(String table) {
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                rs = conn.createStatement().executeQuery("SELECT * FROM " + table);

                if (Objects.equals(table, "students")) {
                    ObservableList<Student> studList = FXCollections.observableArrayList();
                    while (rs.next()) {
                        studList.add(new Student(
                                rs.getString(2),
                                rs.getString(1),
                                rs.getString(3),
                                rs.getString(4)));
                    }

                    view.studentTable.setItems(studList);
                } else {
                    ObservableList<Module> modList = FXCollections.observableArrayList();
                    while (rs.next()) {
                        modList.add(new Module(
                                rs.getString(2),
                                rs.getString(1),
                                rs.getString(3)));
                    }
                    view.moduleTable.setItems(modList);
                }
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  exits program
     */
    public void exit() {
        Platform.exit();
    }

    /**
     * @return student name and ID for dropDown menus.
     */
    // function that loads students to drop down in tab 2
    public ObservableList<String> loadChoice(String table) {
        ResultSet rs = null;
        Connection conn = null;
        try {
            ObservableList<String> tempList = FXCollections.observableArrayList();
            conn = dbConnection.getConnection();
            if (conn != null) {
                rs = conn.createStatement().executeQuery("SELECT * FROM " + table);

                if (Objects.equals(table, "students")) {
                    while (rs.next()) {
                        String txt = rs.getString("name") + " " + rs.getString("id");
                        tempList.add(txt);
                    }
                } else {
                    while (rs.next()) {
                        String txt = rs.getString("name") + " " + rs.getString("code");
                        tempList.add(txt);
                    }
                }
                return tempList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.alert.setContentText("Something went wrong loading " + table);
        view.alert.show();
        return FXCollections.observableArrayList();
    }

    /**
     *  adds module to DB
     */
    public void addModule() {
        String modName = view.modNameField.getText();
        String code = view.codeField.getText();
        String semester = view.semesterChoiceTab2.getValue();

        try {
            if (!validator.moduleExists(code)) {
                view.alert.setContentText("Student already in database");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        }

        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("INSERT INTO modules (code, name, semester) VALUES (?,?,?)");
                st.setString(1, code);
                st.setString(2, modName);
                st.setString(3, semester);

                st.executeUpdate();

            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  removes module from DB
     */
    public void removeModule() {
        String code = view.codeField.getText();
        PreparedStatement st = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("DELETE FROM modules WHERE code=?");
                st.setString(1, code);
                st.executeUpdate();
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            view.alert.setContentText("File not found");
            view.alert.show();
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Adds grade to DB
     */
    public void addGrade() {
        String[] stdDropDown = view.studentChoiceTab3.getValue().split(" ", 2);
        String[] modDropDown = view.moduleChoiceTab3.getValue().split(" ", 2);
        String grade = view.gradeField.getText();

        try {
            if (!validator.gradeExists(modDropDown[1], stdDropDown[1])) {
                view.alert.setContentText("Grade already in database");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        }

        try {
            if (!validator.gradeValidation(grade)) {
                view.alert.setContentText("Grade must be a value between 0 and 100 or NP");
                view.alert.show();
                return;
            }
        } catch (NumberFormatException e) {
            view.alert.setContentText("Please enter a number");
            view.alert.show();
            return;
        }

        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;


        try {
            conn = dbConnection.getConnection();
            if (conn != null) {

                // we get the students current semester
                st = conn.prepareStatement("SELECT semester from students where id = ?");
                st.setString(1, stdDropDown[1]);
                rs = st.executeQuery();
                String studentSemester = rs.getString(1);
                String[] studSem = studentSemester.split(" ", 2);

                // we get the grades for this student
                st = conn.prepareStatement("SELECT  m.semester " +
                        "FROM grades g, modules m WHERE m.code = g.code" +
                        "AND g.stdID = ?");
                st.setString(1, stdDropDown[1]);
                rs = st.executeQuery();

                ArrayList<String> studentGradeSemesters = new ArrayList<>();
                ArrayList<String> studentGradeSemestersCurrent = new ArrayList<>();
                int counter = 1;
                while(rs.next())
                    if (!Objects.equals(rs.getString(counter), studentSemester))
                        studentGradeSemesters.add(rs.getString(counter++));
                    else
                        studentGradeSemestersCurrent.add(rs.getString(counter++));

                int numericalValueSemester = Integer.parseInt(studSem[1]);

                // if the previous semesters are missing grades we return error to user
                if ((numericalValueSemester - 1) * 6 != studentGradeSemesters.size()) {
                    view.alert.setContentText("Please ensure all previous semester grades added first before inserting new semester grades!");
                    view.alert.show();
                // if the current semester already has 6 grades then we return error
                } else if (studentGradeSemestersCurrent.size() >= 6) {
                    view.alert.setContentText("There are already 6 module grades added for this students " + studentSemester);
                    view.alert.show();
                } else {
                    st = conn.prepareStatement("INSERT INTO grades (stdID, code, grade) VALUES (?,?,?)");
                    st.setString(1, stdDropDown[1]);
                    st.setString(2, modDropDown[1]);
                    st.setString(3, grade);

                    st.executeUpdate();
                }
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
        } finally {
            // same as above many try catches.
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  updates grade in DB
     */
    public void updateGrade() {
        String[] stdDropDown = view.studentChoiceTab3.getValue().split(" ", 2);
        String[] modDropDown = view.moduleChoiceTab3.getValue().split(" ", 2);
        String grade = view.gradeField.getText();

        try {
            if (!validator.gradeExists(modDropDown[1], stdDropDown[1])) {
                view.alert.setContentText("Grade not in database");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        }

        try {
            if (!validator.gradeValidation(grade)) {
                view.alert.setContentText("Grade must be a value between 0 and 100 or NP");
                view.alert.show();
                return;
            }
        } catch (NumberFormatException e) {
            view.alert.setContentText("Please enter a number");
            view.alert.show();
            return;
        }

        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("UPDATE grades SET grade = ? WHERE stdID = ? AND code = ?");
                st.setString(1, grade);
                st.setString(2, stdDropDown[1]);
                st.setString(3, modDropDown[1]);

                st.executeUpdate();

            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Removes grade from DB
     */
    public void removeGrade() {
        String[] stdDropDown = view.studentChoiceTab3.getValue().split(" ", 2);
        String[] modDropDown = view.moduleChoiceTab3.getValue().split(" ", 2);

        PreparedStatement st = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("DELETE FROM grades WHERE stdID = ? AND code = ?");
                st.setString(1, stdDropDown[1]);
                st.setString(2, modDropDown[1]);
                st.executeUpdate();
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            view.alert.setContentText("File not found");
            view.alert.show();
            return;
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Loads grades from DB
     */
    public void loadGrade() {
        String[] stdDropDown = view.studentChoiceTab3.getValue().split(" ", 2);

        if (stdDropDown.length < 2) {
            view.alert.setContentText("Please choose a student to load");
            view.alert.show();
            return;
        }

        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("""
                        SELECT  m.name, g.grade, s.semester
                        FROM grades g, modules m, students s
                        WHERE m.code = g.code
                        AND s.id = g.stdID
                        AND s.id = ?""");
                st.setString(1, stdDropDown[1]);

                rs = st.executeQuery();

                ObservableList<Grade> gradeList = FXCollections.observableArrayList();
                while (rs.next()) {
                        Grade grade = new Grade();
                        grade.setModuleName(rs.getString(1));
                        grade.setGrade(rs.getString(2));
                        grade.setSemester(rs.getString(3));

                        // including condition for showing only passed modules
                        if (view.r1.isSelected())
                            if (Objects.equals(grade.getGrade(), "NP") || Double.parseDouble(grade.getGrade()) < 40.0)
                                continue;

                    gradeList.add(grade);
                }
                view.gradeTable.setItems(gradeList);
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
                return;
            }
        } catch (SQLException e) {
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
            return;
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @param choice boolean to decide whether the function loads information for students or modules in tab 3
     */
    public void labelLoad(boolean choice) {
        String[] stdDropDown = view.studentChoiceTab3.getValue().split(" ", 2);
        String[] modDropDown = view.moduleChoiceTab3.getValue().split(" ", 2);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                if (choice) {
                    st = conn.prepareStatement("SELECT dob, semester FROM students WHERE id = ?");
                    st.setString(1, stdDropDown[1]);
                    rs = st.executeQuery();
                    view.tab3DOBLabel.setText(rs.getString(1));
                    view.tab3DOBLabel.setVisible(true);

                    view.tab3StudSemesterLabel.setText(rs.getString(2));
                    view.tab3StudSemesterLabel.setVisible(true);

                } else {
                    st = conn.prepareStatement("SELECT semester FROM modules WHERE code = ?");
                    st.setString(1, modDropDown[1]);
                    rs = st.executeQuery();
                    view.tab3ModSemesterLabel.setText(rs.getString(1));
                    view.tab3ModSemesterLabel.setVisible(true);
                }
            } else {
                view.alert.setContentText("DB Connection Error");
                view.alert.show();
            }
        } catch (SQLException e) {
            view.alert.setContentText("SQL ERROR");
            view.alert.show();
        } finally {
            // same as above many try catches.
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Test for memory leak
     */
    public void memTest() {
        ArrayList<Student> studList = new ArrayList<>();
        while (true) {
            studList.add(new Student());
            System.out.println(studList.size());
        }
    }
}
