package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 用连续自然数之和来表达整数 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    final int i = in.nextInt();
    List<List<Integer>> result = new ArrayList<>() {
      {
        add(List.of(i));
      }
    };
    int size = (i / 2);
    Sum sum = new Sum(i);
    sum.add(size + 1);
    for (int j = size; j > 0; j--) {

      sum.add(j);
      if (sum.total == i) {
        result.add(new ArrayList<>(sum.sum));
        sum.clear();
        continue;
      }
      if (sum.total > i) {
        sum.clear();
        continue;
      }
    }
    result.sort((o1, o2) -> {
      return o1.size() - o2.size();
    });
    result.forEach(o -> {
      StringBuilder sb = new StringBuilder();
      sb.append(i).append("=");
      o.forEach(_o -> {
        sb.append(_o).append("+");
      });
      sb.deleteCharAt(sb.length() - 1);
      System.out.println(sb);
    });
    System.out.println("Result:" + result.size());
  }

  static class Sum {
    List<Integer> sum = new ArrayList<>();
    int total = 0;
    int target;

    Sum(int target) {
      this.target = target;
    }

    void add(Integer i) {
      sum.add(i);
      total += i;
    }

    void clear(int i) {
      Integer sumFirst = sum.getFirst();
      total -= sumFirst;
      sum.remove(sumFirst);
      add(i);
    }
    void clear() {
      Integer sumFirst = sum.getFirst();
      total -= sumFirst;
      sum.remove(sumFirst);
    }

    int getTotal() {
      return this.total;
    }

    boolean cmp() {
      return total - target > 0;
    }
  }
}
