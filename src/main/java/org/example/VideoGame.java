package org.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VideoJuegos")
public class VideoGame {
    // Los campos de la clase VideoGame deben estar anotados como elementos XML.
    @XmlAttribute(name = "Identificador")
    private String id;
    @XmlElement(name = "Titulo")
    private String title;
    @XmlElement(name = "Genero")
    private String genre;
    @XmlElement(name = "Desarrolladora")
    private String developer;
    @XmlElement(name = "PEGI")
    private String pegi;
    @XmlElement(name = "Plataforma")
    private String platform;
    @XmlElement(name = "Precio")
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPegi() {
        return pegi;
    }

    public String getPlatform() {
        return platform;
    }

    public String getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "VideoGame{" + "title=" + title + ", genre=" + genre + ", developer=" + developer + ", pegi=" + pegi + ", platform=" + platform + ", price=" + price + '}';
    }
}
