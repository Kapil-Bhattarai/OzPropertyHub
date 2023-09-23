package config;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.OzEJB;

@Stateless
public class ConfigEJB extends OzEJB {

   public Boolean createConfig(ConfigEntity config) {
        try {
            entityManager.persist(config);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
    public ConfigEntity updateConfig(ConfigEntity ne) {
        return entityManager.merge(ne);
    }
    
    public List<ConfigEntity> getConfigs() {
        try {
            return entityManager.createNamedQuery(ConfigEntity.QUERY_GET_CONFIGS, ConfigEntity.class).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
     public ConfigEntity getConfigByKey(String key) {
        try {
            return entityManager.createNamedQuery(ConfigEntity.QUERY_GET_CONFIG_BY_ID, ConfigEntity.class).
                    setParameter("key", key).getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

}
