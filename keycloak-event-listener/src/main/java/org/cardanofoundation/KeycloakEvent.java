package org.cardanofoundation;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.cardanofoundation.model.EventModel;
import org.cardanofoundation.properties.PropertiesLoader;

@JBossLog
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeycloakEvent {

  public static void push(EventModel model) {
    try {
      log.info("Starting push status user to authentication...");
      Properties conf = PropertiesLoader.loadProperties();
      String secretCode = System.getenv().get("SECRET_CODE");
      String forwardEventUrl = System.getenv().get("FORWARD_EVENT_URL");
      log.info("url=" + forwardEventUrl);
      URL url = new URL(forwardEventUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json; utf-8");
      ObjectMapper mapper = new ObjectMapper();
      model.setSecretCode(secretCode);
      String modelStr = mapper.writeValueAsString(model);
      OutputStream os = conn.getOutputStream();
      os.write(modelStr.getBytes());
      os.flush();
      if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
        log.error("Failed : HTTP error code : " + conn.getResponseMessage());
      }
      conn.disconnect();
      log.info("Send event " + model.getResourceType() + " done!");
    } catch (IOException | RuntimeException e) {
      log.error("Error: " + e.getMessage());
    }
  }
}
