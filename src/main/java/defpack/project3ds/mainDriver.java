package defpack.project3ds;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class mainDriver extends Application {
    final static String MAIN_FILES_PATH = "C:\\Users\\mctib\\eclipse-workspace\\Project3DS\\depFiles\\";
    final static AVLTree departmentsTree = new AVLTree();
    final static File departmentsFile = new File(MAIN_FILES_PATH + "departments.txt");


    final static String color_1 = "#ACBDBA";
    final static String color_2 = "#CDDDDD";
    final static String color_3 = "#2E2F2F";
    final static String color_4 = "#051014";

    Department selectedDepartment = null;
    Student selectedStudent = null;


    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: " + color_1);
        HBox departmentMenuBar = new HBox();
        HBox studentsMenuBar = new HBox();
        TextArea departmentTextArea = new TextArea();
        TextArea studentsTextArea = new TextArea();

        for (HBox H: Arrays.asList(departmentMenuBar,studentsMenuBar)){
            H.setStyle("-fx-background-color: " + color_3);
            H.setFillHeight(true);
            H.setPrefHeight(80);
            H.setSpacing(10);
        }
        for (TextArea T: Arrays.asList(departmentTextArea,studentsTextArea)){
            T.setStyle("-fx-control-inner-background: " + color_4 + "; -fx-highlight-fill: " + color_2 + "; "
                    + "-fx-highlight-text-fill: " + color_4 + "; -fx-text-fill: " + color_2 + "; "
                    + "-fx-border-color: " + color_4 + "; -fx-border-width: 1; ");
            T.setFont(Font.font("Consolas", 18) );
            T.setPrefHeight(320);
            T.setEditable(false);
        }
        departmentTextArea.setText(departmentsTree.toString());

        Label searchDepartment = new Label("Search");
        Label searchStudents = new Label("Search");
        TextField searchDepartmentsTextField = new TextField();
        TextField searchStudentsTextField = new TextField();
        VBox searchDepVBox = new VBox();
        VBox searchStdVBox = new VBox();
        for (Label L: Arrays.asList(searchDepartment,searchStudents)){
            L.setFont(Font.font("Consolas", 18) );
            L.setTextFill(Color.web(color_2));
        }
        for (VBox V: Arrays.asList(searchDepVBox,searchStdVBox)){
            V.setStyle("-fx-background-color: " + color_3);
            V.setFillWidth(true);
            V.setPrefWidth(240);
            V.setSpacing(10);
        }
        for (TextField T: Arrays.asList(searchDepartmentsTextField,searchStudentsTextField)){
            T.setStyle("-fx-control-inner-background: " + color_4 + "; -fx-highlight-fill: " + color_2 + "; "
                    + "-fx-highlight-text-fill: " + color_4 + "; -fx-text-fill: " + color_2 + "; "
                    + "-fx-border-color: " + color_4 + "; -fx-border-width: 1; ");
            T.setFont(Font.font("Consolas", 18) );
        }
        searchDepartmentsTextField.setOnAction(e -> {
            String key = searchDepartmentsTextField.getText();
            TreeNode current = departmentsTree.get(key);
            if (current != null)
                selectedDepartment = current.data;
            else {
                selectedDepartment = null;
                selectedStudent = null;
            }
            if (selectedDepartment != null){
                departmentTextArea.setText(selectedDepartment.toString());
                studentsTextArea.setText(selectedDepartment.students.printTable());
            }else {
                departmentTextArea.setText(departmentsTree.toString());
                studentsTextArea.setText("");
            }
        });
        searchStudentsTextField.setOnAction(e -> {
            if (selectedDepartment != null){
                String key = searchStudentsTextField.getText();
                selectedStudent = selectedDepartment.students.find(key);
                if (selectedStudent != null){
                    studentsTextArea.setText(selectedStudent.toString());
                }else {
                    if (selectedDepartment != null) {
                        studentsTextArea.setText(selectedDepartment.students.printTable());
                        System.out.println("Can't find a student with this name");
                    }else {
                        studentsTextArea.setText("");
                        selectedStudent = null;
                    }
                }
            }else {
                System.out.println("Select a department before searching for a student");
            }
        });
        searchDepVBox.getChildren().addAll(searchDepartment,searchDepartmentsTextField);
        searchStdVBox.getChildren().addAll(searchStudents,searchStudentsTextField);

        Button depBtn_insert = new Button("INSERT DEPARTMENT");
        Button depBtn_remove = new Button("DELETE DEPARTMENT");
        Button depBtn_treeHeight = new Button("TREE HEIGHT");
        Button stdBtn_insert = new Button("INSERT STUDENT");
        Button stdBtn_remove = new Button("DELETE STUDENT");
        Button stdBtn_size = new Button("HASH TABLE SIZE");
        Button stdBtn_hashUsed = new Button("HASH USED");
        Button depBtn_save = new Button("SAVE DEPARTMENTS");
        Button stdBtn_save = new Button("SAVE STUDENTS");

        for (Button b: Arrays.asList(depBtn_insert,depBtn_remove,depBtn_treeHeight,stdBtn_insert,stdBtn_remove,stdBtn_hashUsed
                ,stdBtn_size,stdBtn_save,depBtn_save)){
            b.setPrefHeight(80);
            b.setStyle("-fx-background-color: " + color_3);
            b.setTextFill(Color.web(color_2));
            b.setFont(Font.font("Consolas", 20) );
            b.setOnMouseEntered(e -> {
                b.setStyle("-fx-background-color: " + color_2 + " ; -fx-cursor: HAND");
                b.setFont(Font.font("Consolas", FontWeight.BOLD,20) );
                b.setTextFill(Color.web(color_3));
            });
            b.setOnMouseExited(e -> {
                b.setStyle("-fx-background-color: " + color_3);
                b.setFont(Font.font("Consolas", 20) );
                b.setTextFill(Color.web(color_2));
            });
        }

        Stage insertStudentStage = setUpInsertStudentStage(studentsTextArea);
        Stage insertDepartmentStage = setUpInsertDepartmentStage(departmentTextArea);

        stdBtn_insert.setOnAction(e -> {
            if (selectedDepartment != null)
                insertStudentStage.showAndWait();

        });
        depBtn_insert.setOnAction(e -> insertDepartmentStage.showAndWait());
        depBtn_remove.setOnAction(e -> {
            if (selectedDepartment == null)
                return;

            String key = selectedDepartment.getName();
            departmentsTree.delete(key);
            showAlert("Success","Deleted " + key + " From Tree");
            selectedDepartment = null;
            departmentTextArea.setText(departmentsTree.toString());
        });
        stdBtn_remove.setOnAction(e -> {
            if (selectedDepartment == null || selectedStudent == null)
                return;

            String key = selectedStudent.getFullName();
            selectedDepartment.students.delete(key);
            showAlert("Success","Deleted " + key + " From HashTable");
            selectedStudent = null;
            studentsTextArea.setText(selectedDepartment.students.printTable());
        });
        depBtn_treeHeight.setOnAction(e -> showAlert("Department Tree Height","Tree Height is " + departmentsTree.getTreeHeight()));
        stdBtn_size.setOnAction(e -> {
            if (selectedDepartment == null)
                return;
            showAlert("Student HashTable Size","HashTable Size Is " + selectedDepartment.students.getCapacity() + " For "
                    + selectedDepartment.getName());
        });
        stdBtn_hashUsed.setOnAction(e -> {
            if (selectedDepartment == null)
                return;
            if (selectedStudent == null)
                return;

            showAlert("Hash Used", "Hash Used For " + selectedStudent.getFullName() + " Is " +
                    selectedDepartment.students.hashUsed(selectedStudent.getFullName())) ;
        });
        depBtn_save.setOnAction(e -> {
            try {
                departmentsTree.saveToFile(departmentsFile);
                showAlert("Success!", "Successfully Saved To File");
            } catch (FileNotFoundException ex) {
                showAlert("Error!", "Can't Save To File");
            }
        });
        stdBtn_save.setOnAction(e -> {
            if (selectedDepartment == null)
                return;

            try {
                selectedDepartment.students.saveTableToFile(selectedDepartment.getFile());
                showAlert("Success!", "Successfully Saved To File");
            } catch (FileNotFoundException ex) {
                showAlert("Error!", "Can't Save To File");
            }
        });
        departmentMenuBar.getChildren().addAll(searchDepVBox,depBtn_insert,depBtn_remove,depBtn_treeHeight,depBtn_save);
        studentsMenuBar.getChildren().addAll(searchStdVBox,stdBtn_insert,stdBtn_remove,stdBtn_size,stdBtn_hashUsed,stdBtn_save);
        root.getChildren().addAll(departmentMenuBar,departmentTextArea,studentsMenuBar,studentsTextArea);
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Project 3 : A Data Structure For Birzeit University");

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }

    private Stage setUpInsertDepartmentStage(TextArea textArea) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setStyle("-fx-background-color: " + color_1);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        TextField departmentName = new TextField();
        TextField departmentFilePath = new TextField();
        departmentName.setPromptText("DEPARTMENT NAME");
        departmentFilePath.setPromptText("FILE PATH");
        for (TextField T: Arrays.asList(departmentName,departmentFilePath)){
            T.setStyle("-fx-control-inner-background: " + color_4 + "; -fx-highlight-fill: " + color_2 + "; "
                    + "-fx-highlight-text-fill: " + color_4 + "; -fx-text-fill: " + color_2 + "; "
                    + "-fx-border-color: " + color_4 + "; -fx-border-width: 1; -fx-prompt-text-fill: rgba(172,189,186,0.46)");
            T.setFont(Font.font("Consolas", 18) );
            T.setPrefHeight(60);
            root.getChildren().add(T);
        }
        Button insert = insertButton(root);
        insert.setOnAction(e -> {
            String name = departmentName.getText();
            String path = departmentFilePath.getText();
            if (path.endsWith(".txt"))
                departmentsTree.insert(new Department(name,path));
            else
                departmentsTree.insert(new Department(name,path + ".txt"));
            textArea.setText(departmentsTree.toString());
        });

        Scene scene = new Scene(root,240,200);
        stage.setScene(scene);
        return stage;
    }

    private Stage setUpInsertStudentStage(TextArea textArea) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setStyle("-fx-background-color: " + color_1);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        HBox radioHBox = new HBox();
        radioHBox.setStyle("-fx-background-color: " + color_1);
        radioHBox.setSpacing(10);
        radioHBox.setAlignment(Pos.CENTER);

        TextField nameTextField = new TextField();
        TextField idTextField = new TextField();
        TextField avgTextField = new TextField();
        RadioButton genderMRB = new RadioButton("MALE");
        RadioButton genderFRB = new RadioButton("FEMALE");
        ToggleGroup toggleGroup = new ToggleGroup();

        nameTextField.setPromptText("FULL NAME");
        idTextField.setPromptText("ID");
        avgTextField.setPromptText("AVERAGE");

        for (TextField T: Arrays.asList(nameTextField,idTextField,avgTextField)){
            T.setStyle("-fx-control-inner-background: " + color_4 + "; -fx-highlight-fill: " + color_2 + "; "
                    + "-fx-highlight-text-fill: " + color_4 + "; -fx-text-fill: " + color_2 + "; "
                    + "-fx-border-color: " + color_4 + "; -fx-border-width: 1; -fx-prompt-text-fill: rgba(172,189,186,0.46)");
            T.setFont(Font.font("Consolas", 18) );
            T.setPrefHeight(60);
            root.getChildren().add(T);
        }
        for (RadioButton R: Arrays.asList(genderFRB,genderMRB)){
            R.setFont(Font.font("Consolas", 18) );
            R.setToggleGroup(toggleGroup);
            radioHBox.getChildren().add(R);
        }
        genderFRB.setSelected(true);
        root.getChildren().add(radioHBox);

        Button insert = insertButton(root);
        insert.setOnAction(e -> {
            if (selectedDepartment == null)
                return;
            String name = nameTextField.getText();
            String id = idTextField.getText();
            char gender;
            double average = 0;
            if (genderMRB.isSelected())
                gender = 'M';
            else
                gender = 'F';
            try{
                average = Double.parseDouble(avgTextField.getText());
            }catch (NumberFormatException exception){
                avgTextField.setText("");
            }
            selectedDepartment.students.insert(new Student(name,id,average,gender));
            System.out.println("INSERTED NEW STUDENT");
            textArea.setText(selectedDepartment.students.printTable());
        });

        Scene scene = new Scene(root,240,250);
        stage.setScene(scene);
        return stage;
    }

    private Button insertButton(VBox root) {
        Button insert = new Button("INSERT");
        insert.setStyle("-fx-background-color: " + color_4);
        insert.setFont(Font.font("Consolas", 18) );
        insert.setTextFill(Color.web(color_2));
        insert.setOnMouseEntered(e -> {
            insert.setStyle("-fx-background-color: " + color_2 + " ; -fx-cursor: HAND");
            insert.setFont(Font.font("Consolas", FontWeight.BOLD, 18) );
            insert.setTextFill(Color.web(color_4));
        });
        insert.setOnMouseExited(e -> {
            insert.setStyle("-fx-background-color: " + color_4);
            insert.setFont(Font.font("Consolas", 18) );
            insert.setTextFill(Color.web(color_2));
        });

        root.getChildren().add(insert);
        return insert;
    }

    private void showAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText("");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        try {
            readFiles();
        }catch (Exception e){
            System.out.println("Error reading file");
        }
        launch();



    }

    private static void readFiles() throws IOException {
        Scanner scanner = new Scanner(departmentsFile);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] lineSplit = line.split("/");
            Department newDepartment = new Department(lineSplit[0],lineSplit[1]);
            departmentsTree.insert(newDepartment);
            if (newDepartment.getFile().exists()){
                Scanner innerScanner = new Scanner(newDepartment.getFile());
                while (innerScanner.hasNext()){
                    String innerLine = innerScanner.nextLine();
                    String[] innerSplit = innerLine.split("/");
                    Student newStudent = new Student(innerSplit[0],innerSplit[1],Double.parseDouble(innerSplit[2]),innerSplit[3].charAt(0));
                    newDepartment.students.insert(newStudent);
                }
                innerScanner.close();
            }else {
                if (newDepartment.getFile().createNewFile())
                    System.out.println("Created New File!");
            }
        }
    }
}