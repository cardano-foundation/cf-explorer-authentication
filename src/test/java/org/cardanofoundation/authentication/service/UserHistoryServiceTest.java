package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.service.impl.UserHistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserHistoryServiceTest {

  @InjectMocks
  private UserHistoryServiceImpl userHistoryService;

  @Mock
  private UserHistoryRepository userHistoryRepository;

  @Test
  void whenSaveUserHistory() {
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    Instant actionTime = Instant.parse("2018-11-30T18:35:24.00Z");
    UserHistoryEntity userHistory = UserHistoryEntity.builder().user(user).actionTime(actionTime)
        .userAction(EUserAction.LOGIN).build();
    when(userHistoryRepository.save(any())).thenReturn(userHistory);
    userHistoryService.saveUserHistory(EUserAction.LOGIN, actionTime, user);
    verify(userHistoryRepository).save(any());
  }
}
