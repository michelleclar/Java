package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class 考勤信息 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int N = in.nextInt();
    in.nextLine();
    List<String[]> list = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      String str = in.nextLine();
      list.add(parse(str));
    }
    List<Boolean> r = new ArrayList<>();
    for (String[] array : list) {
      Map<String, Integer> m = new HashMap<>();
      int temp = 0;
      boolean flag = true;
      for (String str : array) {
        Integer integer = m.get(str);
        if (str.equals("late") || str.equals("leaveearly") || str.equals("absent"))
          temp++;
        else
          temp = 0;
        if (temp >= 3) {
          flag = false;
          break;
        }
        if (integer == null)
          m.put(str, 1);
        else
          m.put(str, integer + 1);
      }
      if (m.get("absent") != null && m.get("absent") > 1) {
        flag = false;
      }

      r.add(flag);
    }
    System.out.println(r);

  }

  static String[] parse(String str) {
    return str.split(" ");
  }
}
