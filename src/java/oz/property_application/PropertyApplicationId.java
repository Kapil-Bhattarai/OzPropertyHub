
package oz.property_application;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class PropertyApplicationId implements Serializable {

    @Column(name = "propertyId")
    private Integer propertyId;

    @Column(name = "userd")
    private Integer userId;
    
    @Column(name = "agentId")
    private Integer agentId;

    // Constructors

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyApplicationId that = (PropertyApplicationId) o;
        return Objects.equals(propertyId, that.propertyId) &&
               Objects.equals(userId, that.userId) &&
                Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, userId, agentId);
    }
}