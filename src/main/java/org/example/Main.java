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
                    } catch (IOException | JAXBException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Introduce el identificador del videojuego: ");
                        String id = sc.next();
                        if(id.length() != 5){
                            System.err.println("El identificador debe tener 5 caracteres.");
                            break;
                        }else {
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
                            videoGameList.addVideoGameToXml(videoGame1, new File(sc.next()));
                        }
                    } catch (JAXBException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    System.out.println("Introduce el nombre del fichero: ");
                    videoGameList.sortById(sc.next());
                    break;
                case 4:
                    try {
                        System.out.println("Introduce el identificador del videojuego: ");
                        String id = sc.next();
                        System.out.println("Introduce el nombre del fichero: ");
                        String file = sc.next();
                        videoGameList.borrarPorIdentificador(id, file);
                    } catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    System.out.println("Introduce el identificador del videojuego: ");
                    String id = sc.next();
                    System.out.println("Introduce el nombre del fichero: ");
                    String file = sc.next();
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
                    videoGameList.modifyAttributes(id, name, genre, developer, minAge, platform, price, file);
                    break;
                case 6:
                    try {
                        System.out.println("Introduce el identificador del videojuego: ");
                        String id1 = sc.next();
                        System.out.println("Introduce el formato del fichero: ");
                        String format = sc.next();
                        //el formato del fichero puede ser xml o json solo.
                        if (!format.equals("xml") && !format.equals("json")) {
                            System.out.println("El formato del fichero no es válido.");
                        } else {
                            System.out.println("Introduce el nombre del fichero: ");
                            String file1 = sc.next();
                            videoGameList.exportarGame(id1, format, file1);
                        }
                    } catch (JAXBException | IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 7:
                    try {
                        System.out.println("Introduce el nombre del fichero XML: ");
                        String xml = sc.next();
                        System.out.println("Introduce el nombre del fichero JSON: ");
                        String json = sc.next();
                        videoGameList.convertXMLtoJSO(xml, json);
                    } catch (JAXBException | IOException e) {
                        throw new RuntimeException(e);
                    }
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