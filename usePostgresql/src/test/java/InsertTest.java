import org.carl.crud.Insert;
import org.carl.generated.tables.pojos.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class InsertTest {

    @Test
    public void testInsert() {
        User user = new User(1, "sdad", LocalDateTime.now(), LocalDateTime.now());
        Insert.insertUser(user);
    }
}
