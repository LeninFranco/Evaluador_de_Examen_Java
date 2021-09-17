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

import server.ExamEvaluatorServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;

public class ExamEvaluator extends Application {

   
    static ExamEvaluatorServer stub;
    boolean n,f;
    @Override
    public void start(Stage primaryStage) {

        //GUI Components
        TextField user = new TextField();
        user.setPromptText("User");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        
        Label userLabel = new Label("User: ");
        userLabel.setPrefWidth(100);
        userLabel.setLabelFor(user);
        userLabel.setMnemonicParsing(true);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setPrefWidth(100);
        passwordLabel.setLabelFor(password);
        passwordLabel.setMnemonicParsing(true);
        Button loginButton = new Button("Login");
        loginButton.setOnAction( e -> {
            try {
                if (login(user.getText(),password.getText()) && stub.login(user.getText(), password.getText()))
                    primaryStage.close();
            } catch (RemoteException ex) {
                Logger.getLogger(ExamEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Create user button method
        Button createUserButton = new Button("Register");
        createUserButton.setOnAction(e -> registerNewUser());

        //About us button method
        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setOnAction(e -> EvaluatorAlert.sendAlert("About Us",
                        "Created by LeninFranco",
                        AlertType.INFORMATION));

        //Conteiner Boxes for GUI Components
        HBox userBox = new HBox();
        userBox.setPrefSize(400, 50);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(userLabel, user);
        userBox.setSpacing(10);
        userBox.setLayoutY(100);

        HBox passwordBox = new HBox();
        passwordBox.setPrefSize(400, 50);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(passwordLabel,password);
        passwordBox.setSpacing(10);
        passwordBox.setLayoutY(150);

        HBox buttonBox = new HBox();
        buttonBox.setPrefSize(400, 50);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton,createUserButton,aboutUsButton);
        buttonBox.setSpacing(40);
        buttonBox.setLayoutY(225);
   
        //Group all boxes
        Group g = new Group();
        g.getChildren().addAll(userBox, passwordBox, buttonBox);
        
        //Add everithing to the Scene
        Scene scene = new Scene(g,400,400);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
	
    public boolean login(String user, String password){
        
        //Empty field validation
        if (user.isEmpty() || password.isEmpty()){
            EvaluatorAlert.sendAlert("Error","Please fill all the fields",AlertType.ERROR);
            return false;
        }

        //Select user from database
        try (Connection con = SQLConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE name=? AND password=?")) {
            ps.setString(1,user);
            ps.setString(2,password);
            try (ResultSet rs = ps.executeQuery()) {
                if ( rs.next() && user.equals(rs.getString("name")) && password.equals(rs.getString("password"))) {
                        new MainMenuFrame().startMenu(rs.getString("name"), rs.getBoolean("type"));
                        return true;
                }
                EvaluatorAlert.sendAlert("Error","User or Password is incorret. Try again",AlertType.ERROR);
                return false;
            }
        }catch (SQLException e){
                EvaluatorAlert.sendAlert("Error","There was a problem with the database",AlertType.ERROR);
                return false;
        }
    }
	
    public void registerNewUser() {
        
        //Stage

        Stage stage = new Stage();
        
        //GUI Components
        Label userLabel = new Label("New User:");
        userLabel.setPrefWidth(100);
        
        TextField user = new TextField();
        
        Label passwordLabel = new Label("Password:");
        passwordLabel.setPrefWidth(100);
        PasswordField password = new PasswordField();
        
        //Create button method call
        Button createButton = new Button("Register");
        
        createButton.setOnAction(e -> {
            try {
                if (createUser(user.getText(),password.getText()) && stub.createUser(user.getText(),password.getText()))
                    stage.close();
            } catch (RemoteException ex) {
                Logger.getLogger(ExamEvaluator.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Cancel button method call
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());
        
        //Conteiner Boxes for GUI Components
        HBox userBox = new HBox();
        userBox.setSpacing(15);
        userBox.setPrefSize(410, 50);
        userBox.getChildren().addAll(userLabel, user);
        userBox.setSpacing(10);
        userBox.setLayoutY(100);
        userBox.setAlignment(Pos.CENTER);
        
        HBox passwordBox = new HBox();
        passwordBox.setSpacing(15);
        passwordBox.setPrefSize(410, 50);
        passwordBox.getChildren().addAll(passwordLabel,password);
        passwordBox.setSpacing(10);
        passwordBox.setLayoutY(150);
        passwordBox.setAlignment(Pos.CENTER);
        
        HBox buttonBox = new HBox();
        buttonBox.setPrefSize(410, 50);
        buttonBox.setSpacing(15);
        buttonBox.getChildren().addAll(createButton,cancelButton);
        buttonBox.setSpacing(40);
        buttonBox.setLayoutY(225);
        buttonBox.setAlignment(Pos.CENTER);
        
        //Group all boxes
        Group g = new Group();
        g.getChildren().addAll(userBox, passwordBox, buttonBox);
        
        //Add everything to Scene
        Scene scene = new Scene(g,410,410);
        stage.setScene(scene);
        stage.setTitle("User Register");
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
	
    public boolean createUser(String user, String password) {
        
        //Empty field validations
        if (user.isEmpty() || password.isEmpty()) {
                EvaluatorAlert.sendAlert("Error","Please fill all the fields",AlertType.ERROR);
                return false;
        }
        
        //Accept password validation
        if (!(user.matches("[[a-z]|[A-Z]|\\s]*"))) {
                EvaluatorAlert.sendAlert("Error","Name can not have special characters\n"
                                + "or numbers",AlertType.ERROR);
                return false;
        }
        
        //Incorrect type of password validation
        if (!(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{0,}$"))) {
                EvaluatorAlert.sendAlert("Error","Password must have at least 8 characters\n"
                                + " and must have mayus, minus and numbers",AlertType.ERROR);
                return false;
        }
        
        //Same user name validation
        if (isUserDuplicate(user)) {
                return false;
        }
        try (Connection con = SQLConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO user (name,password,type) VALUES (?,?,0)")) {
                ps.setString(1,user);
                ps.setString(2,password);
                ps.executeUpdate();
        } catch (SQLException e) {
                EvaluatorAlert.sendAlert("Error","There was a problem when sending the information to the Database",AlertType.ERROR);
                return false;
        }
        EvaluatorAlert.sendAlert("Aviso","User Registered",AlertType.INFORMATION);
        return true;

    }

    public boolean isUserDuplicate(String user) {
        
        //Get No. of registered users
        boolean duplicationFlag = false;
        try (Connection con = SQLConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT COUNT(name) AS count FROM user WHERE name = ?")) {
                ps.setString(1,user);
                try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt("count") != 0) {
                                EvaluatorAlert.sendAlert("Error","User already taken, please choose another one",AlertType.ERROR);
                                duplicationFlag = true;
                        }
                }
        }
        catch(SQLException e) {
                EvaluatorAlert.sendAlert("Error","There was an error with User Name. Please try again",AlertType.ERROR);
                duplicationFlag = true;
        }
        return duplicationFlag;
        
    }


    public static void main(String[] args) {
        String host = null;
   	try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub = (ExamEvaluatorServer) registry.lookup("ExamServer");
            System.out.println("Cliente listo");
            } catch (Exception e) {
            System.err.println("Error en el cliente: " +e.toString());
    	}
            launch(args);
    }

}