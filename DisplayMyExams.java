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
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayMyExams{

    //Declares necesary instance variables
    Scene scene;
    Stage stage;
    Label questionLabel, titleLabel;
    Label myExams;
    Label[] results;
    RadioButton radioOption1, radioOption2, radioOption3, radioOption4;
    Button home;
    Connection con;
    int idu = 0;
    boolean isAdmin = false;
    String name = "";
     
    public void startDisplay(String userName, boolean admin){
        
        //Get necesary data from MainMenuFrame class
        stage = new Stage();
        
        Label aux = new Label("");
        aux.setPrefSize(600, 30);
        
        boolean isAdmin = admin;
        name = userName;
        int examnumber = 0;
         
        myExams = new Label("My Exams");
        myExams.setPrefSize(600, 100);
        myExams.setAlignment(Pos.CENTER);
          
        //Connects to Database
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examevaluator","root","P0L1m4s7er!");  
        }catch(Exception e){
            System.out.println(e);
        }
        
        //Gets user name from database
        try{
            
            PreparedStatement st = con.prepareStatement("SELECT idu FROM user WHERE name = ?");
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            if(rs.next());
            idu = rs.getInt("idu");
            rs.close();         
        }catch(Exception e){
            System.out.println("Error when gettin user name");
            System.out.println(e);
        }
            
        //Gets the number of exam a user have made
        try{   
            
            try{
                PreparedStatement qs = con.prepareStatement("SELECT COUNT(*) FROM exam WHERE idu = ?");
                qs.setInt(1, idu);
                ResultSet rs = qs.executeQuery();
                if(rs.next())
                examnumber = rs.getInt("COUNT(*)");
            }catch(Exception e){
                System.out.println(e);
            }
            
            results = new Label[examnumber];
            
            for(int i = 0; i < examnumber ; i++){
                
                results[i] = new Label("");
                results[i].setPrefWidth(500);
                results[i].setWrapText(true);
                
            }
            
            //Gets exam information
            try{
                PreparedStatement qs = con.prepareStatement("select e.title, u.name, q.question, e.timeleft, ifnull(e.grade, 'Unanswered') grade FROM question q, user u, exam e WHERE u.idu = ? AND u.idu = e.idu AND idp = (select max(idp) from response where useranswer <> '0')");
                    qs.setInt(1, idu);
                    ResultSet rs = qs.executeQuery();
                    int x = 0;
                    while(rs.next()){
                    results[x].setText("Title: " + rs.getString("title") + "\n" + "User: "  + rs.getString("name") + "\n" 
                    + "Last answered question: " + rs.getString("question") + " \n" + "Time left: " + rs.getString("timeleft") + "\n" + "Grade: " + rs.getString("grade") + "\n\n\n");
                    x++;
                    }
                
            }catch(Exception e){
                System.out.println(e);
            }  
            
            home = new Button("Home");
            home.setPrefSize(100, 30);
            home.setOnAction((ActionEvent e) -> ReturnMenuAux());
            home.setLayoutX(20);
            
            VBox homeBox = new VBox();
            homeBox.setPrefSize(500, 30);
            homeBox.getChildren().addAll(aux, home);
            homeBox.setAlignment(Pos.CENTER);
            
            
           VBox theResults = new VBox();
           theResults.getChildren().addAll(homeBox, myExams);
           for(int i = 0 ; i < examnumber ; i++){
            theResults.getChildren().addAll(results[i]);
           }
           theResults.setAlignment(Pos.CENTER);
           
           ScrollPane sp = new ScrollPane();
           sp.setContent(theResults);
           
           
           StackPane pane = new StackPane();
           pane.setPrefSize(600, 600);
           pane.getChildren().addAll(sp);
           pane.setAlignment(sp, Pos.CENTER);
          
            scene = new Scene(pane,600,600);
            stage.setScene(scene);
            stage.setMinHeight(600);
            stage.setMinWidth(600);
            stage.setTitle("MyExams");
            stage.show();
            stage.setResizable(false); 
            
        }catch(Exception e){
            System.out.println("Valio Madres");
            e.printStackTrace();
        }       
    }
    
    public void ReturnMenuAux(){
        ReturnMenu(name, isAdmin, stage);
    }
    
    //Return to Menu method
    public void ReturnMenu(String name, boolean isAdmin, Stage stage ){

        new MainMenuFrame().startMenu(name, isAdmin);
        stage.close();
        
    }
     
}
