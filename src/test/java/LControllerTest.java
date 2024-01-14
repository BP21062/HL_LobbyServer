import com.example.hl_lobbyserver.LController;
import com.example.hl_lobbyserver.LDatabaseConnector;
import com.example.hl_lobbyserver.Message;
import com.example.hl_lobbyserver.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LControllerTest {
    ArrayList<User> userList;

    @BeforeEach
    void setUp() {
        userList = new ArrayList<User>();
        userList.add(new User("test", "test"));
    }

    @Test
    void testcheckDuplicateUserID(){
        // ユーザIDが重複している場合
        assertEquals(true, LController.checkDuplicateUserID("test", userList));
        // ユーザIDが重複していない場合
        assertEquals(false, LController.checkDuplicateUserID("test2", userList));
    }

    // 殆どの処理が通信後即返却なのでテストができませんでした
}
