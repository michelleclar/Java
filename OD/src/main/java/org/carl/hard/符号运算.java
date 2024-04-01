package org.carl.hard;

import java.util.Scanner;
import java.util.Stack;

public class 符号运算 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    String line = in.nextLine();
    line = line.replace(" ", "");
    Stack<Fraction> s1 = new Stack<>();
    Stack<Character> s2 = new Stack<>();
    char[] charArray = line.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];
      if (c == '(') {
      }

      if (c == '*') {
        if (!sb.isEmpty()) {
          int before = Integer.parseInt(sb.toString());
          sb.delete(0, sb.length());
          s1.add(new Fraction(before * before, before));
        }
        // i++;
        // c = charArray[i];
        while (++i < charArray.length && Character.isDigit(charArray[i])) {
          sb.append(charArray[i]);
        }
        i--;
        Fraction pop = s1.pop();
        int after = Integer.parseInt(sb.toString());

        sb.delete(0, sb.length());
        Fraction f = new Fraction(after * after, after);
        s1.push(pop.mult(f));
        continue;
      }
      if (c == '/') {
        if (!sb.isEmpty()) {
          int before = Integer.parseInt(sb.toString());
          sb.delete(0, sb.length());
          s1.add(new Fraction(before * before, before));
        }
        while (++i < charArray.length && Character.isDigit(charArray[i])) {
          sb.append(charArray[i]);
        }
        i--;
        Fraction pop = s1.pop();
        int after = Integer.parseInt(sb.toString());

        sb.delete(0, sb.length());
        Fraction f = new Fraction(after * after, after);
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
      Fraction pop = s1.pop();
      Character pop2 = s2.pop();
      Fraction pop3 = s1.pop();
      Fraction f;
      if (pop2.equals('-')) {
        f = pop3.sub(pop);
      } else {
        f = pop3.add(pop);
      }
      s1.push(f);
    }
    System.out.println(s1.pop());
  }

  static class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
      this.numerator = numerator;
      this.denominator = denominator;
    }

    // 返回分数的浮点数表示
    public double toDouble() {
      return (double) numerator / denominator;
    }

    // 返回分数的字符串表示
    public String toString() {
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

    // 约分
    private void reduce() {
      int gcd = gcd(numerator, denominator);
      numerator /= gcd;
      denominator /= gcd;
    }

    void simplify() {
      int lcm = Fraction.lcm(this.denominator, this.numerator);
      this.numerator /= lcm;

      this.denominator /= lcm;
    }

    // 通分
    public Fraction toCommonDenominator(Fraction other) {
      int commonDenominator = lcm(denominator, other.denominator);
      int newNumerator = numerator * (commonDenominator / denominator);
      int otherNumerator = other.numerator * (commonDenominator / other.denominator);
      return new Fraction(newNumerator, commonDenominator);
    }
  }
}
