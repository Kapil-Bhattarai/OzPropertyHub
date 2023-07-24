package products.movie;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import products.Product;

@ManagedBean(name = "movieBean")
@SessionScoped
public class MovieController implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;
    
    @EJB
    private MovieEJB movieEJB;
        
    @Resource
    private UserTransaction utx;

    private Product product;

    private String specialFeatures;
    private Long duration;

    private String searchQuery;

    // states
    private int movieId;
    private boolean search = false;
    
    // Getters and Setters
    @PostConstruct
    public void init() {
        product = new Movie();
    }

    @Transactional
    public String addMovie() {
        Movie movie = (Movie) product;

        movie.setDuration(duration);
        movie.setSpecialFeatures(specialFeatures);
        movie.setType("Movie");
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        if(movieEJB.addMovie(movie)) {
           
        Logger.getAnonymousLogger().log(Level.INFO, "Data added successfully");
        System.out.println("Console value " + context.getViewRoot().getViewMap());
        context.getViewRoot().getViewMap().clear();
        
         movieId = movie.getId();
        return "detail_movie.faces?faces-redirect=true";
           
        } else {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error creating user!",
                    "Unexpected error when creating your account.  Please contact the system Administrator");
            context.addMessage(null, message);
           
            return null;
        }
        
    }

    
     public String redirectToMovieDetail(int movieId) {
     
        this.movieId = movieId;
        return "search_result_movie.faces?faces-redirect=true";
    }
     
    public List<Movie> getMovieList(int movieId) {
        return movieEJB.getMovieList(movieId);
    }

    public String searchMovie() {
        var movies = movieEJB.searchMovie(searchQuery);
        searchQuery = "";
        search = true;
        if (!movies.isEmpty()) {
               this.movieId = movies.get(0).getId();
            return "search_result_movie.faces?faces-redirect=true";
        } else {
             FacesContext context = FacesContext.getCurrentInstance();
             FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Movie not found! Please enter another name.","");
            context.addMessage(null, message);
            return null;
        }
    }

    public String getMovieName(String moveId) {
        return movieEJB.getMovieName(moveId);
    }

    public Long countMovies() {
        return movieEJB.countMovies();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    
}
