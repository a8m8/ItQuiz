package ua.com.itquiz.entities;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @author Artur Meshcheriakov
 */
@MappedSuperclass
public abstract class AbstractEntity implements Comparable<AbstractEntity>, IEntity {

    private static final long serialVersionUID = 1561671420531224878L;

    @Override
    @Transient
    public abstract Serializable getId();

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof AbstractEntity)) {
	    return false;
	}
	AbstractEntity other = (AbstractEntity) obj;
	if (getId() == null) {
	    if (other.getId() != null) {
		return false;
	    }
	} else if (!getId().equals(other.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(AbstractEntity o) {
	Object o1 = this.getId();
	Object o2 = o != null ? o.getId() : null;
	return new CompareToBuilder().append(o1, o2).toComparison();
    }

    @Override
    public String toString() {
	return new StringBuilder().append(this.getClass().getSimpleName()).append(" ")
	    .append(this.getId()).toString();
    }

}
