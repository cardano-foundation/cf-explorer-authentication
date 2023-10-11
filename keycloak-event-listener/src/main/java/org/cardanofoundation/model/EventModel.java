package org.cardanofoundation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventModel {

  private String resourceType;

  private String resourcePath;
  private String secretCode;
}
