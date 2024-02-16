package org.carl.utils.generation;


import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.commons.fields.Driver;
import org.carl.commons.fields.JooqGen;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class JooqGeneration {
  static JooqGeneration DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new JooqGeneration();
  }

  public void gen(
      String driver,
      String jooqGenDatabase,
      DataSource dataSource,
      String schema,
      String includes,
      String excludes,
      String packageName,
      String directoryName) {
    // jooq generator config
    Configuration configuration =
        new Configuration()
            .withJdbc(
                new Jdbc()
                    .withDriver(driver)
                    .withUrl(dataSource.getJdbcUrl())
                    .withUser(dataSource.getUsername())
                    .withPassword(dataSource.getPassword()))
            .withGenerator(
                new Generator()
                    .withDatabase(
                        new Database()
                            .withName(jooqGenDatabase)
                            .withInputSchema(schema) // 数据库模式
                            .withIncludes(includes) // 包含生成的表
                            .withExcludes(excludes)) // 排除不生成的表
                    .withGenerate(
                        new Generate()
                            .withPojos(true) // 生成POJO类
                            .withDaos(true)) // 生成DAO类
                    .withTarget(
                        new Target()
                            .withPackageName(packageName) // 生成类的包名
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
          return;
        }
        case DB.POSTGRES -> {
          this.driver = Driver.POSTGRES;
          this.jooqGenDatabase = JooqGen.POSTGRES;
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
      DEFAULT_INSTANCE.gen(
          this.driver,
          this.jooqGenDatabase,
          this.dataSource,
          this.schema,
          this.includes,
          this.excludes,
          this.packageName,
          this.directoryName);
    }
  }

  public static void main(String[] args) throws Exception {
    JooqGeneration.getBuilder(DB.MYSQL)
        .setDataSourceId("db1")
        .setSchema("db")
        .setIncludes(".*")
        .setExcludes("")
        .setPackageName("org.gen")
        .setDirectoryName("ORM/JOOQ/src/main/java")
        .execute();
    // JooqGeneration.getBuilder(DB.POSTGRES)
    //     .setDataSourceId("db2")
    //     .setSchema("public")
    //     .setIncludes(".*")
    //     .setExcludes("")
    //     .setPackageName("org.gen")
    //     .setDirectoryName("ORM/src/main/java")
    //     .execute();
  }
}
