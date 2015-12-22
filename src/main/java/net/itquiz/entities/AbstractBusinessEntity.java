package net.itquiz.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author Artur Meshcheriakov
 */
@MappedSuperclass
public abstract class AbstractBusinessEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(insertable = false, updatable = false)
    private Timestamp created;

    @Column
    private Timestamp updated;

    @Column
    private Boolean active;

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getUpdated() {
        return this.updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

}
