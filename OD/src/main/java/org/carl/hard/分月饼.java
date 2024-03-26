package org.carl.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class 分月饼 {
  static Scanner in = new Scanner(System.in);

  static List<String> before = new ArrayList<>();
  static Set<String> after = new HashSet<>();

  public static void main(String[] args) {
    int m = in.nextInt();
    int n = in.nextInt();
    n = n - m;
    calc(n, "");
    int result = 0;
    for (String s : after) {
      if (s.length() <= 3) {
        result++;
      }
    }
    System.out.println(result);
    // System.out.println(before.size());
    // System.out.println(after);
  }

  static void calc(int n, String s) {
    if (n == 0) {
      s = s.substring(1);
      // System.out.println(s);
      before.add(s);
      String[] split = s.split("[+]");
      Arrays.sort(split);
      after.add(append(split));

      return;
    }
    for (int i = 1; i <= n; i++) {
      calc(n - i, s + "+" + i);
    }
  }

  static String append(String[] strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) {
      sb.append(s).append("+");
    }

    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
