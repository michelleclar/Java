package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 求字符串中所有整数的最小和 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String line = in.next();
    List<Integer> list = calc(line);
    int r = 0;
    for (Integer i : list) {
      r += i;

    }

    System.out.println(r);

  }

  static List<Integer> calc(String s) {
    List<Integer> list = new ArrayList<>();
    char[] charArray = s.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      if (Character.isDigit(charArray[i])) {
        list.add(Character.getNumericValue(charArray[i]));
        continue;
      }

      if (charArray[i] == '-' || charArray[i] == '+') {
        StringBuilder sb = new StringBuilder();
        sb.append(charArray[i]);
        while (++i < charArray.length) {
          if (!Character.isDigit(charArray[i]))
            break;
          sb.append(charArray[i]);
        }
        if (sb.length() > 1)
          list.add(Integer.parseInt(sb.toString()));
        i--;
      }

    }
    return list;

  }
}
