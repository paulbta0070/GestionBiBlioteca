package actividad2sistemagestionbiblioteca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Actividad2SistemaGestionBiblioteca {

    public static void main(String[] args) {

        ArrayList<String[]> libros = new ArrayList<>();
        LinkedList<String[]> usuarios = new LinkedList<>();
        Stack<String[]> librosPrestados = new Stack<>();
        Queue<String[]> colaEspera = new LinkedList<>();

        Scanner entrada = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("======================================");
            System.out.println(" Nombre: Christian                    ");
            System.out.println(" Cedula:                              ");
            System.out.println("   SISTEMA DE GESTION DE BIBLIOTECAS  ");
            System.out.println("======================================");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Registrar Usuario");
            System.out.println("3. Prestar Libro");
            System.out.println("4. Devolver Libro");
            System.out.println("5. Mostrar Libros Disponibles");
            System.out.println("6. Mostrar Usuarios Registrados");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");

            while (!entrada.hasNextInt()) {
                System.out.println("Error: Ingrese un numero valido !!!");
                entrada.next();
                System.out.print("Seleccione una opcion: ");
            }

            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID del Libro (Unico): ");
                    String idLibro = entrada.nextLine();
                    boolean idDuplicado = false;
                    for (String[] libro : libros) {
                        if (libro[0].equals(idLibro)) {
                            idDuplicado = true;
                            break;
                        }
                    }
                    if (idDuplicado) {
                        System.out.println("Error: el ID del Libro ya existe !!!!");
                    } else {
                        System.out.print("Ingrese el nombre del libro: ");
                        String nombreLibro = entrada.nextLine();
                        System.out.print("Ingrese el autor del libro: ");
                        String autorLibro = entrada.nextLine();
                        libros.add(new String[]{idLibro, nombreLibro, autorLibro});
                        System.out.println("Libro agregado con éxito!!!");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese la cedula del Usuario (solo numero): ");
                    while (!entrada.hasNextInt()) {
                        System.out.println("Error: Ingrese un numero valido !!!");
                        entrada.next();
                        System.out.print("Ingrese la cedula del usuario (Solo con Numeros !!!): ");
                    }
                    int cedulaUsuario = entrada.nextInt();
                    entrada.nextLine();
                    System.out.print("Ingrese el nombre del Usuario: ");
                    String nombreUsuario = entrada.nextLine();
                    System.out.print("Ingrese los apellidos del Usuario: ");
                    String apellidosUsuario = entrada.nextLine();
                    boolean cedulaDuplicado = false;
                    for (String[] usuario : usuarios) {
                        if (usuario[0].equals(String.valueOf(cedulaUsuario))) {
                            cedulaDuplicado = true;
                            break;
                        }
                    }
                    if (cedulaDuplicado) {
                        System.out.println("Error: el usuario ya existe !!!!");
                    } else {
                        usuarios.add(new String[]{String.valueOf(cedulaUsuario), nombreUsuario, apellidosUsuario});
                        System.out.println("Usuario registrado exitosamente !!!");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el ID del libro que desea prestar: ");
                    String idPrestar = entrada.nextLine();
                    System.out.print("Ingrese la cedula del usuario que presta el libro: ");
                    while (!entrada.hasNextInt()) {
                        System.out.println("Error: Ingrese un numero valido !!!");
                        entrada.next();
                        System.out.print("Ingrese la cedula del usuario (Solo con Numeros !!!): ");
                    }
                    int cedulaPrestar = entrada.nextInt();
                    entrada.nextLine();

                    boolean libroEncontrado = false;
                    for (String[] libro : libros) {
                        if (libro[0].equals(idPrestar)) {
                            librosPrestados.push(new String[]{idPrestar, String.valueOf(cedulaPrestar)});
                            libros.remove(libro);  // Remover libro de la lista de disponibles
                            libroEncontrado = true;
                            System.out.println("Libro prestado con éxito.");
                            break;  // Salir del ciclo una vez encontrado el libro
                        }
                    }
                    if (!libroEncontrado) {
                        System.out.println("Libro no disponible. ¿Desea agregar a la cola de espera? (si/no):");
                        String respuesta = entrada.nextLine();
                        if (respuesta.equalsIgnoreCase("si")) {
                            colaEspera.add(new String[]{idPrestar, String.valueOf(cedulaPrestar)});
                            System.out.println("Usuario agregado a la cola de espera.");
                        } else {
                            System.out.println("El usuario no fue agregado a la cola de espera.");
                        }
                    }
                    break;

                case 4: // Devolver libro
                    if (librosPrestados.isEmpty()) {
                        System.out.println("No hay libros prestados.");
                    } else {
                        String[] libroDevuelto = librosPrestados.pop();  // Quitar el libro de la pila de libros prestados
                        String idDevuelto = libroDevuelto[0];
                        System.out.println("Libro con ID: " + idDevuelto + " devuelto con éxito.");
                        
                        // Devolver el libro a la lista de disponibles
                        libros.add(libroDevuelto);
                        
                        // Verificar si hay usuarios en la cola de espera para este libro
                        if (!colaEspera.isEmpty()) {
                            for (String[] enEspera : colaEspera) {
                                if (enEspera[0].equals(idDevuelto)) {
                                    System.out.println("El usuario con cedula " + enEspera[1] + " ha sido notificado.");
                                    colaEspera.remove(enEspera);
                                    break;
                                }
                            }
                        }
                    }
                    break;

                case 5: // Mostrar libros disponibles
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros disponibles.");
                    } else {
                        System.out.println("Libros disponibles:");
                        for (String[] libro : libros) {
                            System.out.println("ID: " + libro[0] + ", Nombre: " + libro[1] + ", Autor: " + libro[2]);
                        }
                    }
                    break;

                case 6: // Mostrar usuarios registrados
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("Usuarios registrados:");
                        for (String[] usuario : usuarios) {
                            System.out.println("Cedula: " + usuario[0] + ", Nombre: " + usuario[1] + ", Apellidos: " + usuario[2]);
                        }
                    }
                    break;

                case 7:
                    System.out.println("Vuelve pronto");
                    break;

                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        } while (opcion != 7);

        entrada.close(); // Cerrar el escáner
    }
}
