package org.carl.utils;

public class Utils {
 public static int[] parse(String str) {
    String[] stars = str.split(" ");
    int[] arr = new int[stars.length];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Integer.parseInt(stars[i]);
    }
    return arr;
  }
}
