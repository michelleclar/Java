import java.time.Duration;
import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.commons.fields.Driver;
import org.carl.commons.fields.JooqGen;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

public class JooqCodeGenerator {
  static JooqCodeGenerator DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new JooqCodeGenerator();
  }

  public void gen(String driver, String jooqGenDatabase, DataSource dataSource, String schema,
      String includes, String excludes, String packageName, String directoryName) {
    // jooq generator config
    Configuration configuration = new Configuration()
        .withJdbc(new Jdbc().withDriver(driver).withUrl(dataSource.getJdbcUrl())
            .withUser(dataSource.getUsername()).withPassword(dataSource.getPassword()))
        .withGenerator(new Generator()
            .withDatabase(new Database().withName(jooqGenDatabase).withInputSchema(schema) // 数据库模式
                .withIncludes(includes) // 包含生成的表
                .withExcludes(excludes)) // 排除不生成的表
            .withGenerate(new Generate().withPojos(true) // 生成POJO类
                .withDaos(true)) // 生成DAO类
            .withTarget(new Target().withPackageName(packageName) // 生成类的包名
                .withDirectory(directoryName))); // 生成类的输出目录

    // execute
    try {
      GenerationTool.generate(configuration);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Builder getBuilder(String type) {
    return new Builder(type);
  }

  public static final class Builder {
    Builder(String type) {
      this.type = type;
      switch (this.type) {
        case DB.MYSQL -> {
          this.driver = Driver.MYSQL;
          this.jooqGenDatabase = JooqGen.MYSQL;
        }
        case DB.POSTGRES -> {
          this.driver = Driver.POSTGRES;
          this.jooqGenDatabase = JooqGen.POSTGRES;
        }
        case DB.MARIADB -> {
          this.driver = Driver.MARIADB;
          this.jooqGenDatabase = JooqGen.MARIADB;
        }
        default -> throw new RuntimeException("not supported this driver");
      }
    }

    String driver;
    String jooqGenDatabase;
    DataSource dataSource;
    String schema;
    String includes;
    String excludes;
    String packageName;
    String directoryName;
    String dataSourceId;
    String type;

    public Builder setSchema(String schema) {
      this.schema = schema;
      return this;
    }

    public Builder setIncludes(String includes) {
      this.includes = includes;
      return this;
    }

    public Builder setExcludes(String excludes) {
      this.excludes = excludes;
      return this;
    }

    public Builder setPackageName(String packageName) {
      this.packageName = packageName;
      return this;
    }

    public Builder setDirectoryName(String directoryName) {
      this.directoryName = directoryName;
      return this;
    }

    public Builder setDataSourceId(String id) {
      this.dataSourceId = id;
      this.dataSource = DB.getDataSource(this.type, id);
      return this;
    }

    public void execute() {
      DEFAULT_INSTANCE.gen(this.driver, this.jooqGenDatabase, this.dataSource, this.schema,
          this.includes, this.excludes, this.packageName, this.directoryName);
    }
  }

  static void codeGenByDB() {
    // String s = "demo";
    JooqCodeGenerator.getBuilder(DB.MYSQL).setDataSourceId("db1").setSchema("db").setIncludes(".*")
        .setExcludes("").setPackageName("org.gen." + DB.MYSQL).setDirectoryName("src/main/java")
        .execute();
    JooqCodeGenerator.getBuilder(DB.MARIADB).setDataSourceId("db3").setSchema("db")
        .setIncludes(".*").setExcludes("").setPackageName("org.gen." + DB.MARIADB)
        .setDirectoryName("src/main/java").execute();
    JooqCodeGenerator.getBuilder(DB.POSTGRES).setDataSourceId("db2").setSchema("public")
        .setIncludes(".*").setExcludes("").setPackageName("org.gen." + DB.POSTGRES)
        .setDirectoryName("src/main/java").execute();
  }

  static void codeGenByContainers() throws Exception {
    PostgreSQLContainer container =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest")).withDatabaseName("db")
            .withUsername("root").withPassword("root");
    container.start();
    Awaitility.await().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofSeconds(1))
        .until(container::isRunning);

    String currentDirectory = System.getProperty("user.dir");
    System.out.println("Current working directory is: " + currentDirectory);
    try {
      // Generate JOOQ code programmatically
      Configuration configuration =
          new Configuration()
              .withJdbc(
                  new Jdbc()
                      .withDriver(
                          "org.postgresql.Driver")
                      .withUrl(container.getJdbcUrl()).withUser(container.getUsername())
                      .withPassword(container.getPassword()))
              .withGenerator(new Generator()
                  .withDatabase(new Database().withName("org.jooq.meta.postgres.PostgresDatabase")
                      .withInputSchema("db") // 数据库模式
                      .withIncludes(".*") // 包含生成的表
                      .withExcludes("")) // 排除不生成的表
                  .withGenerate(new Generate().withPojos(true) // 生成POJO类
                      .withDaos(true)) // 生成DAO类
                  .withTarget(new Target().withPackageName("org.acme.generated") // 生成类的包名
                      .withDirectory("src/main/generated"))); // 生成类的输出目录

      GenerationTool.generate(configuration);

    } finally {
      container.stop();
    }
  }

  public static void main(String[] args) throws Exception {
    codeGenByContainers();
  }
}
