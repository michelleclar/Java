package org.carl.hard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class 二叉树计算 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    // 中序遍历
    String s1 = in.nextLine();
    // 前序遍历
    String s2 = in.nextLine();
    List<Integer> l1 = parse(s1);
    List<Integer> l2 = parse(s2);
    Deque<Integer> deque = new ArrayDeque<>(l2);
    if (l1.get(0) == l2.get(0)) {
      // 没有左子树
    }
    Tree root = new Tree(l2.get(0));
    root.l = new Tree(l1.get(1));
    // 0-index-1 index+1 - l1.size()
    int index = l1.indexOf(root.node);



  }

  static List<Integer> parse(String str) {
    List<Integer> l = new ArrayList<>();
    String[] split = str.split(" ");
    for (String s : split) {
      l.add(Integer.parseInt(s));
    }
    return l;


  }

  static class Tree {
    int node;
    Tree l;
    Tree r;

    public Tree(int node) {
      this.node = node;
    }

  }
}
