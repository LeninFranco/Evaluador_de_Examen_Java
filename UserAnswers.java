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

import java.sql.*;

public class UserAnswers {
    
    //Gets the user answers of an especific exam from the database
    public static String[] GetUserAnswers(int idu, int ide){
        
        String[] userAnswers = new String[10];
        Connection con = null;
        
        try{
            con = SQLConnection.getConnection();
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{
            PreparedStatement st = con.prepareStatement("SELECT ifnull(useranswer, 'Unanswered') useranswer FROM response WHERE idu = ? and ide = ?");
            st.setInt(1, idu);
            st.setInt(2, ide);
            ResultSet rs = st.executeQuery();
            int cont = 0;
            while(rs.next()){
                userAnswers[cont] = rs.getString("useranswer");
                cont++;
            }
            
        }catch(Exception e){
            
        }
        
        for(int i=0; i<10; i++){
            if(userAnswers[i].equals("0"))
                userAnswers[i] = "Unanswered";
        }
        
        return userAnswers;
    }
        
}

