package org.carl.hard;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Stack;

public class 符号运算 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String line = in.nextLine();
    line = line.replace(" ", "");
    Fraction f = calc(line.toCharArray());
    System.out.println(f);
  }

  static Fraction calc(char[] charArray) {
    Deque<Fraction> s1 = new ArrayDeque<>();
    Deque<Character> s2 = new ArrayDeque<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];
      if (c == '(') {
        Stack<Character> stack = new Stack<>();
        stack.push(charArray[i]);
        StringBuilder sb1 = new StringBuilder();
        while (++i < charArray.length && !stack.isEmpty()) {

          if (charArray[i] == ')') {
            if (!(stack.size() == 1)) sb1.append(')');
            stack.pop();
            continue;
          }

          if (charArray[i] == '(') {
            stack.push('(');
          }
          sb1.append(charArray[i]);
        }

        Fraction f = calc(sb1.toString().toCharArray());
        s1.push(f);
        continue;
      }
      if (c == '*') {

        Fraction f = null;
        if (!sb.isEmpty()) {
          int before = Integer.parseInt(sb.toString());
          sb.delete(0, sb.length());
          s1.add(new Fraction(before * before, before));
        }
        while (++i < charArray.length && Character.isDigit(charArray[i])) {
          sb.append(charArray[i]);
        }
        if (i < charArray.length && charArray[i] == '(') {
          Stack<Character> stack = new Stack<>();
          stack.push(charArray[i]);
          StringBuilder sb1 = new StringBuilder();
          while (++i < charArray.length && !stack.isEmpty()) {

            if (charArray[i] == ')') {
              if (!(stack.size() == 1)) sb1.append(')');
              stack.pop();
              continue;
            }

            if (charArray[i] == '(') {
              stack.push('(');
            }
            sb1.append(charArray[i]);
          }

          f = calc(sb1.toString().toCharArray());
        }
        i--;
        Fraction pop = s1.pollLast();
        if (f == null) {
          int after = Integer.parseInt(sb.toString());

          sb.delete(0, sb.length());
          f = new Fraction(after * after, after);
        }
        s1.push(pop.mult(f));
        continue;
      }
      if (c == '/') {
        Fraction f = null;
        if (!sb.isEmpty()) {
          int before = Integer.parseInt(sb.toString());
          sb.delete(0, sb.length());
          s1.add(new Fraction(before * before, before));
        }
        while (++i < charArray.length && Character.isDigit(charArray[i])) {
          sb.append(charArray[i]);
        }
        if (i < charArray.length && charArray[i] == '(') {
          Stack<Character> stack = new Stack<>();
          stack.push(charArray[i]);
          StringBuilder sb1 = new StringBuilder();
          while (++i < charArray.length && !stack.isEmpty()) {

            if (charArray[i] == ')') {
              if (!(stack.size() == 1)) sb1.append(')');
              stack.pop();
              continue;
            }

            if (charArray[i] == '(') {
              stack.push('(');
            }
            sb1.append(charArray[i]);
          }

          f = calc(sb1.toString().toCharArray());
        }
        i--;
        Fraction pop = s1.pollLast();
        if (f == null) {
          int after = Integer.parseInt(sb.toString());
          sb.delete(0, sb.length());
          f = new Fraction(after * after, after);
        }
        i--;
        if (f.denominator == 0) return null;

        s1.push(pop.div(f));

        continue;
      }
      if (c == '+' || c == '-') {
        s2.push(c);
        if (!sb.isEmpty()) {
          int int1 = Integer.parseInt(sb.toString());
          s1.push(new Fraction(int1 * int1, int1));
          sb.delete(0, sb.length());
        }

        continue;
      }

      sb.append(c);
    }
    if (!sb.isEmpty()) {
      int int1 = Integer.parseInt(sb.toString());
      s1.push(new Fraction(int1 * int1, int1));
    }
    while (!s2.isEmpty()) {
      Fraction pop = s1.removeLast();
      Character pop2 = s2.removeFirst();
      Fraction pop3 = s1.removeLast();
      Fraction f;
      if (pop2.equals('-')) {
        f = pop.sub(pop3);
      } else {
        f = pop.add(pop3);
      }
      s1.push(f);
    }
    return s1.pop();
  }

  static class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
      this.numerator = numerator;
      this.denominator = denominator;
    }

    // 返回分数的字符串表示
    public String toString() {
      this.simplify();
      if (denominator == 1) return numerator + "";

      return numerator + "/" + denominator;
    }

    // 分数相加
    public Fraction add(Fraction other) {
      int newNumerator = this.numerator * other.denominator + this.denominator * other.numerator;
      int newDenominator = this.denominator * other.denominator;
      return new Fraction(newNumerator, newDenominator);
    }

    // 分数相减
    public Fraction sub(Fraction other) {
      int newNumerator = this.numerator * other.denominator - this.denominator * other.numerator;
      int newDenominator = this.denominator * other.denominator;
      return new Fraction(newNumerator, newDenominator);
    }

    // 分数相乘
    public Fraction mult(Fraction other) {
      int newNumerator = this.numerator * other.numerator;
      int newDenominator = this.denominator * other.denominator;
      return new Fraction(newNumerator, newDenominator);
    }

    // 分数相除
    public Fraction div(Fraction other) {
      return mult(new Fraction(other.denominator, other.numerator));
    }

    // 最大公约数 (GCD)
    private static int gcd(int a, int b) {
      if (b == 0) {
        return a;
      }
      return gcd(b, a % b);
    }

    // 最小公倍数 (LCM)
    private static int lcm(int a, int b) {
      return (a * b) / gcd(a, b);
    }

    void simplify() {
      int gcd = Fraction.gcd(this.denominator, this.numerator);
      this.numerator /= gcd;

      this.denominator /= gcd;
    }
  }
}
