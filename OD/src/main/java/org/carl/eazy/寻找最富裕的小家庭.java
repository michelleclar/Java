package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class 寻找最富裕的小家庭 {
  static Scanner in = new Scanner(System.in);

  public static void main(final String[] args) {
    final int N = in.nextInt();

    final Map<Integer, Family> m = new HashMap<>();
    for (int i = 0; i < N; i++) {
      final int num = in.nextInt();
      final People people = new People(i + 1, num);
      final Family family = new Family();
      family.root = people;
      family.list = new ArrayList<>();
      m.put(i + 1, family);
    }

    for (int i = 0; i < N - 1; i++) {
      final int node = in.nextInt();
      final int child = in.nextInt();
      final Family f = m.get(node);
      final Family f1 = m.get(child);
      f.list.add(f1.root);
    }
    int result = 0;
    for (Entry<Integer, Family> e : m.entrySet()) {
      int total = e.getValue().calc();
      result = result > total ? result : total;
    }
    System.out.println(result);
  }

  static class People {
    int index;
    int rich;

    public People(final int index, final int rich) {
      this.index = index;
      this.rich = rich;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + index;
      result = prime * result + rich;
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      final People other = (People) obj;
      if (index != other.index)
        return false;
      if (rich != other.rich)
        return false;
      return true;
    }
  }

  static class Family {
    People root;
    List<People> list;
    int total;

    public int calc() {
      int total = this.root.rich;
      for (final People p : this.list) {
        total += p.rich;
      }
      return total;
    }
  }
}
