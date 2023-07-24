package products.game;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import products.Product;

@Entity
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g where TYPE(g) = Game"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByTitle", query = "SELECT g FROM Game g WHERE g.title = :title"),
    @NamedQuery(name = "Game.findByCompany", query = "SELECT g FROM Game g WHERE g.company = :company"),
    @NamedQuery(name = "Game.findByPlatform", query = "SELECT g FROM Game g WHERE g.platform = :platform"),
    @NamedQuery(name = "Game.findByClassification", query = "SELECT g FROM Game g WHERE g.classification = :classification"),
    @NamedQuery(name = "Game.findByOutput", query = "SELECT g FROM Game g WHERE g.output = :output"),
    @NamedQuery(name = "Game.findByHardDiskSpace", query = "SELECT g FROM Game g WHERE g.hardDiskSpace = :hardDiskSpace"),
    @NamedQuery(name = "Game.findByNoOfCoPlayers", query = "SELECT g FROM Game g WHERE g.noOfCoPlayers = :noOfCoPlayers"),
    @NamedQuery(name = "Game.findByPrice", query = "SELECT g FROM Game g WHERE g.price = :price"),
    @NamedQuery(name = "Game.findByStock", query = "SELECT g FROM Game g WHERE g.stock = :stock"),
    @NamedQuery(name = "Game.countTotal", query = "SELECT COUNT(g) FROM Game g"),
    @NamedQuery(name = "Game.getGameName", query = "SELECT p.title FROM Product p WHERE p.id= :id")
})
public class Game extends Product {

    @Column(name = "hasHdOutput", nullable = false)
    private String output;
    @Column(name = "hardDiskSpace", nullable = false)
    private Double hardDiskSpace;
    @Column(name = "noOfCoPlayers", nullable = false)
    private int noOfCoPlayers;

    public Game() {}

    public Game(String output, Double hardDiskSpace, int noOfCoPlayers, Integer id, String title, String company, String platform, String classification, String description, Double price, int stock, String type) {
        super(id, title, company, platform, classification, description, price, stock, type);
        this.output = output;
        this.hardDiskSpace = hardDiskSpace;
        this.noOfCoPlayers = noOfCoPlayers;
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

    public int getNoOfCoPlayers() {
        return noOfCoPlayers;
    }

    public void setNoOfCoPlayers(int noOfCoPlayers) {
        this.noOfCoPlayers = noOfCoPlayers;
    }
    
}
