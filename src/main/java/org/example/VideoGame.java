package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@XmlRootElement(name = "VideoJuego")
public class VideoGame {
    // Los campos de la clase VideoGame deben estar anotados como elementos XML.

    private String id;

    private String title;

    private String genre;

    private String developer;

    private String pegi;

    private String platform;

    private String price;

    public VideoGame() {
    }

    public VideoGame(String id, String title, String genre, String developer, String pegi, String platform, String price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.developer = developer;
        this.pegi = pegi;
        this.platform = platform;
        this.price = price;
    }

    @XmlAttribute(name = "Identificador")
    public String getId() {
        return id;
    }

    @XmlElement(name = "Titulo")
    public String getTitle() {
        return title;
    }

    @XmlElement(name = "Genero")
    public String getGenre() {
        return genre;
    }

    @XmlElement(name = "Desarrolladora")
    public String getDeveloper() {
        return developer;
    }

    @XmlElement(name = "PEGI")
    public String getPegi() {
        return pegi;
    }

    @XmlElement(name = "Plataformas")
    public String getPlatform() {
        return platform;
    }

    @XmlElement(name = "Precio")
    public String getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VideoGame{" + "title=" + title + ", genre=" + genre + ", developer=" + developer + ", pegi=" + pegi + ", platform=" + platform + ", price=" + price + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoGame videoGame = (VideoGame) o;
        return Objects.equals(id, videoGame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void saveInXML(String s) {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, new File(s));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void convertXMLtoJSON(String s, String s1) {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            VideoGame videojuego = (VideoGame) unmarshaller.unmarshal(new File(s));
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject(videojuego);
            jsonArray.put(jsonObject1);
            jsonObject.put("Videojuegos", jsonArray);
            FileWriter fileWriter = new FileWriter(s1);
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
