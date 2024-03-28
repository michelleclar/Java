package org.carl.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class _5G网络建设 {
  static Scanner in = new Scanner(System.in);

  /*
   * 3 3 1 2 7 0 1 3 1 0 2 3 5 0
   */
  public static void main(String[] args) {
    int N = in.nextInt();
    int M = in.nextInt();
    List<Edgs> lEdgs = new ArrayList<>();
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < M; i++) {
      int X = in.nextInt();
      int Y = in.nextInt();
      int Z = in.nextInt();
      int P = in.nextInt();
      Edgs e = new Edgs(X, Y, Z);
      lEdgs.add(e);
      if (P == 1) list.addAll(Arrays.asList(X, Y));
    }
    Set<Integer> set = new HashSet<>(list);
    lEdgs.sort(
        (o1, o2) -> {
          return o1.l - o2.l;
        });
    int result = 0;
    for (Edgs e : lEdgs) {
      if (set.contains(e.a) && set.contains(e.b)) {
        // 有环
        continue;
      }
      result += e.l;
      set.add(e.a);

      set.add(e.b);
    }
    if (set.size() == N) System.out.println(result);
    else System.out.println(-1);
  }

  static class Edgs {
    Integer a;
    Integer b;
    Integer l;

    public Edgs(Integer a, Integer b, Integer l) {
      this.a = a;
      this.b = b;
      this.l = l;
    }
  }
}
