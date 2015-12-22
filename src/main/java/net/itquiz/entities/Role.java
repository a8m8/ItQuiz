package net.itquiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "role")
public class Role extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_role", unique = true, nullable = false)
    private Short idRole;

    @Column(nullable = false, length = 15, unique = true)
    private String title;

    public Role() {
    }

    public Short getIdRole() {
        return idRole;
    }

    public void setIdRole(Short idRole) {
        this.idRole = idRole;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Serializable getId() {
        return getIdRole();
    }

}
