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
    Titulo del examen, nombre de quien presenta el examen, fecha, ultima pregunta   respondida, y calificacion.
*/
package server;

import app.EvaluatorAlert;
import app.SQLConnection;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import javafx.scene.control.Alert.AlertType;

public class ExamServer implements ExamEvaluatorServer {
	
	@Override
	public boolean createUser(String user, String password) {
        //Empty field validations
        if (user.isEmpty() || password.isEmpty()) {
                return false;
        }
        
        //Accept password validation
        if (!(user.matches("[[a-z]|[A-Z]|\\s]*"))) {
                return false;
        }
        
        //Incorrect type of password validation
        if (!(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{0,}$"))) {
                return false;
        }
        
        //Same user name validation

        try (Connection con = SQLConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO user (name,password,type) VALUES (?,?,0)")) {
                ps.setString(1,user);
                ps.setString(2,password);
                System.out.println(ps.toString());
        } catch (SQLException e) {
                return false;
        }
        return true;
	}

	@Override
	public boolean isUserDuplicate(String user) {
        //Get No. of registered users
        boolean duplicationFlag = false;
        try (Connection con = SQLConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT COUNT(name) AS count FROM user WHERE name = ?")) {
                ps.setString(1,user);
                try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt("count") != 0) {
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

	@Override
	public boolean login(String user, String password) {
        //Empty field validation
        if (user.isEmpty() || password.isEmpty()){
            return false;
        }

        //Select user from database
        try (Connection con = SQLConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE name=? AND password=?")) {
            ps.setString(1,user);
            ps.setString(2,password);
            System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if ( rs.next() && user.equals(rs.getString("name")) && password.equals(rs.getString("password")) ) {
                        return true;
                }
                return false;
            }
        }catch (SQLException e){
                return false;
        }
	}

	
	public static void main(String[] args) {
		try {
			ExamServer server = new ExamServer();
			ExamEvaluatorServer stub =  (ExamEvaluatorServer) UnicastRemoteObject.exportObject(server,0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("ExamServer",stub);
			System.out.println("Servidor listo");
		} catch(Exception e) {
			System.err.println("Error en el sevidor: " + e.toString());
		}
	}
}
