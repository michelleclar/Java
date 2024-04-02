package org.carl.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class 最小矩阵宽度 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int m = in.nextInt();
    int n = in.nextInt();
    int[][] arr = new int[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++)
        arr[i][j] = in.nextInt();
    }
    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < n; i++) {

      List<Integer> list = new ArrayList<>();
      for (int j = 0; j < m; j++) {
        list.add(arr[j][i]);
      }
      map.put(i, list);
    }
    int k = in.nextInt();
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      list.add(in.nextInt());
    }
    for (int i = 0; i < n; i++) {
      List<Integer> _list = new ArrayList<>(list);
      List<Integer> l = map.get(i);
      _list.removeAll(l);
      int temp = -1;
      for (int j = i + 1; j < n; j++) {
        if (_list.isEmpty()) {
          temp = j;
          break;
        }
        l = map.get(j);
        _list.removeAll(l);
      }
      if (temp != -1) {
        System.out.println(i + " " + temp);
      }
    }
  }
}
