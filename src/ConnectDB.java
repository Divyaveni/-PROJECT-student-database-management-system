import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    public Connection connection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SDMS", "vineelsai", "vineelsai73");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
