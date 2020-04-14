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
@Table(name = "option")
@EntityListeners(AuditingEntityListener.class)
public class Option implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @Column(nullable = false, name = "option_text")
    private String optionText;

    @OneToMany(mappedBy = "option", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Long getOptionId() {
        return optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Option option = (Option) o;
        return Objects.equals(optionId, option.optionId) &&
                Objects.equals(optionText, option.optionText) &&
                Objects.equals(createdAt, option.createdAt) &&
                Objects.equals(updatedAt, option.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId, optionText, createdAt, updatedAt);
    }
}