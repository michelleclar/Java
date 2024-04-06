package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class 内存冷热标记 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int N = in.nextInt();
    Map<Integer, Integer> m = new HashMap<>();
    for (int i = 0; i < N; i++) {
      int i1 = in.nextInt();
      m.merge(i1, 1, Integer::sum);

    }
    int K = in.nextInt();
    List<Page> r = new ArrayList<>();
    for (Entry<Integer, Integer> entry : m.entrySet()) {
      int i = entry.getValue();
      if (i >= K) {
        Page p = new Page();
        p.count = i;
        p.index = entry.getKey();
        r.add(p);
      }
    }
    r.sort((o1, o2) -> {
      if (o1.count == o2.count) {
        return o1.index - o2.index;
      }
      return o2.count - o1.count;
    });
    System.out.println(r.size());
    System.out.println(r);

  }

  static class Page {
    int count;
    int index;

    @Override
    public String toString() {
      return index + "";
    }


  }
}
