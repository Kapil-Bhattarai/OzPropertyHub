package products.game;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import products.Product;

@ManagedBean(name = "gameBean")
@SessionScoped
public class GameController implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private GameEJB gameEJB;

    private Product product;
    private String output;
    private Double hardDiskSpace;
    private Integer noOfCoPlayers;

    private String searchQuery;

    //states
    private int gameId = 0;
    private boolean search = false;

    // Getters and Setters
    @PostConstruct
    public void init() {
        product = new Game();
    }

    /**
     * method to add the game object
     * @return: returns the url of the final page
     */
    @Transactional
    public String addGame() {
        Game game = (Game) product;
        game.setOutput(output);
        game.setHardDiskSpace(hardDiskSpace);
        game.setNoOfCoPlayers(noOfCoPlayers);
        game.setType("Game");
        FacesContext context = FacesContext.getCurrentInstance();
        if (gameEJB.addGame(game)) {
            gameId = game.getId();
            return "detail_game.faces?faces-redirect=true";

        } else {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error creating user!",
                    "Unexpected error when creating your account.  Please contact the system Administrator");
            context.addMessage(null, message);
            return null;
        }

    }

    /**
     *
     * @param gameId: game id to display
     * @return : returns the final url
     */
    public String redirectToGameDetail(int gameId) {

        this.gameId = gameId;
        return "search_result_game.faces?faces-redirect=true";
    }

    /**
     *
     * @param gameId: id of Game
     * @return: list of movies based on gameId
     */
    public List<Game> getGameList(int gameId) {
        return gameEJB.getGameList(gameId);
    }

    /**
     *
     * @return: method to search game based on searchQuery
     */
    public String searchGame() {
        var games = gameEJB.searchGame(searchQuery);
        searchQuery = "";
        search = true;
        if (!games.isEmpty()) {
            this.gameId = games.get(0).getId();
            return "search_result_game.faces?faces-redirect=true";
        } else {
             FacesContext context = FacesContext.getCurrentInstance();
             FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Game not found! Please enter another name.","");
            context.addMessage(null, message);
            return null;
        }
    }

    /**
     *
     * @param gameId: gets the game Name based on gameId
     * @return
     */
    public String getGameName(int gameId) {
        return gameEJB.getGameName(gameId);
    }

    /**
     *
     * @return: returns the total number of games
     */
    public Long countGames() {
        return gameEJB.countGames();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Double getHardDiskSpace() {
        return hardDiskSpace;
    }

    public void setHardDiskSpace(Double hardDiskSpace) {
        this.hardDiskSpace = hardDiskSpace;
    }

    public Integer getNoOfCoPlayers() {
        return noOfCoPlayers;
    }

    public void setNoOfCoPlayers(Integer noOfCoPlayers) {
        this.noOfCoPlayers = noOfCoPlayers;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

}
