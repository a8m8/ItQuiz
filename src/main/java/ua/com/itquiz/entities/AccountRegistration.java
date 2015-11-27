package ua.com.itquiz.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "account_registration")
public class AccountRegistration extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ACCOUNT_REGISTID_GENERATOR", sequenceName = "ACCOUNT_REGISTRATION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_REGISTID_GENERATOR")
    @Column(name = "id_account_registration", unique = true, nullable = false)
    private Integer idAccountRegistration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false, updatable = false)
    private Account account;

    @Column(nullable = false, unique = true)
    private String hash;

    public AccountRegistration() {
    }

    public Integer getIdAccountRegistration() {
	return idAccountRegistration;
    }

    public void setIdAccountRegistration(Integer idAccountRegistration) {
	this.idAccountRegistration = idAccountRegistration;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    public String getHash() {
	return hash;
    }

    public void setHash(String hash) {
	this.hash = hash;
    }

    @Override
    public Serializable getId() {
	return getIdAccountRegistration();
    }

}
