package com.challenge.demo.bean;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "site")
@EntityListeners(AuditingEntityListener.class)
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "site_id")
	private Long siteId;

	@Column(nullable = false, name = "site_uuid")
	private UUID siteUUID;

	@Column(nullable = false, name = "url")
	private String url;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public UUID getSiteUUID() {
		return siteUUID;
	}

	public void setSiteUUID(UUID siteUUID) {
		this.siteUUID = siteUUID;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Site site = (Site) o;
		return Objects.equals(siteId, site.siteId) &&
				Objects.equals(siteUUID, site.siteUUID) &&
				Objects.equals(url, site.url) &&
				Objects.equals(createdAt, site.createdAt) &&
				Objects.equals(updatedAt, site.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(siteId, siteUUID, url, createdAt, updatedAt);
	}
}