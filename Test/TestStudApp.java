import com.example.assignment1.Model.Student;
import com.example.assignment1.Model.Module;
import com.example.assignment1.dbConnection;
import com.example.assignment1.Validator.Validator;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestStudApp {

    private Validator valid = new Validator();

    /**
     * tests date validation
     */
    @Test
    public void TestValiDate() {

        // check if correct format works
        String goodDate = "01/01/1999";
        // check if impossible date returns false
        String badDate = "31/31/1999";
        // check lower bound
        String tooLowDate = "01/01/1000";
        // check upper bound
        String tooHighDate = "01/01/2300";
        // check value other than date
        String notDate = "This is a date ;)";
        //check backwards date
        String backwardsDate = "1999/01/01";

        assertTrue(valid.valiDate(goodDate));
        assertFalse(valid.valiDate(badDate));
        assertFalse(valid.valiDate(tooLowDate));
        assertFalse(valid.valiDate(tooHighDate));
        assertFalse(valid.valiDate(notDate));
        assertFalse(valid.valiDate(backwardsDate));

    }

    /**
     * Tests students exists validation
     */
    @Test
    public void testStudentExists() {

        // make student and add to list
        Student tempStud = new Student("Dom", "RTEST", "29/07/1999", "Semester 2");

        // add student to db
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("INSERT INTO students (id, name, dob, semester) VALUES (?,?,?,?)");
                st.setString(1, tempStud.getStdID());
                st.setString(2, tempStud.getName());
                st.setString(3, tempStud.getDOB());
                st.setString(4, tempStud.getSemester());
                st.executeUpdate();
            } else {
                System.out.println("DB Connection Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

            try {
                assertTrue(valid.studentExists("RTEST"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                assertFalse(valid.studentExists("RTEST2"));
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                conn = dbConnection.getConnection();
                if (conn != null) {
                    st = conn.prepareStatement("DELETE FROM students WHERE id=?");
                    st.setString(1, tempStud.getStdID());
                    st.executeUpdate();
                } else {
                    System.out.println("DB Connection Error");
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
                    if (st != null)
                        st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                assertFalse(valid.studentExists("RTEST"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tests grade validation
     */
    @Test
    public void TestGradeValidation() {
       assertTrue(valid.gradeValidation("99.0"));
       assertFalse(valid.gradeValidation("-1.0"));
       assertFalse(valid.gradeValidation("101.0"));
       assertFalse(valid.gradeValidation("BOOGA"));
    }

    /**
     * Tests module exist validation
     */
    @Test
    public void TestModuleExists() {
        Module tempModule = new Module("TEST", "TEST", "Semester 5");


        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("INSERT INTO modules (code, name, semester) VALUES (?,?,?)");
                st.setString(1, tempModule.getCode());
                st.setString(2, tempModule.getModuleName());
                st.setString(3, tempModule.getSemester());

                st.executeUpdate();

            } else {
                System.out.println("DB Connection Error");
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
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            assertTrue(valid.moduleExists("TEST"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            assertFalse(valid.moduleExists("TEST2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("DELETE FROM modules WHERE code=?");
                st.setString(1, tempModule.getCode());
                st.executeUpdate();
            } else {
                System.out.println("DB Connection Error");
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
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            assertFalse(valid.moduleExists("CRN01"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests module exist validation
     */
    @Test
    public void TestGradeExists() {
        String stdId = "R0023023";
        String code = "CRN01";
        String grade = "100";

        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("INSERT INTO grades (stdID, code, grade) VALUES (?,?,?)");
                st.setString(1, stdId);
                st.setString(2, code);
                st.setString(3, grade);

                st.executeUpdate();
            } else {
                System.out.println("DB Connection Error");
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
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            assertTrue(valid.gradeExists("CRN01", "R0023023"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            assertFalse(valid.gradeExists("CRN02","R0023023"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            assertFalse(valid.gradeExists("CRN01","R000000"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            conn = dbConnection.getConnection();
            if (conn != null) {
                st = conn.prepareStatement("DELETE FROM grades WHERE code=? AND stdID =?");
                st.setString(1, code);
                st.setString(2, stdId);
                st.executeUpdate();
            } else {
                System.out.println("DB Connection Error");
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
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            assertFalse(valid.gradeExists("CRN01", "R0023023"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
