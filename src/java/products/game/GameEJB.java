package products.game;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class GameEJB {

    @PersistenceContext(unitName = "oz_property_hub")
    private EntityManager entityManager;

    /**
     * 
     * @param game: game to save on db
     * @return: returns true if the save is successful otherwise false
     */
    public boolean addGame(Game game) {
        try {
            entityManager.persist(game);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param gameId: returns all the games if the gameId = 0 otherwise gets the game based on gameId
     * @return 
     */
    public List<Game> getGameList(int gameId) {
        if (gameId == 0) {
            return entityManager.createNamedQuery("Game.findAll", Game.class).getResultList();
        } else {
            return entityManager.createNamedQuery("Game.findById", Game.class)
                    .setParameter("id", gameId)
                    .getResultList();
        }
    }

    /**
     * 
     * @param searchQuery: query to search the game
     * @return: list of game based on searchQuery
     */
    public List<Game> searchGame(String searchQuery) {
        return entityManager.createNamedQuery("Game.findByTitle", Game.class)
                .setParameter("title", searchQuery)
                .getResultList();
    }

    /**
     * 
     * @param gameId: gameId of the Game object
     * @return: gets the name of the Game based on gameId
     */
    public String getGameName(int gameId) {
        try {
            return entityManager.createNamedQuery("Game.getGameName", String.class)
                    .setParameter("id", gameId).getSingleResult();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * @return: returns the total number of games
     */
    public Long countGames() {
        return entityManager.createNamedQuery("Game.countTotal", Long.class).getSingleResult();
    }
}
