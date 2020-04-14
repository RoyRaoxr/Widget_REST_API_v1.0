package com.challenge.demo.bean;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "option")
@EntityListeners(AuditingEntityListener.class)
public class Option implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @Column(nullable = false, name = "option_uuid")
    private UUID optionUUID;

    @Column(nullable = false, name = "option_title")
    private String optionTitle;

//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
//    private Question question;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public UUID getOptionUUID() {
        return optionUUID;
    }

    public void setOptionUUID(UUID optionUUID) {
        this.optionUUID = optionUUID;
    }

    public Long getOptionId() {
        return optionId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
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
                Objects.equals(optionTitle, option.optionTitle) &&
                Objects.equals(optionUUID, option.optionUUID) &&
                Objects.equals(createdAt, option.createdAt) &&
                Objects.equals(updatedAt, option.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId, optionTitle, optionUUID, createdAt, updatedAt);
    }
}