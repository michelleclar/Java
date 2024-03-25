package org.carl.eazy;

import java.util.Scanner;

public class 靠谱的车 {
  static Scanner in = new Scanner(System.in);
static int i = 1;
  public static void main(String[] args) {
    int result = in.nextInt();
    int temp = result;
    result = 0;

    while (i <= temp) {
      result++;
      String s = String.valueOf(i);
      if (s.contains("4")) {
        calc(s);
      }
      i++;
    }

    // for (; i < temp; i++) {
    //   result++;
    //   String s = String.valueOf(i);
    //   if (s.contains("4")) {
    //     calc(s, i);
    //   }
    // }
    System.out.println(result);
  }

  static void calc(String s ){
    char[] charArray = s.toCharArray();
    for (int j = charArray.length - 1; j >= 0; j--) {
      if (charArray[j] == '4') {
        i = i + (int) Math.pow(10, charArray.length - j - 1);
      }
    }
  }
}
