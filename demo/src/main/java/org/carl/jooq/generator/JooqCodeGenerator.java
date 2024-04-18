package org.carl.jooq.generator;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class JooqCodeGenerator {


  static String jdbcUrl = ConfigProvider.getConfig().getValue("quarkus.datasource.jdbc.url", String.class);

  static String username = ConfigProvider.getConfig().getValue("quarkus.datasource.username", String.class);

  static String password = ConfigProvider.getConfig().getValue("quarkus.datasource.password", String.class);

  static void codeGenByContainers() throws Exception {

    // Generate JOOQ code programmatically
    Configuration configuration =
        new Configuration()
                .withLogging(Logging.DEBUG)
            .withJdbc(
                new Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(jdbcUrl)
                    .withUser(username)
                    .withPassword(password))
            .withGenerator(
                new Generator()
                    .withDatabase(
                        new Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withInputSchema("db") // 数据库模式
                            .withIncludes(".*") // 包含生成的表
                            .withExcludes("")) // 排除不生成的表
                    .withGenerate(
                        new Generate()
                            .withPojos(true) // 生成POJO类
                            .withDaos(true)) // 生成DAO类
                    .withTarget(
                        new Target()
                            .withPackageName("org.carl.generated.db") // 生成类的包名
                            .withDirectory("src/main/generated"))); // 生成类的输出目录

    GenerationTool.generate(configuration);
  }

  public static void main(String[] args) throws Exception {
    codeGenByContainers();
  }
}
