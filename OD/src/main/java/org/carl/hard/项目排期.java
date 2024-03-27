package org.carl.hard;

import java.util.*;

public class 项目排期 {
  static Scanner in = new Scanner(System.in);

  // 6 2 7 7 9 3 2 1 3 11 4
  // [1, 2, 2, 3, 3, 4, 6, 7, 7, 9, 11]
  // 11 + 10 + 7
  // 2 + 3 +
  public static void main(String[] args) {
    String s = in.nextLine();
    // in.nextLine();
    int num = in.nextInt();
    String[] arr = s.split(" ");
    List<Task> _tasks = new ArrayList<>();

    for (String c : arr) {
      int i = Integer.parseInt(c);
      Task t = new Task();
      t.days = i;
      _tasks.add(t);
    }
    _tasks.sort((Comparator.comparingInt(o -> o.days)));
    Deque<Task> tasks = new ArrayDeque<>(_tasks);
    int result = 0;
    List<Staff> sList = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      if (i == 0)
        sList.add(new Staff(true));
      else
        sList.add(new Staff());
    }
    Map<Staff, Integer> count = new HashMap<>();
    while (!tasks.isEmpty()) {
      for (Staff staff : sList) {
        if (staff.free && !tasks.isEmpty()) {
          staff.t = staff.flag ? tasks.removeLast() : tasks.removeFirst();
          staff.free = false;
        } else if (!staff.free) {
          staff.t.sub();
          put(count, staff);
        }
      }
      for (Staff staff : sList) {
        if (staff.t.days == 0)
          staff.free = true;
      }
    }
    for (Staff staff : sList) {
      if (!staff.free) {
        put(count, staff, staff.t.days);
      }
    }
    System.out.println("111");
  }

  static void put(Map<Staff, Integer> m, Staff s) {
    Integer integer = m.get(s);
    if (integer == null) {
      m.put(s, 1);
      return;
    }
    m.put(s, ++integer);
  }

  static void put(Map<Staff, Integer> m, Staff s, Integer i) {
    Integer integer = m.get(s);
    if (integer == null) {
      m.put(s, 1);
      return;
    }
    m.put(s, integer + i);
  }

  static class Staff {
    Task t;
    boolean free = true;

    Staff(boolean flag) {
      this.flag = flag;
    }

    Staff() {

    }

    boolean flag = false;

    @Override
    public String toString() {
      return "Staff [t=" + t + ", free=" + free + "]";
    }

  }


  static class Task {
    int days;
    boolean finish;

    void sub() {
      this.days -= 1;
    }

    @Override
    public String toString() {
      return "Task [days=" + days + ", finish=" + finish + "]";
    }
  }
}
