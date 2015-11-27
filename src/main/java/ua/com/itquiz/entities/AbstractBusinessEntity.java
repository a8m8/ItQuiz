package ua.com.itquiz.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Artur Meshcheriakov
 */
@MappedSuperclass
public abstract class AbstractBusinessEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, insertable = false)
    private Timestamp created;

    @Column
    private Timestamp updated;

    @Column(nullable = false, insertable = false)
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
