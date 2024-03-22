package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class 整数对最小和 {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // 双指针
        int n = in.nextInt();
        List<Integer> l1 = new ArrayList<Integer>();

        List<Integer> l2 = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            int num = in.nextInt();
            l1.add(num);
        }
        n = in.nextInt();
        for (int j = 0; j < n; j++) {
            int num = in.nextInt();
            l2.add(num);
        }
        int k = in.nextInt();
        int dp = l1.get(0) + l2.get(0);

        int length = l1.size() > l2.size() ? l1.size() : l2.size();
        int r = 0, l = 0;
        k = k - 1;
        Set<Point> s = new HashSet<Point>();
        Point p = new Point(0, 0);
        p.total = dp;
        s.add(p);
        while ((r < l1.size() || l < l2.size()) && k > 0) {
            int temp = dp;
            Point p1 = null;
            Point p2 = null;
            if (r + 1 < l1.size()) {
                p1 = new Point(r + 1, l);
                p.total = l1.get(r + 1) + l2.get(l);
            }
            if (l + 1 < l2.size()) {
                p2 = new Point(r, l + 1);
                p.total = l1.get(r) + l2.get(l + 1);
            }
            if (!(p1 == null) && p2 == null) {
                s.add(p1);
                k = k - 1;
                r += 1;
                dp += p1.total;
            }
            if (!(p2 == null) && p1 == null) {
                s.add(p2);
                k -= 1;
                l += 1;
                dp += p2.total;
            }
            if (p1.total > p2.total) {
                l += 1;
                s.add(p2);
                dp += p2.total;
                k -= 1;
                continue;
            }
            if (p1.total < p2.total) {
                r += 1;
                s.add(p1);
                dp += p1.total;
                k -= 1;
                continue;
            }
            if(l1.get(r + 1) + l2.get(l) > l1.get(r) + l2.get(l + 1)){
                l += 1;
                s.add(p2);
                dp += p2.total;
                k -= 1;
                continue;
            }
            k -= 1;
            r += 1;
            s.add(p1);
            dp += p1.total;
        }
        System.out.println(dp);
    }

    static class Point {
        int x;
        int y;
        int total;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Point other = (Point) obj;
            if (x != other.x) return false;
            if (y != other.y) return false;
            return true;
        }
    }
}
