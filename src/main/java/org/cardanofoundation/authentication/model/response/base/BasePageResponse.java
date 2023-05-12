package org.cardanofoundation.authentication.model.response.base;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasePageResponse<T> {

  private List<T> data;

  private long totalItems;
}
