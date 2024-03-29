package org.carl.hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class 田忌赛马 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String a = in.nextLine();

    String b = in.nextLine();
    List<Integer> A = parse(a);

    List<Integer> B = parse(b);
    Collections.sort(A);
    
    Collections.sort(B);

    
  }

  static List<Integer> parse(String str) {
    String[] arr = str.split(" ");
    List<Integer> l = new ArrayList<>();
    for (String s : arr)
      l.add(Integer.parseInt(s));
    return l;
  }
}
