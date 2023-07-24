package products.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import products.Product;

@Entity
@NamedQueries({
        @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m WHERE TYPE(m) = Movie"),
        @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
        @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title"),
        @NamedQuery(name = "Movie.findByCompany", query = "SELECT m FROM Movie m WHERE m.company = :company"),
        @NamedQuery(name = "Movie.findByPlatform", query = "SELECT m FROM Movie m WHERE m.platform = :platform"),
        @NamedQuery(name = "Movie.findByClassification", query = "SELECT m FROM Movie m WHERE m.classification = :classification"),
        @NamedQuery(name = "Movie.findBySpecialFeatures", query = "SELECT m FROM Movie m WHERE m.specialFeatures = :specialFeatures"),
        @NamedQuery(name = "Movie.findByDuration", query = "SELECT m FROM Movie m WHERE m.duration = :duration"),
        @NamedQuery(name = "Movie.findByPrice", query = "SELECT m FROM Movie m WHERE m.price = :price"),
        @NamedQuery(name = "Movie.findByStock", query = "SELECT m FROM Movie m WHERE m.stock = :stock"),
        @NamedQuery(name = "Movie.countTotal", query = "SELECT COUNT(m) FROM Movie m"),
        @NamedQuery(name = "Movie.getMoveName", query = "SELECT p.title FROM Product p WHERE p.id = :id")
})
public class Movie extends Product {

    @Column(name = "specialFeatures", nullable = false)
    private String specialFeatures;
    @Column(name = "duration", nullable = false)
    private Long duration;

 
    public Movie() {}
    
    public Movie(String specialFeatures, Long duraiton, Integer id, String title, String company, String platform, String classification, String description, Double price, int stock, String type) {
        super(id, title, company, platform, classification, description, price, stock, type);
        this.specialFeatures = specialFeatures;
        this.duration = duraiton;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
     
}
