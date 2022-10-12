package com.sotatek.authservice.model.entity;

import com.sotatek.authservice.model.enums.EUserAction;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authentication_history")
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationHistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_action")
  @Enumerated(EnumType.STRING)
  private EUserAction userAction;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "action_time")
  private Instant actionTime;

  @Column(name = "is_success")
  private Boolean isSuccess;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

}
