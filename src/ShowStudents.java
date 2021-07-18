import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowStudents extends JFrame {
    private final Connection con = new ConnectDB().connection();

    ShowStudents() {
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                String sql = "SELECT * FROM data_table";
                ResultSet resultSet = statement.executeQuery(sql);

                String[][] data = new String[60][];
                int i = 0;
                while (resultSet.next()) {
                    String[] sub = {resultSet.getString("name").trim(), String.valueOf(resultSet.getInt("regNo")), resultSet.getString("rollNo").trim(), resultSet.getString("semester").trim(), resultSet.getString("email").trim()};
                    data[i] = sub;
                    i++;
                }
                String[] column = {"Name", "Reg No", "Roll No", "Semester", "email"};
                JTable grades = new JTable();

                grades.setModel(new DefaultTableModel(data, column));

                for (int x = 0; x < 5; x++) {
                    grades.getColumnModel().getColumn(x).setMinWidth(133);
                }

                JScrollPane scrollPane = new JScrollPane(grades, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                grades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                add(scrollPane);
                con.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setTitle("SDMS");
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
    }
}
