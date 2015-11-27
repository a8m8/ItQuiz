package ua.com.itquiz.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "account_role")
public class AccountRole extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ACCOUNT_ROLE_ID_GENERATOR", sequenceName = "ACCOUNT_ROLE_SEQ",
		       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ROLE_ID_GENERATOR")
    @Column(name = "id_account_role", unique = true, nullable = false)
    private Long idAccountRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    public AccountRole() {
    }

    public AccountRole(Account account, Role role) {
	super();
	this.account = account;
	this.role = role;
    }

    public Long getIdAccountRole() {
	return idAccountRole;
    }

    public void setIdAccountRole(Long idAccountRole) {
	this.idAccountRole = idAccountRole;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

    @Override
    public Serializable getId() {
	return getIdAccountRole();
    }

}
