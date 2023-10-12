package org.cardanofoundation;

import lombok.extern.jbosslog.JBossLog;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

@JBossLog
public class KeycloakEventListenerFactory implements EventListenerProviderFactory {

  @Override
  public EventListenerProvider create(KeycloakSession keycloakSession) {
    return new KeycloakEventListener();
  }

  @Override
  public void init(Config.Scope scope) {
    log.info("Init");
  }

  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    log.info("Post init");
  }

  @Override
  public void close() {
  }

  @Override
  public String getId() {
    return "mapping-role-event-listener";
  }
}
