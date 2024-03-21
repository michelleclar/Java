package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class 围棋的气 {
  // 0 5 8 9 9 10
  // 5 0 9 9 9 8
  // 0 - 18
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      // 0,0 0,18 18,18 18,0
      Map<Integer, List<Point>> m = new HashMap<>();
      if (in.hasNextLine()) {
        List<Point> lP = new ArrayList<>();
        while (in.hasNext()) {
          Point point = new Point();
          point.x = in.nextInt();
          point.y = in.nextInt();
          lP.add(point);
          System.out.println(point);
        }
        m.put(0, lP);
      }
      if (in.hasNextLine()) {
        List<Point> lP = new ArrayList<>();
        while (in.hasNext()) {
          Point point = new Point();
          point.x = in.nextInt();
          point.y = in.nextInt();
          lP.add(point);
          System.out.println(point);
        }
        m.put(0, lP);
      }
    }
  }

  static void put(Map<Integer, List<Point>> m, int i) {
    if (in.hasNextLine()) {
      List<Point> lP = new ArrayList<>();
      while (in.hasNext()) {
        Point point = new Point();
        point.x = in.nextInt();
        point.y = in.nextInt();
        lP.add(point);
        System.out.println(point);
      }
      m.put(0, lP);
    }
  }

  static class Point {
    int x;
    int y;

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
