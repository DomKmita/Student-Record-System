package com.example.assignment1;

import com.example.assignment1.Model.Grade;
import com.example.assignment1.Model.Module;
import com.example.assignment1.Model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
public class View {
    protected ComboBox<String> semesterChoiceTab2 = new ComboBox<>();
    protected ComboBox<String> studentChoiceTab3 = new ComboBox<>();
    protected Alert alert = new Alert(AlertType.INFORMATION);
    protected TextField nameField = new TextField();
    protected Label nameLabel = new Label("Enter name");
    protected TextField IDField = new TextField();
    protected Label IDLabel = new Label("Enter StudentID");
    protected TextField DOBField = new TextField();
    protected Label DOBLabel = new Label("Enter date of birth");
    protected ComboBox<String> semesterChoiceTab1 = new ComboBox<>();
    protected Label semesterLabel = new Label("Enter Semester");
    protected Button addButton = new Button("Add");
    protected Button removeButton = new Button("Remove");
    protected Button loadButton = new Button("Load");
    protected Button stdExitButton = new Button("Exit");
    protected TableView<Student> studentTable = new TableView<>();
    protected HBox inputHBox;
    protected HBox outputHBox;
    protected GridPane gridPaneStudent = new GridPane();
    protected TextField modNameField = new TextField();
    protected Label modNameLabel = new Label("Enter Module Name");
    protected TextField codeField = new TextField();
    protected Label codeLabel = new Label("Enter code");
    protected TableView<Module> moduleTable = new TableView<>();
    protected Button addModuleButton = new Button("Add");
    protected Button removeModuleButton = new Button("Remove");
    protected Button loadModuleButton = new Button("Load");
    protected GridPane gridPaneModule = new GridPane();
    protected Button modExitButton = new Button("Exit");
    protected VBox modNameBox;
    protected VBox codeBox;
    protected HBox modBtnBox;
    protected ComboBox<String> moduleChoiceTab3 = new ComboBox<>();
    protected TextField gradeField = new TextField();
    protected Button addGradeBtn = new Button("Add");
    protected Button updateGradeBtn = new Button("Update");
    protected Button removeGradeBtn = new Button("Remove");
    protected Button loadGradeBtn = new Button("Load");
    protected Button gradeExitBtn = new Button("Exit");
    protected HBox gradeBtnBox;
    protected TableView<Grade> gradeTable = new TableView<>();
    protected GridPane gridPaneGrades = new GridPane();
    protected TilePane pane = new TilePane();
    protected RadioButton r1 = new RadioButton("Show only passed modules");
    protected Label tab3DOBLabel = new Label();
    protected Label tab3StudSemesterLabel = new Label();
    protected Label tab3ModSemesterLabel = new Label();
    protected HBox studentLabelBox;
    protected HBox moduleLabelBox;
    protected Button memTestBtn = new Button("Memory Leak Test");
    protected Button memTestExitButton = new Button("Exit");
    protected VBox memTestBox = new VBox();
    protected TabPane tabpane = new TabPane();
    protected Scene scene;
    protected Controller control = new Controller(this);

