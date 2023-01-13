package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.EBookMarkType;
import java.util.Objects;
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
import org.hibernate.Hibernate;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMarkEntity extends BaseEntity {

  @Column(name = "url_page", nullable = false)
  private String urlPage;

  @Column(name = "keyword")
  private String keyword;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private EBookMarkType type;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @EqualsAndHashCode.Exclude
  private UserEntity user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    BookMarkEntity that = (BookMarkEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
