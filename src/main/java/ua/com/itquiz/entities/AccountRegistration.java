package ua.com.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "account_registration")
public class AccountRegistration extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_account_registration", unique = true, nullable = false)
    private Integer idAccountRegistration;

    @Id
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
