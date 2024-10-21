import org.carl.EhcacheTool;
import org.junit.jupiter.api.Test;

public class TestEhcacheTool {

  @Test
  public void testGet(){
    System.out.println(EhcacheTool.get("key"));
  }
  
}
