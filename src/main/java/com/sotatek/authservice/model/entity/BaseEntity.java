package com.sotatek.authservice.model.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_by", updatable = false)
  @CreatedBy
  private String createdBy;

  @Column(name = "created_date", updatable = false, nullable = false)
  @CreatedDate
  private Instant createdDate;

  @Column(name = "modified_by")
  @LastModifiedBy
  private String modifiedBy;

  @Column(name = "modified_date")
  @LastModifiedDate
  private Instant modifiedDate;

}
