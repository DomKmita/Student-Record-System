package com.example.assignment1;

import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("MTU Student Record System");
        View view = new View();
        stage.setScene(view.scene);
        stage.show();
    }

    /**
     * @param args Honestly I don't remember what args does. Just know launch(args) needed to start GUI.
     */
    public static void main(String[] args) {
        launch(args);
    }
}