package com.challenge.demo.bean;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "choice")
@EntityListeners(AuditingEntityListener.class)
public class Choice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long choiceId;

    @Column(nullable = false, name = "choice_text")
    private String choiceText;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @OneToMany(mappedBy = "choice", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    public Long getChoiceId() {
        return choiceId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Choice choice = (Choice) o;
        return Objects.equals(choiceId, choice.choiceId) &&
                Objects.equals(question, choice.question) &&
                Objects.equals(choiceText, choice.choiceText) &&
                Objects.equals(createdAt, choice.createdAt) &&
                Objects.equals(updatedAt, choice.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId, question, choiceText, createdAt, updatedAt);
    }
}
