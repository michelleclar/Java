package org.carl.jooq;

import java.util.Objects;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;

public class Fruit {

  public Long id;

  public String name;
  private String description;

  public Fruit() {}

  public Fruit(String name) {
    this.name = name;
  }

  public Fruit(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Fruit)) {
      return false;
    }

    Fruit other = (Fruit) obj;

    return Objects.equals(other.name, this.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public static Fruit from(Row row) {
    return new Fruit(row.getLong("id"), row.getString("name"));
  }

  public static Multi<Fruit> findAll(PgPool client) {
    return client
        .query("SELECT id, name FROM fruits ORDER BY name ASC")
        .execute()
        .onItem()
        .transformToMulti(set -> Multi.createFrom().iterable(set))
        .onItem()
        .transform(Fruit::from);
  }
}
