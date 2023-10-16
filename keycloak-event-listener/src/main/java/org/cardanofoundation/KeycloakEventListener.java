package org.cardanofoundation;

import lombok.extern.jbosslog.JBossLog;
import org.cardanofoundation.model.EventModel;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;

@JBossLog
public class KeycloakEventListener implements EventListenerProvider {

  @Override
  public void onEvent(Event event) {
    log.info("Event: " + event.getType().name());
  }

  @Override
  public void onEvent(AdminEvent adminEvent, boolean b) {
    log.info("Admin Event: " + adminEvent.getResourceType().name());
    if (adminEvent.getResourceType().equals(ResourceType.REALM_ROLE_MAPPING) || (
        adminEvent.getResourceType().equals(ResourceType.REALM_ROLE)
            && adminEvent.getOperationType().equals(
            OperationType.DELETE))) {
      EventModel model = new EventModel();
      model.setResourcePath(adminEvent.getResourcePath());
      model.setResourceType(adminEvent.getResourceType().name());
      KeycloakEvent.push(model);
    }
  }

  @Override
  public void close() {

  }
}
