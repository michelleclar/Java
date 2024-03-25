package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class 查找接口成功率最优时间段 {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int mid = in.nextInt();
    LinkedList<Pair> list = new LinkedList<>();
    int y = 0;
    in.nextLine();
    String str = in.nextLine();
    String[] strs = str.split(" ");
    for (String s : strs) {
      Integer i = Integer.parseInt(s);
      Pair pair = new Pair();
      pair.x = i;
      y = y + i;
      pair.y = y;
      list.add(pair);
    }
    mid = mid * 100;
    List<String> list2 = calc(list, mid);
    if (list2 == null)
      System.out.println(false);
    for (String s : list2) {
      System.out.print(s);
      System.out.print(" ");
    }
  }

  private static List<String> calc(LinkedList<Pair> list, int mid) {
    Map<Integer, List<String>> result = new HashMap<>();
    for (int i = 0; i < list.size(); i++) {
      for (int j = i; j < list.size(); j++) {
        Pair p2 = list.get(j);
        int ave;
        if (i != 0)
          ave = (p2.y * 100 - list.get(i - 1).y * 100) / (j - i + 1);
        else
          ave = (p2.y * 100) / (j - i + 1);

        if (ave <= mid) {
          String r = i + "-" + j;
          List<String> list2 = result.get(j-i);
          if (list2 == null)
            list2 = new ArrayList<>();
          list2.add(r);
          result.put(j - i, list2);
        }
      }
    }
    Set<Integer> keySet = result.keySet();
    int i = 0;
    for (Integer num : keySet) {
      i = i > num ? i : num;
    }
    List<String> list2 = result.get(i);
    return list2;
  }

  static class Pair {
    Integer x;
    Integer y;
  }
}
