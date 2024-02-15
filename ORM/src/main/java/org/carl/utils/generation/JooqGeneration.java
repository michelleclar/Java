package org.carl.utils.generation;

import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.commons.fileds.Driver;
import org.carl.commons.fileds.JooqGen;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;

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
                                        .withDriver(Driver.MYSQL)
                                        .withUrl(dataSource.getJdbcUrl())
                                        .withUser(dataSource.getUsername())
                                        .withPassword(dataSource.getPassword()))
                        .withGenerator(
                                new Generator()
                                        .withDatabase(
                                                new Database()
                                                        .withName(JooqGen.MYSQL)
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
                .setDirectoryName("ORM/src/main/java")
                .execute();
    }
}
