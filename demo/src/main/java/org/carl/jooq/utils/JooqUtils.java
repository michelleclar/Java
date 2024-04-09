package org.carl.jooq.utils;

import org.eclipse.microprofile.config.ConfigProvider;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JooqUtils {
  static String jdbcUrl =
      ConfigProvider.getConfig().getValue("quarkus.datasource.jdbc.url", String.class);

  static String username =
      ConfigProvider.getConfig().getValue("quarkus.datasource.username", String.class);

  static String password =
      ConfigProvider.getConfig().getValue("quarkus.datasource.password", String.class);

  public static void main(String[] args) {
    try {
      Connection connection = DriverManager.getConnection(jdbcUrl,username,password);
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet tables = metaData.getTables(null,null,"table_name",new String[]{"TABLE"});
      while (tables.next()) {
        String tablename= tables.getString("TABLE_NAME");
        ResultSet columns = metaData.getColumns(null,null,tablename,null);

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE").append(tablename).append("(");
        while (columns.next()) {
          String columnName= columns.getString("COLUMN_NAME");

          String columnType= columns.getString("TYPR_NAME");
          sb.append("\n\t").append(columnName).append(" ").append(columnType).append(",");
        }
        sb.replace(sb.length()-1,sb.length(),");");
      }

    } catch (Exception e) {
      //TODO: handle exception
      e.printStackTrace();
    }
  }
  static void writeFile(String context){
    String fileName = System.currentTimeMillis() + ".sql";
    Path path = Paths.get(fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(path,StandardCharsets.UTF_8,StandardOpenOption.APPEND)) {
      writer.write(context);
    } catch (Exception e) {
      //TODO: handle exception
      e.printStackTrace();
    }

  }
}
