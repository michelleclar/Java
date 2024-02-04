package org.carl;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class Main {

  public static void main(String[] args) {
    RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.getDefault();
    RandomGenerator r = factory.create(System.currentTimeMillis());
    System.out.println(r.nextLong());
  }
}
