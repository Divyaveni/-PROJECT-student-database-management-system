import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (new File("user.txt").exists()) {
            new MainScreen();
        } else {
            new LoginScreen();
        }
    }
}
