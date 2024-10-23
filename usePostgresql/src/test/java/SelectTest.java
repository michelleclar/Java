import org.carl.crud.Insert;
import org.carl.crud.Select;
import org.carl.generated.tables.pojos.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SelectTest {
    @BeforeAll
    public static void before() {
        User user = new User(1, "sdad", LocalDateTime.now(), LocalDateTime.now());
        Insert.insertUser(user);
    }
    @Test
    public void testSelect() {
        System.out.println(Select.findById(1));
    }
}
