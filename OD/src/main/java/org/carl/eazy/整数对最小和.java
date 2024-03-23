package org.carl.eazy;

import java.util.PriorityQueue;
import java.util.Scanner;

public class 整数对最小和 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int size1 = in.nextInt();
    int[] array1 = new int[size1];
    for (int i = 0; i < size1; i++) {
      array1[i] = in.nextInt();
    }
    int size2 = in.nextInt();
    int[] array2 = new int[size2];
    for (int i = 0; i < size2; i++) {
      array2[i] = in.nextInt();
    }
    int k = in.nextInt();
    System.out.println(minSum(array1, array2, k));
    in.close();
  }

  public static int minSum(int[] array1, int[] array2, int k) {
    PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] + a[1] - b[0] - b[1]);
    boolean[][] visited = new boolean[array1.length][array2.length];
    queue.offer(new int[] {array1[0], array2[0], 0, 0});
    visited[0][0] = true;
    int sum = 0;
    for (int i = 0; i < k; i++) {
      int[] cur = queue.poll();
      sum += cur[0] + cur[1];
      if (cur[2] + 1 < array1.length && !visited[cur[2] + 1][cur[3]]) {
        queue.offer(new int[] {array1[cur[2] + 1], array2[cur[3]], cur[2] + 1, cur[3]});
        visited[cur[2] + 1][cur[3]] = true;
      }
      if (cur[3] + 1 < array2.length && !visited[cur[2]][cur[3] + 1]) {
        queue.offer(new int[] {array1[cur[2]], array2[cur[3] + 1], cur[2], cur[3] + 1});
        visited[cur[2]][cur[3] + 1] = true;
      }
    }
    return sum;
  }
}
