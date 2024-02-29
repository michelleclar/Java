package org.carl.jooq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.eclipse.microprofile.config.ConfigProvider;
import io.quarkus.logging.Log;

public class JooqConfig {

  public static Optional<String> readDataSources() throws IOException {
    Properties properties = new Properties();
    // 使用ClassLoader加载properties配置文件生成对应的输入流
    InputStream in =
        JooqConfig.class.getClassLoader().getResourceAsStream("application.properties");
    // 使用properties对象加载输入流
    properties.load(in);
    // 获取key对应的value值
    String password = properties.getProperty("quarkus.datasource.password");
    Log.info(password);

    return ConfigProvider.getConfig()
        .getOptionalValue("quarkus.datasource.pg.jdbc.url", String.class);
  }
}
