package org.carl.jooq.generator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;
import io.vertx.codegen.Generator;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class JooqCodeGenerator {

  public static void main(String[] args) throws Exception {
    // Start the MariaDB test container

    try {
      // Configure Liquibase

      // Generate JOOQ code programmatically
      Configuration configuration = new Configuration()
          .withJdbc(new Jdbc().withDriver("org.mariadb.jdbc.Driver").withUrl(container.getJdbcUrl())
              .withUser(container.getUsername()).withPassword(container.getPassword()))
          .withGenerator(new Generator().withName("org.acme.generator.MyGenerator")
              .withGenerate(new Generate().withInterfaces(true).withSerializableInterfaces(true)
                  .withDaos(true).withValidationAnnotations(true).withPojosEqualsAndHashCode(true))
              .withStrategy(new Strategy().withName("org.acme.generator.MyGeneratorStrategy"))
              .withDatabase(new Database().withName("org.jooq.meta.mariadb.MariaDBDatabase")
                  .withIncludes("jooq_testshop.*").withExcludes("").withForcedTypes(
                      new ForcedType().withName("BOOLEAN").withIncludeTypes("(?i:TINYINT\\(1\\))")))
              .withTarget(new Target().withPackageName("org.acme.generated")
                  .withDirectory("src/main/generated")));

      GenerationTool.generate(configuration);

    } finally {
      // Stop the MariaDB test container
      container.stop();
    }
  }
}
