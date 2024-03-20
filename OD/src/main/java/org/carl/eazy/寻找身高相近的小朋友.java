package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 寻找身高相近的小朋友 */
public class 寻找身高相近的小朋友 {
  public static void main(String[] args) {
//    Scanner in = new Scanner(System.in);
//    int H = in.nextInt(), N = in.nextInt();
    int H = 100, N = 10;
//    int[] nums = new int[N];
    int[] nums = new int[] {95, 96, 97, 98, 99, 101, 102, 103, 104, 105};
//    for (int i = 0; i < N; i++) {
//      nums[i] = in.nextInt();
//    }
    List<Entry> sort = sort(nums, H);
    for (Entry e : sort) {
      System.out.print(e.hight + " ");
    }

//    in.close();
  }

  private static List<Entry> sort(int[] nums, int H) {
    // TODO Auto-generated method stub
    List<Entry> r = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      Entry e = new Entry();
      e.hight = nums[i];
      e.sub = Math.abs(nums[i] - H);
      r.add(e);
    }
    r.sort(
        (o1, o2) -> {
          if (o1.sub == o2.sub) return o1.hight - o2.hight;
          return o1.sub - o2.sub;
        });

    return r;
  }

  static class Entry {
    int hight;
    int sub;

    @Override
    public String toString() {
      return "Entry{" + "hight=" + hight + ", sub=" + sub + '}';
    }
  }
}
