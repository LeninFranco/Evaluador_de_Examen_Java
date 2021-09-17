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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;


public class TestScore {
    
    //Declares necesary instance variables
    int finalGrade = 0;
    Scene scene;
    ScrollPane scroll;
    Label questionLabel1;
    Label userAnswerLabel;
    Label dbAnswerLabel;
    Label resultLabel1;
    String name = "";
    
    //Get the final score of a completed exam and insert the result into the database
    public int GetScore(String[] questionNumbers , String[] userAnswer, String[] dbAnswers, int idu, int ide,  boolean admin){

        String[] answers = new String[10];
        
        
        Connection con = null;
        try{
            con = SQLConnection.getConnection();
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{
            PreparedStatement st = con.prepareStatement("SELECT name FROM user WHERE idu = ?");
            st.setInt(1, idu);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
            name = rs.getString("name");
            }
            rs.close();
        }catch(Exception e){
            System.out.println("Error in TestScore when trying to get user name");
            System.out.println(e);
        }
        
        try{
            PreparedStatement st = con.prepareStatement("SELECT question FROM question WHERE idp = ?");
            for(int i = 0; i < 10; i++){
                st.setString(1, questionNumbers[i]);
                ResultSet rs = st.executeQuery();
                rs.next();
                answers[i] = rs.getString("question");
                rs.close();
            }
        }catch(Exception e){
            System.out.println("Error in TestScore when trying to get questions texts");
            System.out.println(e);
        }
        
        
        int[] grade = new int[10];
        
        for (int i = 0; i < 10 ; i ++){
            if(userAnswer[i].equals(dbAnswers[i])){
                grade[i] = 1;
            }else{
                grade[i] = 0;
            }
            finalGrade = finalGrade + grade[i];
        }
        
        try{
            PreparedStatement st = con.prepareStatement("UPDATE exam SET grade = ? WHERE idu = ? AND ide = ?");
            st.setInt(1, finalGrade);
            st.setInt(2, idu);
            st.setInt(3, ide);
            st.execute();
            st.close();
        }catch(Exception e){
            System.out.println("Error on TestScore grade insertion");
            System.out.println(e);
    }
        try{
            con.close();
        }catch(Exception e){
            System.out.println("Error in TestScore when trying to close connection");
            System.out.println(e);
        }
        
        new TestDisplay().startDisplay(userAnswer, dbAnswers, idu, questionNumbers, answers, admin, finalGrade, name);
        return finalGrade;
        
    }
    
}
