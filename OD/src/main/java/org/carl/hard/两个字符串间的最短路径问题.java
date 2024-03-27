package org.carl.hard;

import java.util.Scanner;

public class 两个字符串间的最短路径问题 {
  static Scanner in = new Scanner(System.in);
  static int i_a = 0;
  static int i_b = 0;
  static Str a;

  static Str b;

  public static void main(String[] args) {
    a = new Str("0" + in.nextLine());

    b = new Str("0" + in.nextLine());
    i_a = a.s.length();
    i_b = b.s.length();
    if (i_a == 0 || i_b == 0) return;
    int result = calc(0, 0, 0);
    System.out.println(result);
  }

  static int calc(int x, int y, int result) {
    if (x == i_a - 1 && y == i_b - 1) return result;

    // 判断 是否 为 斜边
    if (x + 1 < i_a && y + 1 < i_b) {
      if (a.getChar(x + 1) == b.getChar(y + 1)) return calc(x + 1, y + 1, ++result);
      result++;
      return Math.min(calc(x + 1, y, result), calc(x, y + 1, result));
    }
    if (x + 1 < i_a) return calc(x + 1, y, ++result);

    return calc(x, y + 1, ++result);
  }

  static class Str {
    String s;
    char[] arr;

    Str(String s) {
      this.s = s;
      calc();
    }

    void calc() {
      this.arr = s.toCharArray();
    }

    char getChar(int index) {
      return arr[index];
    }
  }
}
