package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 开源项目热榜 {
  static Scanner in = new Scanner(System.in);
  static final int Wwatch = 0;
  static final int Wstart = 1;
  static final int Wfork = 2;
  static final int Wissue = 3;
  static final int Wmr = 4;
  static int[] Weights = new int[5];

  public static void main(String[] args) {
    // N
    int N = in.nextInt();
    // 权重
    for (int i = 0; i < 5; i++) {
      Weights[i] = in.nextInt();
    }
    List<Open> list = new ArrayList<>();
    in.nextLine();
    for (int i = 0; i < N; i++) {
      String nextLine = in.nextLine();
      if (nextLine.isEmpty()|| nextLine.isBlank()){
        continue;
      }
      String[] strs = nextLine.split(" ");
      Open open = new Open();
      open.into(strs);
      open.total();
      list.add(open);
    }
    list.sort(
        (o1, o2) -> {
          if (o1.total == o2.total) return o1.name.compareToIgnoreCase(o2.name);
          return o2.total - o1.total;
        });
    list.forEach(
            System.out::println);
  }

  static class Open {
    static final int[] _Weights = Weights;
    String name;
    int watch;
    int start;
    int fork;
    int issue;
    int mr;
    int total;

    void into(String[] strs) {
      this.name = strs[Wwatch];
      this.watch = Integer.parseInt(strs[Wwatch + 1]);
      this.start = Integer.parseInt(strs[Wstart + 1]);
      this.fork = Integer.parseInt(strs[Wfork + 1]);
      this.issue = Integer.parseInt(strs[Wissue + 1]);
      this.mr = Integer.parseInt(strs[Wmr + 1]);
    }

    void total() {
      this.total =
          this.watch * _Weights[Wwatch]
              + this.start * _Weights[Wstart]
              + this.fork * _Weights[Wfork]
              + this.issue * _Weights[Wissue]
              + this.mr * _Weights[Wmr];
    }

    @Override
    public String toString() {
      return "Open [name="
          + name
          + ", watch="
          + watch
          + ", start="
          + start
          + ", fork="
          + fork
          + ", issue="
          + issue
          + ", mr="
          + mr
          + ", total="
          + total
          + "]";
    }
  }
}
