package org.carl.eazy;

import java.util.Scanner;

public class 来自异国的客人 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int k = in.nextInt();
    int n = in.nextInt();
    int m = in.nextInt();
    String calc = calc(k, m);
    char[] charArray = calc.toCharArray();
    int r = 0;
    for (char c : charArray) {
      if (Character.getNumericValue(c) == n)
        r++;
    }
    System.out.println(r);


  }

  static String calc(int k, int m) {
    if (k < m)
      return k + "";
    return calc(k / m, m) + calc(k % m, m);
  }
}
