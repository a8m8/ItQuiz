package net.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "question")
public class Question extends AbstractBusinessEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "QUESTION_ID_GENERATOR", sequenceName = "QUESTION_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_ID_GENERATOR")
    @Column(name = "id_question", unique = true, nullable = false)
    private Long idQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test", nullable = false)
    private Test test;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "question")
    @OrderBy("idAnswer")
    private List<Answer> answers;

    public Question() {
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public Serializable getId() {
        return getIdQuestion();
    }

}
