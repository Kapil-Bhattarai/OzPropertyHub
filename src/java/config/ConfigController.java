package config;

import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import oz.MessageType;

@ManagedBean(name = "configBean")
@SessionScoped
public class ConfigController {
    
    @EJB
    private ConfigEJB configEJB;
        
    @PersistenceContext
    private EntityManager em;
 
    String updatedKey;
    
    public String getConfigByKey(MessageType key) {
       return configEJB.getConfigByKey(key.name()).getValue(); 
    } 
    
     public List<ConfigEntity> getAllConfigs() {
       return configEJB.getConfigs();
    } 

    public String getUpdatedKey() {
        return updatedKey;
    }

    public void setUpdatedKey(String updatedKey) {
        this.updatedKey = updatedKey;
    }

     public void inputValueChanged(ValueChangeEvent event) {
        String newValue = event.getNewValue().toString();
        setUpdatedKey(newValue);
    }
     public String updateConfig(ConfigEntity ne) {
         ne.setValue(updatedKey);
        configEJB.updateConfig(ne);
        return "/dashboard/admin/admin_config_dashboard.faces?faces-redirect=true";
    }
}
