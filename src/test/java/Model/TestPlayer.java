package test.java.Model;
import main.java.Model.Player;
import org.junit.Test;

public class TestPlayer {

    private static final int TIMEOUT = 200;
    private Player player;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullUsernameSmallConstructor() {
        player = new Player(null, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullUsernameLargeConstructor() {
        player = new Player(null, 0, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBlankUsernameSmallConstructor() {
        player = new Player("", 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBlankUsernameLargeConstructor() {
        player = new Player("", 0, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullSetUsername() {
        player = new Player("Test", 0, 0);
        player.setUsername(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBlankSetUsername() {
        player = new Player("Test", 0, 0);
        player.setUsername("");
    }
}