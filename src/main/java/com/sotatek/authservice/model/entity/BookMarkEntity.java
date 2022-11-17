package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.EBookMarkType;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Builder
public class BookMarkEntity extends BaseEntity {

  @Column(name = "tittle_page")
  private String tittlePage;

  @Column(name = "url_page", nullable = false)
  private String urlPage;

  @Column(name = "keyword")
  private String keyword;

  @Column(name = "access_time", nullable = false)
  private Instant accessTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private EBookMarkType type;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @EqualsAndHashCode.Exclude
  private UserEntity user;
}
