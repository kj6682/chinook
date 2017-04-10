package org.kj6682.hop;
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

@Entity
class Hop {

    static enum Type {
        AUDIO, BOOK, MOVIE;
    }


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


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hop)) return false;

        Hop hop = (Hop) o;

        if (!getId().equals(hop.getId())) return false;
        if (!getTitle().equals(hop.getTitle())) return false;
        if (!getAuthor().equals(hop.getAuthor())) return false;
        if (!getType().equals(hop.getType())) return false;
        return getLocation().equals(hop.getLocation());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getLocation().hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hop{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
