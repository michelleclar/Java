package org.carl._random;

import java.util.*;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class demo {
  public static void main(String[] args) {
//    test();
    testFunction();
//    RandomGeneratorFactory<RandomGenerator> factory = getGeneratorFactory("random");
//    RandomGenerator r = factory.create(System.currentTimeMillis());
//    System.out.println(r.nextLong());
  }

  public static RandomGeneratorFactory<RandomGenerator> getGeneratorFactory(String seed) {
    return RandomGeneratorFactory.of(seed);
  }

  public static RandomGeneratorFactory<RandomGenerator> getGeneratorFactory() {
    return RandomGeneratorFactory.getDefault();
  }

  public static void test() {
    Stream<ServiceLoader.Provider<RandomGenerator>> stream = ServiceLoader.load(RandomGenerator.class).stream();
    Stream<ServiceLoader.Provider<RandomGenerator>> providerStream = stream
            .filter(p -> !p.type().isInterface());
    Map<String, ServiceLoader.Provider<RandomGenerator>> collect = providerStream
            .collect(Collectors.toMap(p -> p.type().getSimpleName(), Function.identity()));
    Function<Object, Object> identity = Function.identity();

    System.out.println(collect);
  }

  public static void testFunction(){
    List<String> list = Arrays.asList("a", "b", "c");
    Function<String, String> identity = Function.identity();
    List<String> result = new ArrayList<>(list);
    System.out.println(result);
  }

}
