
package oz.property_application;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class PropertyApplicationId implements Serializable {

    @Column(name = "propertyId")
    private Long propertyId;

    @Column(name = "userd")
    private Long userId;
    
    @Column(name = "agentId")
    private Long agentId;

    // Constructors

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
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