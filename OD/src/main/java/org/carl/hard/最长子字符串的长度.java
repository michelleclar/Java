package org.carl.hard;

import java.util.Scanner;

public class 最长子字符串的长度 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String line = in.nextLine();
    line = resver(line, 3);
    System.out.println(line);
    for (int i = 0; i < line.length(); i++) {}
  }

  public static String resver(String str, int index) {
    return str.substring(index, str.length()) + str.substring(0, index);
  }
}