    /**
     * GUI object.
     */
    public View() {

        // ------
        // TAB 1
        // ------

        // adding prompts to text fields
        nameField.setPromptText("name");
        IDField.setPromptText("R00XXXXXX");
        DOBField.setPromptText("dd/MM/yyyy");

        // display area for student information to be loaded. Not sure yet how to make one without input.

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        TableColumn<Student, String> stdIDCol = new TableColumn<>("Student Number");
        TableColumn<Student, String> dobCol = new TableColumn<>("Date of birth");
        TableColumn<Student, String> semesterCol = new TableColumn<>("Semester");


        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stdIDCol.setCellValueFactory(new PropertyValueFactory<>("stdID"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        studentTable.getColumns().addAll(nameCol, stdIDCol,dobCol, semesterCol);
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Hboxes used to organise buttons in groups of 3.
        inputHBox = new HBox(addButton, removeButton);
        inputHBox.setAlignment(Pos.CENTER_LEFT);
        outputHBox = new HBox(loadButton, stdExitButton);
        outputHBox.setAlignment(Pos.CENTER_RIGHT);

        // -------------
        // TAB 2
        // -------------

        modNameField.setPromptText("Module");
        codeField.setPromptText("CRN00");

        // display area for module information to be loaded. Not sure yet how to make one without input.

        TableColumn<Module, String> modNameCol = new TableColumn<>("Name");
        TableColumn<Module, String> codeCol = new TableColumn<>("Code");
        TableColumn<Module, String> modSemesterCol = new TableColumn<>("Semester");


        modNameCol.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        modSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        moduleTable.getColumns().addAll(modNameCol, codeCol, modSemesterCol);
        moduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        modNameBox = new VBox(modNameLabel, modNameField);
        codeBox = new VBox(codeLabel, codeField);
        modBtnBox = new HBox(addModuleButton, loadModuleButton, removeModuleButton);

        // -------------
        // TAB 3
        // -------------
        studentChoiceTab3.setValue("Student");
        moduleChoiceTab3.setValue("Module");
        gradeField.setPromptText("0");

        tab3DOBLabel.setText("");
        tab3StudSemesterLabel.setText("");
        tab3ModSemesterLabel.setText("");

        pane.getChildren().add(r1);

        studentLabelBox = new HBox(studentChoiceTab3,tab3DOBLabel, tab3StudSemesterLabel);
        moduleLabelBox = new HBox(moduleChoiceTab3, tab3ModSemesterLabel);

        gradeBtnBox = new HBox(addGradeBtn, updateGradeBtn, removeGradeBtn);

        // display area for student grade information to be loaded.

        TableColumn<Grade, String> gradeModNameCol = new TableColumn<>("Module Name");
        TableColumn<Grade, String> gradeCol = new TableColumn<>("Grade");
        TableColumn<Grade, String> gradeSemesterCol = new TableColumn<>("semester");


        gradeModNameCol.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        gradeTable.getColumns().addAll(gradeModNameCol, gradeCol, gradeSemesterCol);
        gradeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // -------------
        // TAB 4
        // -------------
        memTestBox.getChildren().addAll(memTestBtn, memTestExitButton);
        memTestBtn.setAlignment(Pos.CENTER);

        setup();
    }

    /**
     * sets up all grid panes tabs button event handling and scene.
     */
    public void setup() {
        // loading dropdown choices
        studentChoiceTab3.getItems().clear();
        studentChoiceTab3.getItems().addAll(control.loadChoice("students"));

        moduleChoiceTab3.getItems().clear();
        moduleChoiceTab3.getItems().addAll(control.loadChoice("modules"));

        addButton.setOnAction(e -> {
            control.add();

            // updating tab 3 options
            studentChoiceTab3.getItems().clear();
            studentChoiceTab3.getItems().addAll(control.loadChoice("students"));
        });

        removeButton.setOnAction(e -> {;
            control.remove();

            // need to remove everything first then add again as I don't want to append the same data again
            studentChoiceTab3.getItems().clear();
            studentChoiceTab3.getItems().addAll(control.loadChoice("students"));
        });

        loadButton.setOnAction(e -> {
            control.load("students");
        });

        stdExitButton.setOnAction(e -> {
            control.exit();
        });

        addModuleButton.setOnAction(e -> {
            control.addModule();

            // updating tab 3 options
            moduleChoiceTab3.getItems().clear();

            moduleChoiceTab3.getItems().addAll(control.loadChoice("modules"));
        });

        loadModuleButton.setOnAction( e -> {
            control.load("modules");
        });

        removeModuleButton.setOnAction( e -> {
            control.removeModule();
        });

        modExitButton.setOnAction(e -> {
            control.exit();
        });

        studentChoiceTab3.setOnAction(e -> {
            control.labelLoad(true);
            control.loadGrade();
        });

        moduleChoiceTab3.setOnAction(e -> {
            control.labelLoad(false);
        });

        addGradeBtn.setOnAction(e -> {
            control.addGrade();
        });

        updateGradeBtn.setOnAction(e -> {
            control.updateGrade();
        });

        removeGradeBtn.setOnAction(e -> {
            control.removeGrade();
        });

        loadGradeBtn.setOnAction( e -> {
            control.loadGrade();
        });

        gradeExitBtn.setOnAction(e -> {
            control.exit();
        });

        memTestBtn.setOnAction(e -> {
            control.memTest();
        });

        memTestExitButton.setOnAction(e -> {
            control.exit();
        });


        // first row - name field
        gridPaneStudent.add(nameLabel,0,0,1,1);
        gridPaneStudent.add(nameField,1,0,1,1);

        // second row - ID field
        gridPaneStudent.add(IDLabel,0,1,1,1);
        gridPaneStudent.add(IDField,1,1,1,1);

        // third row - DOB field
        gridPaneStudent.add(DOBLabel,0,2,1,1);
        gridPaneStudent.add(DOBField,1,2,1,1);

        gridPaneStudent.add(semesterLabel, 0,3,2,1);
        gridPaneStudent.add(semesterChoiceTab1, 1,3,2,1);

        // fourth row - first set of buttons. Add, remove and list current session.
        gridPaneStudent.add(inputHBox,0,4,2,1);

        // fifth row - Student list display text field
        gridPaneStudent.add(studentTable, 0,5,6,1);

        // sixth row - second set of buttons. DB load, save and exit.
        gridPaneStudent.add(outputHBox,0,6,2,1);
//

        // some light styling. Will eventually use CSS or FMXL I presume.
        gridPaneStudent.setPadding(new Insets(10, 10, 10, 10));
        gridPaneStudent.setHgap(10);
        gridPaneStudent.setVgap(20);
        gridPaneStudent.setAlignment(Pos.CENTER);

        gridPaneModule.add(modNameBox, 0, 0, 1,1);
        gridPaneModule.add(codeBox, 0, 1, 1,1);
        gridPaneModule.add(semesterChoiceTab2,0,2,2,1);
        gridPaneModule.add(modBtnBox, 0, 3, 1,1);
        gridPaneModule.add(moduleTable, 0, 4, 6,1);
        gridPaneModule.add(modExitButton, 0, 5, 1,1);

        gridPaneModule.setPadding(new Insets(10, 10, 10, 10));
        gridPaneModule.setHgap(10);
        gridPaneModule.setVgap(20);
        gridPaneModule.setAlignment(Pos.CENTER);

//        gridPaneGrades.add(studentLabelBox, 0,0,1,1);
//        gridPaneGrades.add(moduleLabelBox, 0,1,1,1);
        gridPaneGrades.add(studentChoiceTab3, 0,0,1,1);
        gridPaneGrades.add(tab3DOBLabel, 1,0,1,1);
        gridPaneGrades.add(tab3StudSemesterLabel, 2,0,1,1);
        gridPaneGrades.add(moduleChoiceTab3, 0,1,1,1);
        gridPaneGrades.add(tab3ModSemesterLabel, 1,1,2,1);
        gridPaneGrades.add(gradeField, 0,2,3,1);
        gridPaneGrades.add(gradeBtnBox, 0,3,3,1);
        gridPaneGrades.add(pane, 0,4,3,1);
        gridPaneGrades.add(loadGradeBtn, 0,5,3,1);
        gridPaneGrades.add(gradeTable, 0,6,3,1);
        gridPaneGrades.add(gradeExitBtn, 0,7,3,1);

        gridPaneGrades.setPadding(new Insets(10, 10, 10, 10));
        gridPaneGrades.setHgap(10);
        gridPaneGrades.setVgap(20);
        gridPaneGrades.setAlignment(Pos.CENTER);

        // making sure user can't close tabs
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // create Tab
        Tab tab1 = new Tab("Students");
        Tab tab2 = new Tab("Modules");
        Tab tab3 = new Tab("Grades");
        Tab tab4 = new Tab("Memory Leak Test");

        // add content
        tab1.setContent(gridPaneStudent);
        tab2.setContent(gridPaneModule);
        tab3.setContent(gridPaneGrades);
        tab4.setContent(memTestBox);

        // add tab to pane
        tabpane.getTabs().addAll(tab1, tab2, tab3, tab4);

        // Finally setting the scene to load GUI.
        scene = new Scene(tabpane, 450, 500);
        // setting table height property. Has to be done after scene is created.

    }

}

