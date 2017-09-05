package org.kj6682.hop;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * Created by luigi on 26/08/16.
 *
 * This model object guarantees the minimal required information per document on the server side.
 *
 * Postgres stores data in collections.
 * Spring Data Postgres will map the class Hop into a collection called hop.
 * If you want to change the name of the collection, you can use
 */
@Data
@Entity
class Hop {

    static enum Type {
        AUDIO, BOOK, MOVIE;
    }

    public static final String UNKNOWN_AUTHOR   = "unknown author";
    public static final String UNKNOWN_TITLE    = "unknown title";
    public static final String UNKNOWN_LOCATION = "unknown location";
    public static final String UNKNOWN_TYPE     = "unknown type";


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;

    private String author;

    private  String type;

    private  String location;

    protected Hop(){}

    public Hop(String title, String author, String type, String location) {

        Assert.hasLength(title, "A reasonable title is necessary when creating a Hop");
        Assert.hasLength(author, "A Hop needs an author");
        Assert.hasLength(type, "A strict type is necessary when creating a Hop");
        Assert.hasLength(location, "A Hop is needless without a location");

        this.title = title;
        this.author = author;
        this.type = type;
        this.location = location;
    }

}
