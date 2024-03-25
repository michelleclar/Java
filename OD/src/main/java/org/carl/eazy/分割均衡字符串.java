package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 分割均衡字符串 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String s = in.nextLine();
    List<Count> result = new ArrayList<>();
    char[] charArray = s.toCharArray();
    int count_x = 0;
    int count_y = 0;

    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];

      if ('X' == c) {
        count_x++;
      }
      if ('Y' == c) {
        count_y++;
      }
      Count count = new Count(count_x, count_y);
      result.add(count);
    }
    for (int i = 0; i < result.size(); i++) {
    }
  }

  static int calc(List<Count> list, int index) {
    Count c;
    if (index - 1 < 0)
      c = new Count(0, 0);
    else
      c = list.get(index - 1);
    for (int i = index; i < list.size(); i++) {
      Count _c = list.get(i);
      int count_x = _c.X - c.X;
      int count_y = _c.Y - c.Y;
      if (count_y == count_x)
        return index;
    }
    return -1;
  }

  static class Count {
    Integer X;
    Integer Y;

    public Count(Integer x, Integer y) {
      X = x;
      Y = y;
    }
  }
}
