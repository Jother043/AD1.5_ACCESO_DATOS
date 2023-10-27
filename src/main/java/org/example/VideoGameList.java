package org.example;

import com.sun.org.apache.bcel.internal.classfile.Unknown;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

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

        //Añadimos el objeto VideoGame a la lista
        //Cerramos el BufferedReader
        br.close();
        //Llamamos al método que convierte la lista en un XML
        videoGameListToXml(file);
    }

    public void videoGameListToXml(String file) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(this.getClass());
        context.createMarshaller().marshal(this, new File("cideojuegoxml.xml"));
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, new File("videojuegoxml.xml"));
    }

    public void addVideoGameToXml(VideoGame videoGame, File file) throws JAXBException, IOException {
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

