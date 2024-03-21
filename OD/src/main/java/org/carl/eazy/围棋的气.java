package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class 围棋的气 {
  // 0 5 8 9 9 10
  // 5 0 9 9 9 8
  // 0 - 18
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    // put(m, 0);
    // put(m, 1);
    Point b_point_1 = new Point(0, 5);
    Point b_point_2 = new Point(8, 9);
    Point b_point_3 = new Point(9, 10);

    Point w_point_1 = new Point(5, 0);
    Point w_point_2 = new Point(9, 9);
    Point w_point_3 = new Point(9, 8);
    Entry e1 = new Entry(w_point_1, b_point_1);

    Entry e2 = new Entry(w_point_2, b_point_2);

    Entry e3 = new Entry(w_point_3, b_point_3);
    List<Entry> l = new ArrayList<>();
    l.add(e1);
    l.add(e2);
    l.add(e3);
    Set<Point> w_s = new HashSet<>();

    Set<Point> b_s = new HashSet<>();
    for (Entry entry : l) {
      count(w_s, entry.w);
      count(b_s, entry.b);
    }
    System.out.println("write: " + w_s.size());

    System.out.println("blac: " + b_s.size());

    in.close();
  }

  static void count(Set<Point> s, Point b) {
    s.remove(b);
    // 0,0
    if (b.x == 0 && b.y == 0) {
      s.add(new Point(0, 1));
      s.add(new Point(1, 0));
      return;
    }
    // 18.18
    if (b.x == 18 && b.y == 18) {
      s.add(new Point(17, 18));
      s.add(new Point(18, 17));
      return;
    }
    // 0,18
    if (b.x == 0 && b.y == 18) {
      s.add(new Point(0, 17));
      s.add(new Point(1, 18));
      return;
    }
    // 18,0
    if (b.x == 18 && b.y == 0) {
      s.add(new Point(18, 1));
      s.add(new Point(17, 0));
      return;
    }
    // 0,(1-17)
    if (b.x == 0) {
      s.add(new Point(b.x + 1, b.y));
      s.add(new Point(b.x, b.y + 1));
      s.add(new Point(b.x, b.y - 1));
      return;
    }
    // (1,17),0
    if (b.y == 0) {
      s.add(new Point(b.x, b.y + 1));
      s.add(new Point(b.x + 1, b.y));
      s.add(new Point(b.x - 1, b.y));
      return;
    }
    // 18,(1,17)
    if (b.x == 18) {
      s.add(new Point(b.x - 1, b.y));
      s.add(new Point(b.x, b.y + 1));
      s.add(new Point(b.x, b.y - 1));
      return;
    }
    // (1,17),18
    if (b.y == 18) {
      s.add(new Point(b.x, b.y - 1));
      s.add(new Point(b.x + 1, b.y));
      s.add(new Point(b.x - 1, b.y));
      return;
    }
    // (1,17)(1,17)
    s.add(new Point(b.x + 1, b.y));
    s.add(new Point(b.x - 1, b.y));
    s.add(new Point(b.x, b.y - 1));
    s.add(new Point(b.x, b.y + 1));

  }

  static void put(Map<Integer, List<Point>> m, int i) {
    List<Point> lP = new ArrayList<>();
    while (in.hasNext()) {
      Point point = new Point(in.nextInt(), in.nextInt());
      lP.add(point);
      System.out.println(point);
    }
    m.put(i, lP);
  }

  static class Entry {
    Point b;
    Point w;

    public Entry(Point w, Point b) {
      this.w = w;
      this.b = b;
    }
  }


  static class Point {
    int x;
    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Point other = (Point) obj;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Point [x=" + x + ", y=" + y + "]";
    }
  }
}
