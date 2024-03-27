package org.carl.hard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

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
    List<Task> tasks = new ArrayList<Task>();
    int total = 0;
    for (String c : arr) {
      Task t = new Task();
      t.days = Integer.parseInt(c);
      total += Integer.parseInt(c);
      tasks.add(t);
    }
  }

  static class Staff {
    Task t;
    boolean free = true;
  }

  static class Task {
    int days;
    boolean finish;

    void sub(int i) {
      this.days -= i;
    }

    @Override
    public String toString() {
      return "Task [days=" + days + ", finish=" + finish + "]";
    }
  }
}
