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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test{

    //Declares necessary instance variables
    Scene scene;
    Stage stage;
    Label questionLabel, titleLabel;
    RadioButton radioOption1, radioOption2, radioOption3, radioOption4;
    Button next, previous, home, finishTest;
    int currentQuestion = 0;
    Connection con = null;
    final ToggleGroup group = new ToggleGroup();
    boolean isAdmin;
    Thread tickerThread;
    int timeLeft = 0;
    Label timerLabel = new Label();
    
             
    public void startExam(int idu,  int ide, boolean admin){
        
        //Get necesary values sent from MainMenuFrame class
        isAdmin = admin;
        stage = new Stage();
        final int theIDU = idu;
        final int theIDE = ide;
        String questionTitle = "";
        timeLeft = new Time().GetUserTime(idu, ide);
        
        //Connects to DataBase
        try{ 
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examevaluator","root","P0L1m4s7er!");  
        }catch(Exception e){
            System.out.println("Connection");
            System.out.println(e);
        }
        
        //Gets questions numbers of the exam from database
        String[] questionNumbers = GetQuestionNumbers(theIDU, theIDE);
         
        //Init all GUI Components and stage
        try{   
            timerLabel.setStyle("-fx-font-size: 1em;");
            timerLabel.setLayoutX(300);
            timerLabel.setLayoutY(15);
            timerLabel.setPrefSize(300,30);
            timerLabel.setAlignment(Pos.CENTER);   

            //Exam timer method
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        timeLeft--;
                        timerLabel.setText(""+timeLeft);            
                        if(timeLeft == 0){
                            System.out.println("Acabó");
                            FinishExam(currentQuestion, idu, ide, questionNumbers);
                        }
                        
                    }
                };

                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                    }
                }

            });

            thread.setDaemon(true);
            thread.start();
                    
            questionLabel = new Label("¿Hola?");
            questionLabel.setPrefSize(500, 100);
            questionLabel.setWrapText(true);
            
            titleLabel = new Label("");
            titleLabel.setPrefSize(300,100);
            titleLabel.setWrapText(true);
          
            radioOption1 = new RadioButton("Opcion 1");
            radioOption1.setToggleGroup(group);
            radioOption2 = new RadioButton("Opcion 2");
            radioOption2.setToggleGroup(group);
            radioOption3 = new RadioButton("Opcion 3");
            radioOption3.setToggleGroup(group);
            radioOption4 = new RadioButton("Opcion 4");
            radioOption4.setToggleGroup(group);

            finishTest = new Button("Finish");
            finishTest.setPrefSize(100, 30);
            finishTest.setVisible(false);
            finishTest.setDisable(true);
            finishTest.setLayoutX(495);
            finishTest.setLayoutY(555);
            finishTest.setOnAction((ActionEvent e) -> FinishExam(currentQuestion, idu, ide, questionNumbers));

            next = new Button("Next");
            next.setPrefSize(100, 30);
            next.setOnAction((ActionEvent e) -> printNext(theIDU, theIDE, questionNumbers));
           
            previous = new Button("Previous");
            previous.setPrefSize(100, 30);
            previous.setVisible(false);
            previous.setDisable(true);
            previous.setOnAction((ActionEvent e) -> printPrevious(theIDU, theIDE ,questionNumbers));
           
            if(currentQuestion == 1){
               previous.setDisable(true);
            }else if(currentQuestion > 1){
               previous.setDisable(false);
            }
           
            home = new Button("Home");
            home.setPrefSize(100, 30);
            home.setLayoutX(15);
            home.setLayoutY(15);
            home.setOnAction((ActionEvent e) -> ReturnMenu(theIDU, theIDE));
            
                              
            VBox optionBox = new VBox();
            optionBox.getChildren().addAll(radioOption1, radioOption2, radioOption3, radioOption4);
            optionBox.setSpacing(20);
            optionBox.setLayoutX(50);
            optionBox.setLayoutY(270);
           
            HBox movementBox = new HBox();
            movementBox.getChildren().addAll(previous, next);
            movementBox.setSpacing(380);
            movementBox.setAlignment(Pos.CENTER);
            movementBox.setLayoutX(15);
            movementBox.setLayoutY(555);
           
            VBox questionAndOptions = new VBox();
            questionAndOptions.getChildren().addAll(questionLabel, optionBox);
            questionAndOptions.setSpacing(20);
            questionAndOptions.setPrefSize(500, 225);
            questionAndOptions.setLayoutX(50);
            questionAndOptions.setLayoutY(175);
            
            HBox titleAndTimer = new HBox();
            titleAndTimer.getChildren().addAll(titleLabel);
            titleAndTimer.setPrefSize(500, 100);
            titleAndTimer.setLayoutX(50);
            titleAndTimer.setLayoutY(85);
         
          
            Group g = new Group();
            g.getChildren().addAll(home, timerLabel, questionAndOptions ,movementBox, finishTest, titleAndTimer);
                      
            scene = new Scene(g,600,600);
            stage.setScene(scene);
            stage.setMinHeight(600);
            stage.setMinWidth(600);
            stage.setTitle("Test");
            stage.setResizable(false);   
            stage.show();
            
            GetData(questionNumbers, currentQuestion, theIDE);
            
            //Gets exam title from database
            try{
                PreparedStatement qs = con.prepareStatement("SELECT title FROM exam WHERE ide = ? AND idu = ? LIMIT 1");
                qs.setInt(1, theIDE);
                qs.setInt(2, theIDU);
                ResultSet rs = qs.executeQuery();
                if(rs.next()){
                questionTitle = rs.getString("title");
                }
                qs.close();
            }catch(Exception e){
                
                System.out.println(e);
                System.out.println("Exam");
            }
            
            titleLabel.setText("" + questionTitle);
             
        }catch(Exception e){
            System.out.println("Valio Madres");
            e.printStackTrace();
        }       
    }
    
    //Methos that displays next Answer
    public void printNext(int theIDU, int theIDE, String[] questionNumbers){
        SaveOption(currentQuestion, theIDU, theIDE, questionNumbers);
        group.selectToggle(null);
        currentQuestion = currentQuestion + 1;
        
        GetData(questionNumbers, currentQuestion, theIDE);
        
        if(currentQuestion == 9){
            next.setDisable(true);
            next.setVisible(false);
            finishTest.setVisible(true);
            finishTest.setDisable(false);   
        }

        previous.setVisible(true);
        previous.setDisable(false);
       
    }
    
    //Method that displays previous answer
    public void printPrevious(int theIDU, int theIDE, String[] questionNumbers){
        SaveOption(currentQuestion, theIDU, theIDE, questionNumbers);
        group.selectToggle(null);
        currentQuestion = currentQuestion -1 ;
        
        GetData(questionNumbers, currentQuestion, theIDE);
        
        if(currentQuestion ==0){
            previous.setVisible(false);
            previous.setDisable(true);  
        }
        
        next.setVisible(true);
        next.setDisable(false);
        finishTest.setVisible(false);
        finishTest.setDisable(true);
       
    }
    
    //Method that gets all question and options text from database
    public void GetData(String[] questions, int currentQuestion, int theIDE){
        
        String questionNumber = questions[currentQuestion];
        String question = "";
        String correctAnswer = "";
        String dataBaseChoice = "";
       
        ArrayList<String> choices = new ArrayList<String>();
       
        try{
            PreparedStatement st = con.prepareStatement("SELECT question, answer FROM question WHERE idp = ? ");
            st.setString(1, questionNumber);
            ResultSet rs = st.executeQuery();
            rs.next();
            question = rs.getString("question");
            correctAnswer = rs.getString("answer");
            st.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("GetData.question");
        }
        
        try{
            PreparedStatement st = con.prepareStatement("SELECT useranswer FROM response WHERE ide = ? AND idp = ? ");
            st.setInt(1, theIDE);
            st.setString(2, questionNumber);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
            dataBaseChoice = rs.getString("useranswer");
            }
            st.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("GetData.response");
        }
        
        try{
            PreparedStatement st = con.prepareStatement("SELECT choice FROM choice WHERE idp = ? ");
            st.setString(1, questionNumber);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
               String choice = rs.getString("choice");
               choices.add(choice);
           }
           st.close();
        }catch(Exception e){
            System.out.println("GetData.choice");
           System.out.println(e); 
        }    
        choices.add(correctAnswer);
        Collections.shuffle(choices);
        
        questionLabel.setText(question);
        radioOption1.setText(choices.get(0));
        radioOption2.setText(choices.get(1));
        radioOption3.setText(choices.get(2));
        radioOption4.setText(choices.get(3));
               
        if(dataBaseChoice == null){   
        }else if(dataBaseChoice.equals(radioOption1.getText())){
            radioOption1.setSelected(true);
        }else if(dataBaseChoice.equals(radioOption2.getText())){
            radioOption2.setSelected(true);
        }else if(dataBaseChoice.equals(radioOption3.getText())){
            radioOption3.setSelected(true);
        }else if(dataBaseChoice.equals(radioOption4.getText())){
            radioOption4.setSelected(true);
        }
    }
    
    //Method that get question numbers from an user exam
    public String[] GetQuestionNumbers(int theIDU, int theIDE){
        
        ArrayList<String> questionsArray = new ArrayList<String>();
        try{
           PreparedStatement st = con.prepareStatement("SELECT idp FROM response WHERE idu = ? and ide = ?");
           st.setInt(1, theIDU);
           st.setInt(2, theIDE);
           ResultSet rs = st.executeQuery();
           while(rs.next()){
               String questionNumber = rs.getString("idp");
               questionsArray.add(questionNumber);
           }
           st.close();
        }catch(Exception e){
            System.out.println("GetQuestionNumbers");
           System.out.println(e);
        }
        
        String[] questions = questionsArray.toArray(new String[questionsArray.size()]);
        
        return questions;
    }
    
    //Method than inserts user option into the database
    public void SaveOption(int currentQuestion, int idu, int ide, String[] questionNumbers){
        
        RadioButton chk = (RadioButton)group.getSelectedToggle(); 
        if(group.getSelectedToggle() != null){
            try{
                PreparedStatement so = con.prepareStatement("UPDATE response SET useranswer = ? WHERE ide = ? AND idp = ?");
                so.setString(1, chk.getText());
                so.setInt(2, ide);
                so.setInt(3, Integer.parseInt(questionNumbers[currentQuestion]));
                so.execute();
                so.close();
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Save Option");
                
            }
        }     
    }
    
    //Method that saves the user exam
    public void FinishExam(int currentQuestion, int idu, int ide, String[] questionNumbers){
        
        SaveOption(currentQuestion, idu, ide, questionNumbers);
        
        String [] theUserExam = UserAnswers.GetUserAnswers(idu, ide);
        String [] theDataBaseExam = DataBaseAnswers.GetDataBaseAnswers(questionNumbers);
        new Time().SetUserTime(idu, ide, timeLeft);
        System.out.println("Tu calificación es: " + new TestScore().GetScore(questionNumbers, theUserExam, theDataBaseExam, idu, ide,  isAdmin)); 
        
        stage.close();
        
    }
    
    //Return Menu Method
    public void ReturnMenu(int idu, int ide){
     
        new Time().SetUserTime(idu, ide, timeLeft);
        String name = new Data().GetUserName(idu);
        new MainMenuFrame().startMenu(name, isAdmin);
        stage.close();
        
    }

}

