package org.cardanofoundation.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JBossLog
public class PropertiesLoader {

  public static Properties loadProperties() throws IOException {
    Properties configuration = new Properties();
    InputStream inputStream = PropertiesLoader.class
        .getClassLoader()
        .getResourceAsStream("application.yml");
    configuration.load(inputStream);
    assert inputStream != null;
    inputStream.close();
    return configuration;
  }
}
