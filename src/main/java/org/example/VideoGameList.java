package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.*;

@XmlRootElement(name = "VideoJuegos")
public class VideoGameList {

    List<VideoGame> videoGameList = new ArrayList<>();

    public VideoGameList() {
    }

    public VideoGameList(List<VideoGame> videoGameList) {
        this.videoGameList = videoGameList;
    }

    public List<VideoGame> getVideoGameList() {
        return videoGameList;
    }

    @XmlElement(name = "VideoJuego")
    public void setVideoGameList(List<VideoGame> videoGameList) {
        this.videoGameList = videoGameList;
    }

    @Override
    public String toString() {
        return "VideoGameList{" + "videoGameList=" + videoGameList + '}';
    }

    /**
     * Método que añade un videojuego leído de un fichero CSV a un XML.
     * @param file;
     * @throws IOException;
     * @throws JAXBException;
     */
    public void addVideoGameCsvToXml(String file) throws IOException, JAXBException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/videojuegos.csv"));
        //Leemos el fichero y lo guardamos en un String
        String line = br.readLine();
        //Separamos el String por comas
        String[] values = line.split(",");
        //Leemos el fichero con un bucle while saltándonos la primera línea.
        while ((line = br.readLine()) != null) {
            //Separamos el String por comas
            values = line.split(",");
            //Creamos un objeto VideoGame con los valores de cada línea
            VideoGame videoGame = new VideoGame(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
            //Añadimos el objeto VideoGame a la lista
            videoGameList.add(videoGame);
        }
        //Cerramos el BufferedReader
        br.close();
        //Llamamos al método que convierte la lista en un XML
        videoGameListToXml(file);
    }

    /**
     * Método que convierte una lista de objetos VideoGame en un XML.
     * @param file;
     * @throws JAXBException;
     */
    public void videoGameListToXml(String file) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(this.getClass());
        context.createMarshaller().marshal(this, new File("cideojuegoxml.xml"));
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, new File(file));
    }

    /**
     * Método que añade un objeto VideoGame a un XML.
     * @param videoGame;
     * @param file;
     * @throws JAXBException;
     * @throws IOException;
     */
    public void addVideoGameToXml(VideoGame videoGame, File file) throws JAXBException, IOException {
        boolean existe = false;
        for (VideoGame videojuego : videoGameList) {
            if (videojuego.getId().equals(videoGame.getId())) {
                existe = true;
                break;
            }
        }
        if(existe) {
            System.err.println("El identificador ya existe.");
        }else {
            JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            VideoGameList lisGames = (VideoGameList) unmarshaller.unmarshal(file);
            videoGameList.addAll(lisGames.getVideoGameList());
            //Añadimos el objeto VideoGame a la lista
            videoGameList.add(videoGame);
            //Llamamos al método que convierte la lista en un XML
            videoGameListToXml(file.getName());
        }

    }

    /**
     * Método utilizado para borrar un objeto VideoGame de un XML.
     * @param file;
     * @throws JAXBException;
     */
    public void borrarPorIdentificador(String identificadorBorrar, String file) throws JAXBException {
        //Creamos un objeto VideoGameList con el contenido del XML
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        VideoGameList listaVideoJuego = (VideoGameList) unmarshaller.unmarshal(new File(file));
        List<VideoGame> listaVideojuegos = listaVideoJuego.getVideoGameList();
        for(VideoGame vg : listaVideojuegos) {
            if(vg.getId().equals(identificadorBorrar)) {
                listaVideojuegos.remove(vg);
                break;
            }
        }
        listaVideoJuego.setVideoGameList(listaVideojuegos);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(listaVideoJuego, new File(file));
        System.out.println("Se ha borrado el videojuego con identificador " + identificadorBorrar);
    }

    /**
     * Método utilizado para ordenar los objetos VideoGame de un XML por identificador.
     * @param file;
     */
    public void sortById(String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            VideoGameList videoGameList = (VideoGameList) unmarshaller.unmarshal(new File(file));
            List<VideoGame> listaVideojuegos = videoGameList.getVideoGameList();
            Collections.sort(listaVideojuegos, new Comparator<VideoGame>() {
                @Override
                public int compare(VideoGame o1, VideoGame o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            videoGameList.setVideoGameList(listaVideojuegos);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(videoGameList, new File(file));
            System.out.println("Se ordenó la lista por identificador");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado para modificar los atributos de un objeto VideoGame de un XML.
     * @param file;
     *
     */
    public void modifyAttributes(String identificador, String titulo, String genero, String desarrolladora, String PEGI, String plataformas, String precio, String file) {
        for(VideoGame videojuego : videoGameList) {
            if(videojuego.getId().equals(identificador)) {
                videojuego.setTitle(titulo);
                videojuego.setGenre(genero);
                videojuego.setDeveloper(desarrolladora);
                videojuego.setPegi(PEGI);
                videojuego.setPlatform(plataformas);
                videojuego.setPrice(precio);
                break;
            }
        }
        save(file);
        System.out.println("Se ha modificado el videojuego con identificador " + identificador);
    }

    /**
     * Método utilizado para guardar un XML.
     * @param file;
     */
    public void save(String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            context.createMarshaller().marshal(this, new File(file));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, new File(file));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado para convertir un XML a JSON.
     * @param archivoXML;
     * @param archivoJSON;
     * @throws JAXBException;
     * @throws IOException;
     */
    public void convertXMLtoJSO(String archivoXML, String archivoJSON) throws JAXBException, IOException {

            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
                VideoGameList videoGameList1 = (VideoGameList) unmarshaller.unmarshal(new File(archivoXML));
            List<VideoGame> listaVideojuegos = videoGameList1.getVideoGameList();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(VideoGame videojuego : listaVideojuegos) {
                JSONObject jsonObject1 = new JSONObject(videojuego);
                System.out.println(jsonObject1.toString(4));
                jsonArray.put(jsonObject1.toString(4));
            }
            jsonObject.put("Videojuegos", jsonArray);
            FileWriter fileWriter = new FileWriter(archivoJSON);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.flush();
            fileWriter.close();
    }

    /**
     * Método utilizado para exportar un objeto VideoGame de un XML a un fichero XML o JSON.
     * @param identificador;
     * @param formato;
     * @param ficheroXML;
     * @throws IOException;
     * @throws JAXBException;
     */
    public void exportarGame(String identificador, String formato, String ficheroXML) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        VideoGameList videoGameList1 = (VideoGameList) unmarshaller.unmarshal(new File(ficheroXML));
        List<VideoGame> listaVideojuegos = videoGameList1.getVideoGameList();
        for(VideoGame videojuego : listaVideojuegos) {
            if(videojuego.getId().equals(identificador)) {
                if(formato.equalsIgnoreCase("XML")) {
                    videojuego.saveInXML("videojuego.xml");
                } else if(formato.equalsIgnoreCase("JSON")) {
                    videojuego.convertXMLtoJSON("videojuego.xml", "videojuego.json");
                }
                break;
            }
        }
    }

}

