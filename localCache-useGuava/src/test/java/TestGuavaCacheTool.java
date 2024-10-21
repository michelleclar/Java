import org.carl.GuavaCacheTool;
import org.junit.jupiter.api.Test;

public class TestGuavaCacheTool {
    @Test
    public void testGet() {
        System.out.println(GuavaCacheTool.get("key"));
    }
}
