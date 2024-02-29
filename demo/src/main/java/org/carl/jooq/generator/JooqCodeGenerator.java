package org.carl.jooq.generator;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.ForcedType;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Strategy;
import org.jooq.meta.jaxb.Target;
import io.vertx.codegen.Generator;
import jakarta.inject.Inject;

public class JooqCodeGenerator {
  @Inject
  static DataSourceConfig mysql;
   

  public static void main(String[] args) throws Exception {
    

    Configuration configuration =
        new Configuration()
            .withJdbc(
                new Jdbc()
                    .withDriver("org.mariadb.jdbc.Driver")
                    .withUrl(mysql.getJdbcUrl())
                    .withUser(mysql.getUserName())
                    .withPassword(mysql.getPassword()))
            .withGenerator(
                new Generator()
                    .withName("org.acme.generator.MyGenerator")
                    .withGenerate(
                        new Generate()
                            .withInterfaces(true)
                            .withSerializableInterfaces(true)
                            .withDaos(true)
                            .withValidationAnnotations(true)
                            .withPojosEqualsAndHashCode(true))
                    .withStrategy(new Strategy().withName("org.acme.generator.MyGeneratorStrategy"))
                    .withDatabase(
                        new Database()
                            .withName("org.jooq.meta.mariadb.MariaDBDatabase")
                            .withIncludes("jooq_testshop.*")
                            .withExcludes("")
                            .withForcedTypes(
                                new ForcedType()
                                    .withName("BOOLEAN")
                                    .withIncludeTypes("(?i:TINYINT\\(1\\))")))
                    .withTarget(
                        new Target()
                            .withPackageName("org.acme.generated")
                            .withDirectory("src/main/generated")));

    GenerationTool.generate(configuration);
  }
}
