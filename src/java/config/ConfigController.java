package config;

import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import oz.MessageType;

@ManagedBean(name = "configBean")
@SessionScoped
public class ConfigController {
    
    @EJB
    private ConfigEJB configEJB;
        
    @PersistenceContext
    private EntityManager em;
 
    public String getConfigByKey(MessageType key) {
       return configEJB.getConfigByKey(key.name()).getValue(); 
    } 
}
