package com.example.assignment1.Validator;

import com.example.assignment1.dbConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Validator {

    /**
     * Constructor of validator class
     */
    public Validator() {}

    /**
     * @param dob date provided in string format
     * @return boolean whether dob is a valid date between 01/01/2005 and 01/01/1900.
     */
    public boolean valiDate(String dob) {
        Date date;
        SimpleDateFormat dateFormat;
        Date upperBound;
        Date lowerBound;

        // Check if date is 'null'
        if (!dob.trim().isEmpty()) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            // Create Date object
            // parse the string into date

            try {
                date = dateFormat.parse(dob);
                upperBound = dateFormat.parse("01/01/2007");
                lowerBound = dateFormat.parse("01/01/1900");
            } catch (ParseException e) {
                return false;
            }
            return !(date.after(upperBound) || date.before(lowerBound));
        } else
            return false;
    }

    /**
     * @param ID Student ID
     * @return whether the students with this id exists in the DB
     * @throws SQLException
     */
    public boolean studentExists(String ID) throws SQLException{
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("Select id FROM students WHERE id = ?");
                if (st != null) {
                    st.setString(1, ID);
                    rs = st.executeQuery();
                    return !Objects.equals(rs.getString("id"), null) || !Objects.equals(rs.getString("id"), "");

                }
            }
        } finally {
            if (conn != null)
                conn.close();
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
        }
        return false;
    }

    /**
     * @param inputGrade grade
     * @return whether the grade is valid
     */
    public boolean gradeValidation(String inputGrade) {
        if (Objects.equals(inputGrade, "NP"))
            return true;

        double grade = 0.0;
        try {
            grade = Double.parseDouble(inputGrade);
        } catch (NumberFormatException e) {
            return false;
        }

        return grade >= 0.0 && grade <= 100.0;

    }

    /**
     * @param code module code
     * @return whether a module exists in DB
     * @throws SQLException
     */
    public boolean moduleExists(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("Select code FROM modules WHERE code = ?");
                if (st != null) {
                    st.setString(1, code);
                    rs = st.executeQuery();
                    return !Objects.equals(rs.getString("code"), null) || !Objects.equals(rs.getString("code"), "");
                }
            }
        } finally {
            if (conn != null)
                conn.close();

            if (rs != null)
                rs.close();

            if (st != null)
                st.close();
        }
        return false;
    }

    /**
     * @param code module code
     * @param stdID student ID
     * @return whether a grade exists in DB
     * @throws SQLException
     */
    public boolean gradeExists(String code, String stdID) throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("Select grade FROM grades WHERE code = ? AND stdID = ?");
                if (st != null) {
                    st.setString(1, code);
                    st.setString(2, stdID);
                    rs = st.executeQuery();
                    return !Objects.equals(rs.getString("grade"), null) || !Objects.equals(rs.getString("grade"), "");
                }
            }
        } finally {
            if (conn != null)
                conn.close();

            if (rs != null)
                rs.close();

            if (st != null)
                st.close();
        }
        return false;
    }


}
