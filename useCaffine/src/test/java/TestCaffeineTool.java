import org.carl.CaffeineTool;
import org.junit.jupiter.api.Test;

public class TestCaffeineTool {
    @Test
    public void testGet() {
        System.out.println(CaffeineTool.get("key"));
    }
}
