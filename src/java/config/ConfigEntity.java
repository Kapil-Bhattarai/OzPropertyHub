package config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "OZ_CONFIG")
@NamedQueries({
    @NamedQuery(name = "ConfigEntity.findById", query = "SELECT c FROM ConfigEntity c WHERE c.config_key = :key"),
    @NamedQuery(name = "ConfigEntity.getConfigs", query = "SELECT c FROM ConfigEntity c"),
})
public class ConfigEntity implements Serializable {

    public static final String QUERY_GET_CONFIG_BY_ID = "ConfigEntity.findById";
    public static final String QUERY_GET_CONFIGS = "ConfigEntity.getConfigs";

    public ConfigEntity() { }

    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "config_key", nullable = false)
    private String config_key;

    @Lob
    @Column(name = "display", nullable = false)
    private String display;

    @Lob
    @Column(name = "value", nullable = false)
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return config_key;
    }

    public void setKey(String key) {
        this.config_key = key;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ConfigEntity(String key, String display, String value) {
        this.config_key = key;
        this.display = display;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" + "id=" + id + ", config_key=" + config_key + ", display=" + display + ", value=" + value + '}';
    }

}
