package org.carl.hard;

import java.util.*;

public class 二叉树的广度优先遍历 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String after = in.next();
    String mid = in.next();
    Tree root = calc(after, mid);
    Queue<Tree> deque = new LinkedList<>();
    deque.offer(root);
    while (!deque.isEmpty()) {
      Tree pop = deque.poll();
      System.out.print(pop.val);
      if (pop.r != null)
        deque.offer(pop.r);
      if (pop.l != null)
        deque.offer(pop.l);
    }
    System.out.println();

  }

  static Tree calc(String after, String mid) {
    if (after.isEmpty())
      return null;
    if (mid.length() == 1) {
      return new Tree(mid);
    }
    char c = after.charAt(after.length() - 1);
    Tree root = new Tree(String.valueOf(c));
    int i = mid.indexOf(String.valueOf(c));

    int lStart = after.length() - 1 - (mid.length() - 1 - i);
    int lEnd = after.length() - 1;
    int rStart = 0;
    int rEnd = i;
    String lafter = after.substring(lStart, lEnd);
    String lmid = mid.substring(i + 1);

    String rafter = after.substring(rStart, rEnd);
    String rmid = mid.substring(0, i);
    root.l = calc(rafter, rmid);
    root.r = calc(lafter, lmid);

    return root;

  }


  static class Tree {
    String val;
    Tree r;
    Tree l;

    public Tree(String val) {
      this.val = val;
    }

    @Override
    public String toString() {
      return "Tree [val=" + val + "]";
    }


  }


}
