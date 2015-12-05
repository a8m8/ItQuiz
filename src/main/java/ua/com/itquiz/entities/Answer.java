package ua.com.itquiz.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Artur Meshcheriakov
 */
@Entity
@Table(name = "answer")
public class Answer extends AbstractBusinessEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ANSWER_ID_GENERATOR", sequenceName = "ANSWER_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_ID_GENERATOR")
    @Column(name = "id_answer", unique = true, nullable = false)
    private Long idAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean correct;

    public Answer() {
    }

    public Long getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Long idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public Serializable getId() {
        return getIdAnswer();
    }

}
