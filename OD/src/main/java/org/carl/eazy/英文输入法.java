package org.carl.eazy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class 英文输入法 {
  static Scanner in = new Scanner(System.in);
  static char[] arr;

  public static void main(String[] args) {
    String nextLine = in.nextLine();
    String target = in.nextLine();
    arr = target.toCharArray();
    List<String> list = parse(nextLine);
    list.sort((o1, o2) -> {
      return o1.compareTo(o2);
    });
    List<String> r = new ArrayList<>();
    for (String s : list) {
      if (calc(s)) {
        r.add(s);
      }
    }
    System.out.println(r);
  }

  static boolean calc(String s) {
    char[] charArray = s.toCharArray();
    if (charArray.length < arr.length)
      return false;
    for (int i = 0; i < arr.length; i++) {
      if (!(arr[i] == charArray[i]))
        return false;
    }
    return true;
  }

  static List<String> parse(String str) {
    String[] split = str.split(" ");
    List<String> l = new ArrayList<>();
    for (String s : split) {
      if (s.contains(",")) {
        String[] split2 = s.split(",");
        List<String> asList = Arrays.asList(split2);
        l.addAll(asList);
      } else {
        l.add(s);
      }

    }
    return l;
  }
}
