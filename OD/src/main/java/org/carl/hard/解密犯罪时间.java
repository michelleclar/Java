package org.carl.hard;

import java.util.Scanner;

public class 解密犯罪时间 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String line = in.nextLine();
    Time time = pase(line);
    int min = min(time.H, time.h, time.M, time.m);
    Time r;



    // 判断能否在同一天
    // 判断能否在同一分钟
    // 从右到左开始尝试
    if (time.m < 6) {
      int temp = Integer.MAX_VALUE;
      temp = time.h - time.m > 0 ? Math.min(time.h - time.m, temp) : temp;
      temp = time.H - time.m > 0 ? Math.min(time.H - time.m, temp) : temp;
      temp = time.M - time.m > 0 ? Math.min(time.M - time.m, temp) : temp;
      if (temp != Integer.MAX_VALUE) {
        r = new Time(time.H, time.h, time.M, time.m + temp);
        System.out.println(r);
        return;
      }
    }
    if (time.M < 4) {
      int temp = Integer.MAX_VALUE;
      temp = time.h - time.M > 0 ? Math.min(time.h - time.M, temp) : temp;
      temp = time.H - time.M > 0 ? Math.min(time.H - time.M, temp) : temp;
      if (time.m <= 5)
        temp = time.m - time.M > 0 ? Math.min(time.m - time.M, temp) : temp;
      if (temp != Integer.MAX_VALUE) {
        r = new Time(time.H, time.h, time.M + temp, min);
        System.out.println(r);
        return;
      }
    }
    if (time.h < 3) {
      int temp = Integer.MAX_VALUE;
      temp = time.M - time.h > 0 ? Math.min(time.M - time.h, temp) : temp;
      temp = time.H - time.h > 0 ? Math.min(time.H - time.h, temp) : temp;
      if (time.m <= 5)
        temp = time.m - time.h > 0 ? Math.min(time.m - time.h, temp) : temp;
      if (temp != Integer.MAX_VALUE) {
        r = new Time(time.H, time.h + temp, min, min);
        System.out.println(r);
        return;
      }
    }
    if (time.H < 2) {
      int temp = Integer.MAX_VALUE;
      temp = time.M - time.H > 0 ? Math.min(time.M - time.H, temp) : temp;
      temp = time.h - time.H > 0 ? Math.min(time.h - time.H, temp) : temp;
      if (time.m <= 5)
        temp = time.m - time.H > 0 ? Math.min(time.m - time.H, temp) : temp;
      if (temp != Integer.MAX_VALUE) {
        r = new Time(time.H + temp, min, min, min);
        System.out.println(r);
        return;
      }
    }
    r = new Time(min, min, min, min);
    System.out.println(r);



  }

  static int min(int... n) {
    int r = Integer.MAX_VALUE;
    for (int i = 0; i < n.length; i++)
      r = Math.min(n[i], r);
    return r;

  }


  static Time pase(String line) {
    char[] charArray = line.toCharArray();
    int[] arr = new int[4];
    // 0 2
    int H = Character.getNumericValue(charArray[0]);
    arr[0] = H;
    // 0 4
    int h = Character.getNumericValue(charArray[1]);
    arr[1] = h;

    // 0 5
    int M = Character.getNumericValue(charArray[3]);
    arr[2] = M;
    // 0 9
    int m = Character.getNumericValue(charArray[4]);
    arr[3] = m;
    return new Time(H, h, M, m);
  }

  static boolean isTime(String str) {
    char[] charArray = str.toCharArray();
    // 0 2
    int H = Character.getNumericValue(charArray[0]);
    if (H > 2 || H < 0)
      return false;
    // 0 4
    int h = Character.getNumericValue(charArray[1]);
    if (h > 4 || h < 0)
      return false;

    // 0 5
    int M = Character.getNumericValue(charArray[3]);
    if (M > 5 || M < 0)
      return false;
    // 0 9
    int m = Character.getNumericValue(charArray[4]);
    if (m > 9 || m < 0)
      return false;

    return true;
  }

  static class Time {
    int H;
    int h;
    int M;
    int m;

    public Time(int h, int h2, int m, int m2) {
      this.H = h;
      this.h = h2;
      this.M = m;
      this.m = m2;
    }

    public Time() {
    }

    @Override
    public String toString() {
      return this.H + "" + this.h + ":" + this.M + "" + this.m;

    }

  }
}
