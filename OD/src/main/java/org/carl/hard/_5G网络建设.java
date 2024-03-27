package org.carl.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class _5G网络建设 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int N = in.nextInt();
    int M = in.nextInt();
    Map<Integer, Base> map = new HashMap<>();
    for (int i = 0; i < M; i++) {
      int X = in.nextInt();
      int Y = in.nextInt();
      int Z = in.nextInt();
      int P = in.nextInt();
      Base b = get(map, X);
      Base b2 = get(map, Y);
      Pair<Integer, Base> pair = new Pair<>();
      pair.k = Z;
      pair.v = b2;
      b.l.add(pair);
      if (P == 1) b.con.add(b2);
    }
  }

  static Base get(Map<Integer, Base> map, int i) {
    Base b = map.get(i);
    return b == null ? new Base(i) : b;
  }

  static class Base {
    Integer i;
    List<Pair<Integer, Base>> l = new ArrayList<Pair<Integer, Base>>();
    List<Base> con;

    Base() {}

    Base(int i) {
      this.i = i;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((i == null) ? 0 : i.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Base other = (Base) obj;
      if (i == null) {
        if (other.i != null) return false;
      } else if (!i.equals(other.i)) return false;
      return true;
    }
  }

  static class Pair<K, V> {
    K k;
    V v;

    public K getK() {
      return k;
    }

    public V getV() {
      return v;
    }
  }
}
