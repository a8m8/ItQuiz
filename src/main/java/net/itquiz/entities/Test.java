package net.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "test")
public class Test extends AbstractBusinessEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TEST_ID_GENERATOR", sequenceName = "TEST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_ID_GENERATOR")
    @Column(name = "id_test", unique = true, nullable = false)
    private Long idTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @Column(nullable = false, length = 255)
    private String title;

    @Column
    private String description;

    @Column(name = "time_per_question", nullable = false)
    private Integer timePerQuestion;

    public Test() {
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desctiption) {
        this.description = desctiption;
    }

    public Integer getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(Integer timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    @Override
    public Serializable getId() {
        return getIdTest();
    }

}
