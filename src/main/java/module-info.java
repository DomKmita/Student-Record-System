module com.example.assignment1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.assignment1 to javafx.fxml;
    exports com.example.assignment1;
    exports com.example.assignment1.Model;
    opens com.example.assignment1.Model to javafx.fxml;
    exports com.example.assignment1.Validator;
    opens com.example.assignment1.Validator to javafx.fxml;
}