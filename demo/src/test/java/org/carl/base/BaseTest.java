package org.carl.base;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BaseTest {
  @Test
  public void testForEach() {
    List<String> list = null;
    for (String s : list) {
      System.out.println(s);
    }
  }
}
