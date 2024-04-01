package org.carl.hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class 任务处理 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int num = in.nextInt();
    List<Task> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      int start = in.nextInt();

      int end = in.nextInt();
      Task t = new Task();
      t.start = start;
      t.end = end;
      list.add(t);
    }
    list.sort(
        (o1, o2) -> {
          if (o1.start == o2.start) return o1.end - o2.end;
          return o1.start - o1.start;
        });
    Set<Integer> results = new HashSet<>();
    for (Task t : list) {
      if (t.start == t.end) {
        results.add(t.start);
        continue;
      }
      while (t.start <= t.end) {
        if (!results.contains(t.start)) {
          results.add(t.start);
          break;
        }
        t.start = t.start + 1;
      }
    }
    System.out.println(results);
  }

  static class Task {
    int start;
    int end;
  }
}
