package org.carl.eazy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class 万能字符单词拼写 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int N = in.nextInt();
    Map<Integer, Map<Character, Integer>> map = new HashMap<>();
    for (int i = 0; i < N; i++) {
      String str = in.next();
      map.put(i, parse(str));
    }
    String chars = in.next();
    Map<Character, Integer> m = parse(chars);
    int r = 0;
    for (int i = 0; i < N; i++) {

      Map<Character, Integer> temp = new HashMap<>(m);
      Map<Character, Integer> map1 = map.get(i);
      boolean flag = true;
      for (Entry<Character, Integer> key : map1.entrySet()) {
        Integer integer = temp.get(key.getKey());
        if (integer == null) {
          Integer integer2 = temp.get('?');
          if (integer2 == null){
            flag = false;
            break;
          }

          if (integer2 >= key.getValue()) {
            temp.put('?', integer2 - key.getValue());
          } else {
            flag = false;
            break;
          }
        } else {
          if (integer < key.getValue()) {
            int _i = key.getValue() - integer;
            Integer integer2 = temp.get('?');
            if (integer2 == null) {
              flag = false;
              break;
            }
            if (integer2 >= _i) {
              temp.put('?', integer2 - _i);
            } else {
              flag = false;
              break;
            }
          }
        }

      }
      if (flag) {
        System.out.println(i);
        r++;
      }
    }
    System.out.println(r);

  }

  static Map<Character, Integer> parse(String str) {
    Map<Character, Integer> map = new HashMap<>();
    char[] charArray = str.toCharArray();

    for (int i = 0; i < charArray.length; i++) {
      Integer integer = map.get(charArray[i]);
      if (integer == null)
        map.put(charArray[i], 1);
      else
        map.put(charArray[i], integer + 1);
    }

    return map;
  }
}
