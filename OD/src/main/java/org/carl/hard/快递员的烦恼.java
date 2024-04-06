package org.carl.hard;

import java.util.*;
import java.util.Map.Entry;

public class 快递员的烦恼 {
  static Scanner in = new Scanner(System.in);
  static int i;

  public static void main(String[] args) {
    int n = in.nextInt();
    int m = in.nextInt();
    Map<Integer, Consumer> map = new HashMap<>();
    List<Consumer> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int i1 = in.nextInt();
      int i2 = in.nextInt();
      Consumer c = new Consumer(i1, i2);
      map.put(i1, c);
      list.add(c);

    }
    for (int i = 0; i < m; i++) {
      int i1 = in.nextInt();
      int i2 = in.nextInt();
      int i3 = in.nextInt();
      Consumer c1 = map.get(i1);
      Consumer c2 = map.get(i2);
      Pair<Consumer, Integer> p = new Pair<>();
      p.k = c2;
      p.v = i3;
      c1.list.add(p);
      Pair<Consumer, Integer> p1 = new Pair<>();
      p1.k = c1;
      p1.v = i3;

      c2.list.add(p1);
    }
    int r = Integer.MAX_VALUE;
    i = list.size();
    for (Consumer c : list) {
      r = Math.min(calc(c, list, c.l, new HashSet<>() {
        {
          add(c);
        }
      }), r);
    }

    System.out.println(r);
  }

  static int calc(Consumer current, List<Consumer> list, int r, Set<Consumer> s) {
    if (s.size() == i) {
      if (current != null)
        return r + current.l;
      return r;
    }

    if (current != null) {
      //
      if (current.list.isEmpty()) {
        // 只能回到原点
        r += current.l;
        calc(null, list, r, s);
      } else {
        for (Pair<Consumer, Integer> p : current.list) {
          int temp;
          if (s.contains(p.k)) {
            // 去过
            temp = calc(p.k, list, r + p.v, new HashSet<>(s));
          } else {
            temp = calc(p.k, list, r + p.v, new HashSet<>(s) {
              {
                add(p.k);
              }
            });
          }

          r = Math.min(temp, calc(null, list, r + p.k.l, s));
        }
      }
    } else {
      // 随机去一个点
      for (Consumer c : list) {
        List<Consumer> l = new ArrayList<>(list);
        l.remove(c);
        int temp;
        if (s.contains(c)) {
          // 表示去过
          temp = calc(c, l, r + c.l, new HashSet<>(s));
        } else {
          temp = calc(c, l, r + c.l, new HashSet<>(s) {

            {
              add(c);
            }
          });
        }
        r =  temp;
      }
    }
    return r;

  }

  static class Consumer {

    public Consumer(int i1, int i2) {
      // TODO Auto-generated constructor stub
      this.id = i1;
      this.l = i2;
    }

    int id;
    int l;
    List<Pair<Consumer, Integer>> list = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Consumer consumer = (Consumer) o;
      return id == consumer.id;
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "Consumer{" + "id=" + id + ", l=" + l + '}';
    }
  }


  static class Pair<K, V> {
    K k;
    V v;

  }
}

