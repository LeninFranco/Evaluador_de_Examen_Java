/*
   ALUMNO: Franco Aguilar Lenin Eduardo
   PROYECTO Aplicador y evaluador de examenes de opcion multiple
   FECHA: 15/06/2021
   GRUPO: 2CM13
   MATERIA: PROGRAMACION ORIENTADA A OBJETOS
   PROFESOR: TECLA PARRA ROBERTO

PROCEDIMIENTO
Aplicador y evaluador de examenes
de opcion multiple

-Debera contar con una Interfaz grafica de usuario y en dicha interfaz un Acerca de...
-Para cadapregunta habra 4 posibles respuestas el usuario seleccionara la suya mediante un RadioButton.
-Una vez que el usuario seleccione su respuesta se le presentara la siguiente pregunta junto con sus 4 posibles respuestas.
-Cuando el usuario termine el examen se le evaluara y se le mostrara dicha evaluacion.
-Definir una clase Reactivo con los siguientes atributos:
	Pregunta, opcionA, opcionB, opcionC, opcionD, respuesta
-Almacenar las 10 instancias de reactivo en un vector

-Que limite el tiempo (usando un timer) que tiene el usuario para contestar cada pregunta .
-Que obtenga las 10 preguntas de una base de datos (usando JDBC).
-Que permita (a un administrador) agregar nuevas preguntas a la base de datos.
-Que elija 10 reactivos de forma aleatoria de un banco de 50 reactivos.
-Que permita al usuario abandonar el examen en culquier momento y que luego le permita retomar el examen donde se quedo la ultima vez.
-Ademas de la clase Reactivo definir una clase Examen con los siguientes atributos:
	Titulo del examen, nombre de quien presenta el examen, fecha, ultima pregunta 	respondida, y calificacion.
*/

package app;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Pos;

public class MainMenuFrame {
    
    //Instance Variables
    Stage stage;
    boolean aux, admin;

