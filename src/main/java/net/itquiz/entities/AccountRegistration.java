package net.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false, updatable = false)
    private Account account;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(name = "pass_hash", unique = true)
    private String passHash;

    @Column(name = "pass_hash_created")
    private Timestamp passHashCreated;

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

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public Timestamp getPassHashCreated() {
        return passHashCreated;
    }

    public void setPassHashCreated(Timestamp passHashCreated) {
        this.passHashCreated = passHashCreated;
    }

    @Override
    public Serializable getId() {
        return getIdAccountRegistration();
    }

}
