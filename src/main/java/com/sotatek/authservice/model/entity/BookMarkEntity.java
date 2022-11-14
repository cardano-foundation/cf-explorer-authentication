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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private UserEntity user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BookMarkEntity bookMark = (BookMarkEntity) o;

    return new EqualsBuilder().append(tittlePage, bookMark.tittlePage)
        .append(urlPage, bookMark.urlPage).append(keyword, bookMark.keyword)
        .append(accessTime, bookMark.accessTime).append(type, bookMark.type).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(tittlePage).append(urlPage).append(keyword)
        .append(accessTime).append(type).toHashCode();
  }
}
