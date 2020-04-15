package com.challenge.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener.class)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id", referencedColumnName = "site_id")
    private Site site;

    @NotBlank
    @Length(min = 0, max = 250)
    private String question;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getQuestionId() {
        return questionId;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<QuestionAnswer> getAnswers() {
        return questionAnswers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Question question1 = (Question) o;
        return Objects.equals(questionId, question1.questionId) &&
                Objects.equals(site, question1.site) &&
                Objects.equals(question, question1.question) &&
				Objects.equals(type, question1.type) &&
                Objects.equals(questionAnswers, question1.questionAnswers) &&
                Objects.equals(createdAt, question1.createdAt) &&
                Objects.equals(updatedAt, question1.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, site, type, question, questionAnswers, createdAt, updatedAt);
    }
}