    public void startMenu(String user, boolean isAdmin) {
        
        admin = isAdmin;
        stage = new Stage();
        
        //GUI Components
        Label welcomeLabel = new Label("Welcome: "+user + "!");
        welcomeLabel.setWrapText(true);
        welcomeLabel.setPrefSize(400, 50);
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setLayoutY(50);
        
        Button takeExamButton = new Button("Take Exam");
        takeExamButton.setPrefSize(150, 50);
        
        //takeExam button method call
        takeExamButton.setOnAction(e -> {
                int idu = 0;
                try (Connection con = SQLConnection.getConnection();
                         PreparedStatement ps = con.prepareStatement("SELECT idu FROM user WHERE name = ?")) {
                        ps.setString(1,user);
                        try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                         idu = rs.getInt("idu");
                                }
                        }
                        takeExam(idu);
                } catch (SQLException e1) {
                        EvaluatorAlert.sendAlert("Error","There was a problem with the DataBase",AlertType.ERROR);
                }
        });
       
        //logOut Button method call
        Button logOutButton = new Button("Log Out");
        logOutButton.setPrefSize(150, 50);
        logOutButton.setOnAction(e -> {
                new ExamEvaluator().start(new Stage());
                stage.close();
        });
        
        //myExams button method call
        Button myExamsButton = new Button("My Exams");
        myExamsButton.setPrefSize(150, 50);
        myExamsButton.setOnAction(e -> {
            new DisplayMyExams().startDisplay(user, isAdmin);
            stage.close();
        });
        
        //Container box for GU Components   
        VBox buttonBox = new VBox();
        buttonBox.setPrefSize(400, 50);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(35);
        buttonBox.getChildren().addAll(takeExamButton, myExamsButton, logOutButton);
        buttonBox.setLayoutY(125);
        
        //Admin menu method call
        if (isAdmin) {
                Button createQuestion = new Button("Crear pregunta");
                createQuestion.setOnAction(e -> registerQuestion());
                buttonBox.getChildren().add(createQuestion);
        }
        
        //Group box and label
        Group g = new Group();
        g.getChildren().addAll(welcomeLabel, buttonBox);
        
        //Add everything to Scene
        Scene scene = new Scene(g, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.setResizable(false);
        stage.show();
    }
	
    public void takeExam(int idu) {
        
        //Gets information from database
        try (Connection con = SQLConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT ide FROM exam WHERE idu=? AND grade IS NULL");
            PreparedStatement maxIdp = con.prepareStatement("SELECT MAX(idp) FROM question");
            PreparedStatement idePs = con.prepareStatement("SELECT MAX(ide) FROM exam")) {
            ps.setInt(1,idu);
            int ide = 0;

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ide = rs.getInt("ide");
                    ps.close();
                    con.close();
                    stage.close();
                    new Test().startExam(idu, ide, admin);
                } else
                    try (ResultSet maxRs = idePs.executeQuery(); 
                        ResultSet maxIDPNo = maxIdp.executeQuery()) {
                            if (maxRs.next()) {
                                maxIDPNo.next();
                                int maxp = maxIDPNo.getInt("max(idp)");
                                ide = maxRs.getInt("max(ide)");
                                maxIdp.close();
                                idePs.close();
                                con.close();
                                stage.close();
                                 if (createExam(idu,ide, maxp)) {
                                        con.close();
                                        stage.close();
                                        new Test().startExam(idu,ide+1, admin);
                                }
                            }
                    }
            }
        } catch (SQLException e) {
            System.out.println(e);
            EvaluatorAlert.sendAlert("Error","There was a problem with the DataBase",AlertType.ERROR);
        }
    }
	
    public boolean createExam(int idu, int ide, int maxIdp) {
        int max = maxIdp;
        int ideAux = ide + 1;
        
        //Creates user exams
        try (Connection con = SQLConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO exam (idu, title, timeleft) VALUES (?,?,?)")) {
                ps.setInt(1,idu);
                ps.setString(2,"Examen " + (ideAux));
                ps.setInt(3, 300);
                ps.executeUpdate();

                int[] numbers = new int[10];
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i=1; i<max; i++) {
                    list.add(new Integer(i));
                }

                Collections.shuffle(list);

                for(int i = 0 ; i < 10 ; i++){
                    numbers[i] = list.get(i);
                }

                try{
                    PreparedStatement st = con.prepareStatement("INSERT INTO response (idu, idp, ide, useranswer) VALUES (?,?,?,?)");
                    for(int i = 0; i < 10 ; i++){

                        st.setString(1, Integer.toString(idu));
                        st.setString(2, Integer.toString(numbers[i]));
                        st.setString(3, Integer.toString(ideAux));
                        st.setString(4, "0");
                        st.execute();
                    }
                    st.close();

                }catch(Exception e){
                    System.out.println(e);
                    System.out.println("Hola");
                }
                con.close();
                return true;
                
        } catch(SQLException e) {
                System.out.println("Hola");
                System.out.println(e);
                EvaluatorAlert.sendAlert("Error","No se pudo crear el examen",AlertType.ERROR);
                return false;
        }
    }
	
    public void registerQuestion() {
        
        //GUI Components
        Stage stage = new Stage();
        VBox root = new VBox();
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(15);
        
        Label questionLabel = new Label("Pregunta:");
        TextArea question = new TextArea();
        question.setPrefHeight(60);
        question.setMaxWidth(600);
        
        Label answerLabel = new Label("Respuesta:");
        TextArea answer = new TextArea();
        answer.setPrefHeight(60);
        answer.setMaxWidth(600);
        
        Label optionALabel = new Label("Opcion A");
        TextArea optionA = new TextArea();
        optionA.setPrefHeight(60);
        optionA.setMaxWidth(600);
        
        Label optionBLabel = new Label("Opcion B");
        TextArea optionB = new TextArea();
        optionB.setPrefHeight(60);
        optionB.setMaxWidth(600);
        
        Label optionCLabel = new Label("Opcion C");
        TextArea optionC = new TextArea();
        optionC.setPrefHeight(60);
        optionC.setMaxWidth(600);
        
        //createQuestion method call
        Button createButton = new Button("Crear pregunta");
        createButton.setOnAction(e -> {
                if (createQuestion(question.getText(),answer.getText(),optionA.getText(),optionB.getText(),optionC.getText()))
                        stage.close();
        });
        
        //cancel Button method call
        Button cancelButton = new Button("Cancelar");
        buttonBox.getChildren().addAll(createButton,cancelButton);
        cancelButton.setOnAction(e -> stage.close());
        root.setSpacing(15);
        root.getChildren().addAll(questionLabel, question, answerLabel, answer, optionALabel, optionA, optionBLabel,
                        optionB, optionCLabel, optionC,buttonBox);
        root.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-insets: 5;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: #fc0373");
        
        //Add everthing to scene
        Scene scene = new Scene(root,800,800);
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.setTitle("Crear nueva pregunta");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
	
    public boolean createQuestion(String questionText, String answer, String optionA, String optionB, String optionC) {
        
        //Empty fields validation
        if (questionText.isEmpty() || answer.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty()) {
                EvaluatorAlert.sendAlert("Error","Por favor llene todos los campos",AlertType.ERROR);
                return false;
        }
        
        //Inserts question into the database
        try (Connection con = SQLConnection.getConnection();
                 PreparedStatement questionInsertion = con.prepareStatement("INSERT INTO question (question,answer) VALUES (?,?)");
                 PreparedStatement countStatement = con.prepareStatement("SELECT MAX(idp) AS max FROM question");
                 PreparedStatement answerInsertion = con.prepareStatement("INSERT INTO choice (idp,choice) VALUES (?,?)")) {
                questionInsertion.setString(1,questionText);
                questionInsertion.setString(2,answer);
                questionInsertion.executeUpdate();
                int questionId = 0;
                try (ResultSet rs = countStatement.executeQuery()) {
                        if (rs.next())
                                questionId = rs.getInt("max");
                }
                answerInsertion.setInt(1,questionId);
                answerInsertion.setString(2,optionA);
                answerInsertion.addBatch();
                answerInsertion.setInt(1,questionId);
                answerInsertion.setString(2,optionB);
                answerInsertion.addBatch();
                answerInsertion.setInt(1,questionId);
                answerInsertion.setString(2,optionC);
                answerInsertion.addBatch();
                answerInsertion.executeBatch();
                con.close();
        } catch (SQLException e) {
                EvaluatorAlert.sendAlert("Error","Hubo un error al intentar enviar la informacion a la base de datos",AlertType.ERROR);
                return false;
        }
        EvaluatorAlert.sendAlert("Aviso","La pregunta se ha creado y almacenado con exito",AlertType.INFORMATION);

        return true;
    }
}

