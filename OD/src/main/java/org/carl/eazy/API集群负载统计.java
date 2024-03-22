package org.carl.eazy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class API集群负载统计 {
  static Scanner in = new Scanner(System.in);

  // /huawei/computing/no/one
  // /huawei/computing
  // /huawei
  // /huawei/cloud/no/one
  // /huawei/wireless/no/one
  // 2 computing
  public static void main(String[] args) {
    // N
    int N = in.nextInt();
    Map<Integer, Map<String, Integer>> urls = new HashMap<>();
    in.nextLine();
    for (int i = 0; i < N; i++) {
      String str = in.nextLine();
      String[] split = str.split("/");
      add(split, urls);
    }
    int L = in.nextInt();
    String s = in.next();
    Map<String, Integer> m = urls.get(L);
    System.out.println(m.get(s));
  }

  static void add(String[] strs, Map<Integer, Map<String, Integer>> urls) {
    for (int i = 0; i < strs.length; i++) {

      String s = strs[i];
      if (s.isBlank() || s.isEmpty())
        continue;
      Map<String, Integer> map = urls.get(i);
      if (map == null) {
        Map<String, Integer> _m = new HashMap<>();
        _m.put(s, 1);
        urls.put(i, _m);
        continue;
      }
      Integer _i = map.get(s);
      if (_i == null) {
        map.put(s, 1);
        continue;
      }
      map.put(s, _i + 1);
    }
  }
}
