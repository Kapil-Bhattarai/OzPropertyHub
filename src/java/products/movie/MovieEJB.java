package products.movie;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class MovieEJB {

    @PersistenceContext(unitName = "oz_property_hub")
    private EntityManager entityManager;

    /**
     * 
     * @param movie: movie to save on the db
     * @return: returns true if the operation is successful otherwise false
     */
    public boolean addMovie(Movie movie) {
        try {
            entityManager.persist(movie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param movieId: returns all the movies if the moiveId = 0 otherwise returns movie based on Id
     * @return: movies list
     */
    public List<Movie> getMovieList(int movieId) {
        if (movieId == 0) {
            return entityManager.createNamedQuery("Movie.findAll", Movie.class).getResultList();
        } else {
            return entityManager.createNamedQuery("Movie.findById", Movie.class)
                    .setParameter("id", movieId)
                    .getResultList();
        }
    }

    /**
     * 
     * @param searchQuery: searchQuery to search the movie
     * @return: list of movie
     */
    public List<Movie> searchMovie(String searchQuery) {
        return entityManager.createNamedQuery("Movie.findByTitle", Movie.class)
                .setParameter("title", searchQuery)
                .getResultList();
    }

    /**
     * 
     * @param movieId: movieId of the movie
     * @return: returns the movie name of the movieId
     */
    public String getMovieName(String movieId) {
        try {
            return entityManager.createNamedQuery("Movie.getMoveName", String.class)
                    .setParameter("id", Integer.parseInt(movieId)).getSingleResult();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * @return: returns the total number of movies
     */
    public Long countMovies() {
        return entityManager.createNamedQuery("Movie.countTotal", Long.class).getSingleResult();
    }
}
