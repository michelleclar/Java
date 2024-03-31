package org.carl.hard;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class 田忌赛马 {
  static Scanner in = new Scanner(System.in);

  // 2 4 6 8 10 12 14
  // 3 4 5 6 7 8 9
  public static void main(String[] args) {
    String a = in.nextLine();

    String b = in.nextLine();
    LinkedList<Integer> A = parse(a);

    LinkedList<Integer> B = parse(b);
    B.sort(
        (o1, o2) -> {
          return o2 - o1;
        });
    Collections.sort(A);
    LinkedList<Integer> C = new LinkedList<>();
    for (Integer i : B) {
      int temp = 0;
      for (Integer j : A) {
        if (j > i) temp++;
      }
      C.add(temp);
    }
    int result = factorial(C.getFirst());
    for (int i = 1; i < C.size(); i++) {
      result *= factorial(C.get(i) - C.get(i - 1));
    }

    // 2！*（2-2）！*（3-2）！=2
    System.out.println(result);
  }

  public static int factorial(int n) {
    int result = 1;
    for (int i = 1; i <= n; i++) {
      result *= i;
    }
    return result;
  }

  static LinkedList<Integer> parse(String str) {
    String[] arr = str.split(" ");
    LinkedList<Integer> l = new LinkedList<>();
    for (String s : arr) l.add(Integer.parseInt(s));
    return l;
  }
}
