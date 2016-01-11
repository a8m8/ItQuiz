package net.itquiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "account")
public class Account extends AbstractBusinessEntity {

    private static final long serialVersionUID = -3104346246261090204L;

    @Id
    @SequenceGenerator(name = "ACCOUNT_IDACCOUNT_GENERATOR", sequenceName = "ACCOUNT_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_IDACCOUNT_GENERATOR")
    @Column(name = "id_account", unique = true, nullable = false)
    private Integer idAccount;

    @Column(nullable = false, length = 60, unique = true)
    private String login;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 200)
    private String fio;

    @Column(nullable = false)
    private Boolean confirmed;

    @OneToMany(mappedBy = "account")
    private List<AccountRole> accountRoles;

    @OneToOne(mappedBy = "account")
    private AccountRegistration accountRegistration;

    public Account() {
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public AccountRegistration getAccountRegistration() {
        return accountRegistration;
    }

    public void setAccountRegistration(AccountRegistration accountRegistration) {
        this.accountRegistration = accountRegistration;
    }

    @Override
    public Serializable getId() {
        return getIdAccount();
    }

}
