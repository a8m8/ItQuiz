package net.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "test_result")
public class TestResult extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TESTRESULT_ID_GENERATOR", sequenceName = "TEST_RESULT_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TESTRESULT_ID_GENERATOR")
    @Column(name = "id_test_result", unique = true, nullable = false)
    private Long idTestResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test")
    private Test test;

    @Column(name = "correct_count", nullable = false)
    private Integer correctCount;

    @Column(name = "all_questions_count", nullable = false)
    private Integer allQuestionsCount;

    @Column(nullable = false, insertable = false)
    private Timestamp created;

    @Column(name = "test_title", nullable = false)
    private String testTitle;

    public TestResult() {
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public Long getIdTestResult() {
        return idTestResult;
    }

    public void setIdTestResult(Long idTestResult) {
        this.idTestResult = idTestResult;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getAllQuestionsCount() {
        return allQuestionsCount;
    }

    public void setAllQuestionsCount(Integer allQuestionsCount) {
        this.allQuestionsCount = allQuestionsCount;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public Serializable getId() {
        return getIdTestResult();
    }

}
