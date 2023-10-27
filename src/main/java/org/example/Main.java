package org.example;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean salir = false;
        int opcion;
        VideoGameList videoGameList = new VideoGameList();
        VideoGame videoGame = new VideoGame();

        while (!salir) {

            System.out.print(menu());
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    try {
                        videoGameList.addVideoGameCsvToXml("videojuegos.csv");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Introduce el identificador del videojuego: ");
                        String id = sc.next();
                        System.out.println("Introduce el nombre del videojuego: ");
                        String name = sc.next();
                        System.out.println("Introduce el género del videojuego: ");
                        String genre = sc.next();
                        System.out.println("Introduce el desarrollador del videojuego: ");
                        String developer = sc.next();
                        System.out.println("Introduce la edad mínima del videojuego: ");
                        String minAge = sc.next();
                        System.out.println("Introduce la plataforma del videojuego: ");
                        String platform = sc.next();
                        System.out.println("Introduce el precio del videojuego: ");
                        String price = sc.next();
                        VideoGame videoGame1 = new VideoGame(id, name, genre, developer, minAge, platform, price);
                        System.out.println("Introduce el nombre del fichero: ");
                        videoGameList.addVideoGameToXml(videoGame1,new File(sc.next()));
                    } catch (JAXBException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.err.println("Introduce una opción válida.");
            }

        }
    }


    public static String menu() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. Cargar CSV en XML.\n");
        sb.append("2. insertar un Videojuego en la BBDD.\n");
        sb.append("3. Ordenar el fichero que representa la BBDD por el campo identificador.\n");
        sb.append("4. Borrar un video juego utilizando su identificador.\n");
        sb.append("5. Modificar los atributos de un video juego concreto representado por su identificador.\n");
        sb.append("6. Exportar la información de un videojuego concreto representado por su id de xml a json.\n");
        sb.append("7. Convertir la BBDD XML a un fichero JSON.\n");
        sb.append("8. Salir.\n");
        sb.append("Introduce una opción: ");
        return sb.toString();
    }
}