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

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TestDisplay{

    //Declares necesary instance variables
    Scene scene;
    Stage stage;
    Label titleLabel;
    Label[] results;
    Button home;
   
     
    //Displays the exams information of a user
    public void startDisplay(String[] userAnswers, String[] dbAnswers, int idu, String[] questionNumbers, String[] questionTexts, boolean admin, int grade, String name){
        
        try{
               
            stage = new Stage();

            Label aux = new Label("");
            aux.setPrefSize(600, 30);

            titleLabel = new Label("Your Grade Is: " + grade +"/10");
            titleLabel.setPrefSize(600, 100);
            titleLabel.setAlignment(Pos.CENTER);

            results = new Label[10];
            for(int i = 0; i < 10 ; i++){
                results[i] = new Label("");
                results[i].setPrefWidth(500);
                results[i].setWrapText(true);
            }

            for(int i=0; i<10; i++){
                String resultAux = "";
                if(userAnswers[i].equals(dbAnswers[i]))
                    resultAux = "Correct";
                else
                    resultAux = "Incorrect";
                results[i].setText("Question No. " + (i+1) + ": " + questionTexts[i] + "\n" +
                                    "Your Answer: " + userAnswers[i] + "\n" + 
                                    "Correct Answer: " + dbAnswers[i] + "\n" +
                                    "Result: " + resultAux + "\n\n");
            }


            home = new Button("Home");
            home.setPrefSize(100, 30);
            home.setOnAction((ActionEvent e) -> ReturnMenu(name , admin, stage));
            home.setLayoutX(20);

            VBox homeBox = new VBox();
            homeBox.setPrefSize(500, 30);
            homeBox.getChildren().addAll(aux, home);
            homeBox.setAlignment(Pos.CENTER);

            
           VBox theResults = new VBox();
           theResults.getChildren().addAll(homeBox, titleLabel);
           for(int i = 0 ; i < 10 ; i++){
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
            stage.setTitle("Maincra");
            stage.show();
            stage.setResizable(false);  
            
        }catch(Exception e){
            System.out.println("Valio Madres");
            e.printStackTrace();
        }       
    }
    
        public void ReturnMenu(String name, boolean isAdmin, Stage stage ){
      
        new MainMenuFrame().startMenu(name, isAdmin);
        stage.close();
        
    }
       
        
}
