package org.carl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamAPI {
    public static void listToMap() {
        List<String> list = new ArrayList<>(){{add("a");add("b");add("c");add("d");add("e");add("f");}};
        Map<String, String> map = list.stream().collect(Collectors.toMap(o -> o, Function.identity()));
    }
    public static void distinct() {
        List<String> list = new ArrayList<>(){{add("a");add("b");add("c");add("d");add("e");add("f");}};
        List<String> distinct = list.stream().distinct().toList();
    }
    public static void sort() {
        List<String> list = new ArrayList<>(){{add("a");add("b");add("c");add("d");add("e");add("f");}};
        list.sort(String::compareTo);
        System.out.println(list);
    }
    public static void reduce() {
        List<Integer> list = new ArrayList<>(){{add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(8);add(9);}};
        //sum
        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
        //avg
        list.stream().reduce((o1, o2) -> o1 * o2).ifPresent(System.out::println);
        //max
        list.stream().reduce((o1, o2) -> o1 > o2 ? o1 : o2).ifPresent(System.out::println);
        //min
        list.stream().reduce((o1,o2)-> o1 < o2 ? o1 : o2).ifPresent(System.out::println);
    }
}
