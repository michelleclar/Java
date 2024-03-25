package org.carl.eazy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class 虚拟游戏理财 {

  static Scanner in = new Scanner(System.in);
  static int total;
  static int sum;
  static int num;

  /* 5 10000 100
  100 200 300 400 500
  10 20 30 40 50
  100 200 300 400 500 */
  public static void main(String[] args) {
    // 总数
    total = in.nextInt();
    // 总投资
    sum = in.nextInt();
    // 风险
    num = in.nextInt();
    List<Productor> list = new ArrayList<>();
    for (int i = 0; i < total; i++) {
      Productor p = new Productor();
      p.i = in.nextInt();
      list.add(p);
    }
    for (Productor p : list) {
      p.j = in.nextInt();
    }
    for (Productor p : list) {
      p.k = in.nextInt();
      p.calc();
    }
//    list.sort(
//        (o1, o2) -> {
//          return o2.wight - o1.wight;
//        });
    // n*n
    LinkedList<Integer> results = new LinkedList<>();
    for (int i = 0; i < list.size(); i++) {
      Productor p = list.get(i);
      int temp = num - p.j;
      if (temp <= 0) continue;
      int result = 0;
      for (int j = i; j < list.size(); j++) {
        Productor _p = list.get(j);
        if (j==i) continue;
        if (temp - _p.j < 0) continue;
        if (p.i >= _p.i) result = calc(p, _p);
        else result = calc(_p, p);
        results.add(result);
        result = 0;
      }
    }
    results.sort(
        (o1, o2) -> {
          return o2 - o1;
        });
    System.out.println(results.getFirst());
  }

  static int calc(Productor p1, Productor p2) {
    if (p1.k >= sum) return p1.i * sum;
    if (p2.k<=(sum-p1.k)) return  p1.wight + p2.wight;
    return p1.wight + p2.i * (sum - p1.k);
  }

  static class Productor {
    // 回报
    int i;
    // 风险
    int j;
    // 最大投资额
    int k;

    int wight;

    void calc() {
      this.wight = this.i * this.k;
      // this.wight = this.k * this.i - this.j * this.k;
    }
  }
}
