/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class Project1 {

    // Valores constantes que sabemos que no vamos a cambiar durante algún punto del programa
    private static final String BDD = "jdbc:mysql://localhost:3306/xat?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "1234";
    private static HashMap<String, Boolean> CONECTADOS = new HashMap<>();

    public static void main(String[] args) {
        // Inicializar aquí las conexiones, variables y configuraciones necesarias
        Scanner num = new Scanner(System.in);
        Scanner str = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("<------------------------------------------------->");
            System.out.println("Menú de opciones:");
            System.out.println("01.  Conectar al servidor");
            System.out.println("02.  Dar de alta un cliente (Sign up)");
            System.out.println("03.  Acceder a un cliente (Sign in)");
            System.out.println("04.  Salir de la aplicación (Sign out)");
            System.out.println("05.  Listar usuarios");
            System.out.println("06.  Dar de alta un grupo");
            System.out.println("07.  Dar de baja un grupo");
            System.out.println("08.  Administrar un grupo");
            System.out.println("09.  Transmisión de un archivo");
            System.out.println("10.  Enviar mensaje al servidor");
            System.out.println("11.  Leer mensajes");
            System.out.println("12.  Enviar archivo al servidor");
            System.out.println("13.  Listar archivos");
            System.out.println("14.  Descargar archivo");
            System.out.println("15.  Configuración del servidor");
            System.out.println("16.  Configuración del cliente");
            System.out.println("00.  Salir");
            System.out.println("<------------------------------------------------->");

            System.out.print("Seleccione una opción: ");
            opcion = num.nextInt();

            switch (opcion) {
                case 1:
                    // Implementar la conexión al servidor
                    break;
                case 2:
                    SignUp(str);
                    break;
                case 3:
                    SignIn(str);
                    break;
                case 4:
                    // Implementar salir de la aplicación
                    break;
                case 5:
                    ListarUsuarios(num, str);
                    break;
                case 6:
                    // Implementar dar de alta un grupo
                    break;
                case 7:
                    // Implementar dar de baja un grupo
                    break;
                case 8:
                    // Implementar administrar un grupo
                    break;
                case 9:
                    // Implementar transmisión de un archivo
                    break;
                case 10:
                    // Implementar enviar mensaje al servidor
                    break;
                case 11:
                    // Implementar leer mensajes
                    break;
                case 12:
                    // Implementar enviar archivo al servidor
                    break;
                case 13:
                    // Implementar listar archivos
                    break;
                case 14:
                    // Implementar descargar archivo
                    break;
                case 15:
                    // Implementar configuración del servidor
                    break;
                case 16:
                    // Implementar configuración del cliente
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void SignUp(Scanner str) {

        System.out.print("Ingrese el nombre de usuario: ");
        String newUsername = str.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String newPassword = str.nextLine();
        try {
            // Establecer la conexión a la base de datos
            Connection connection = DriverManager.getConnection(BDD, USUARIO, CONTRASENA);

            // Definir la consulta SQL para insertar un nuevo usuario
            String sql = "INSERT INTO Clients (Nom_usuari, Contrasenya) VALUES (?, ?)";

            // Crear un PreparedStatement para ejecutar la consulta SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newUsername);  // Establecer el nombre de usuario

            preparedStatement.setString(2, newPassword);  // Establecer la contraseña

            // Ejecutar la consulta SQL para insertar el nuevo usuario
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario registrado con éxito.");
            } else {
                System.out.println("No se pudo registrar el usuario.");
            }

            // Cerrar la conexión y el PreparedStatement
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SignIn(Scanner str) {

        Scanner str2 = new Scanner(System.in);

        System.out.print("Ingrese el nombre de usuario: ");
        String inputUsername = str.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String inputPassword = str2.nextLine();

        try {
            // Establecer la conexión a la base de datos

            Connection connection = DriverManager.getConnection(BDD, USUARIO, CONTRASENA);

            // Definir la consulta SQL para buscar un usuario por nombre de usuario y contraseña
            String sql = "SELECT Nom_usuari,Contrasenya FROM Clients WHERE Nom_usuari = ? AND Contrasenya = ?";

            // Crear un PreparedStatement para ejecutar la consulta SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, inputUsername);  // Establecer el nombre de usuario
            preparedStatement.setString(2, inputPassword);  // Establecer la contraseña

            // Ejecutar la consulta SQL para buscar el usuario
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Inicio de sesión exitoso.");
                CONECTADOS.put(inputUsername, true);
            } else {
                System.out.println("Inicio de sesión fallido. Nombre de usuario o contraseña incorrectos.");
            }

            // Cerrar la conexión, el PreparedStatement y el ResultSet
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void ListarUsuarios(Scanner num, Scanner str) {

        try {
            // Variables
            int opcion;
            int continuar;
            boolean seguir = true;
            //

            // Hacemos la conexion a la bdd 
            Connection conexion = DriverManager.getConnection(BDD, USUARIO, CONTRASENA);

            while (seguir) {

                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println("Selecciona si deseas listar a todos los usuarios o usuarios conectados");
                System.out.println("1. Listar todos");
                System.out.println("2. Listar conectados");
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.print("Tu opción: ");
                opcion = num.nextInt();

                Statement sentencia1 = null;
                ResultSet resultado1 = null;

                switch (opcion) {
                    case 1:
                        System.out.println("Listado de todos los usuarios");

                        // Aqui es una consulta simple, no hace falta el Prepared
                        sentencia1 = conexion.createStatement();
                        resultado1 = sentencia1.executeQuery("SELECT Nom_usuari FROM Clients");

                        while (resultado1.next()) {
                            System.out.println("Nombre: " + resultado1.getString("Nom_usuari"));
                        }
                        break;

                    case 2:
                        System.out.println("Listado de todos los usuarios conectados");
                        for (HashMap.Entry<String, Boolean> conectado : CONECTADOS.entrySet()) {
                            if (CONECTADOS.containsValue(true)) {
                                String nombre = conectado.getKey();
                                System.out.println("Nombre: " + nombre);
                            }

                        }
                        break;
                }

                System.out.print("Deseas elegir otro tipo de listado? {1. si } - {2. no} ");
                continuar = str.nextInt();

                switch (continuar) {
                    case 2:
                        seguir = false;
                        conexion.close();
                        sentencia1.close();
                        resultado1.close();
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
