import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowAllStudentsGrades extends JFrame {
    private final Connection con = new ConnectDB().connection();

    ShowAllStudentsGrades() {
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                String sql = "SELECT * FROM grades_table";
                ResultSet resultSet = statement.executeQuery(sql);

                String[][] data = new String[60][];
                int i = 0;
                while (resultSet.next()) {
                    String[] sub = {resultSet.getString("name").trim(), String.valueOf(resultSet.getInt("regNo")), resultSet.getString("MA-101").trim(), resultSet.getString("PH-101").trim(), resultSet.getString("EC-101").trim(),
                            resultSet.getString("CS-101").trim(), resultSet.getString("HU-101").trim(), resultSet.getString("HU-102").trim(),
                            resultSet.getString("CS-111").trim(), resultSet.getString("EC-111").trim(), resultSet.getString("CS-201").trim(),
                            resultSet.getString("EC-201").trim(), resultSet.getString("CS-202").trim(), resultSet.getString("CS-203").trim(),
                            resultSet.getString("HU-201").trim(), resultSet.getString("EC-211").trim(), resultSet.getString("CS-211").trim(),
                            resultSet.getString("CS-212").trim(), resultSet.getString("MA-301").trim(), resultSet.getString("CS-301").trim(),
                            resultSet.getString("CS-302").trim(), resultSet.getString("CS-303").trim(), resultSet.getString("HU-301").trim(),
                            resultSet.getString("CS-311").trim(), resultSet.getString("CS-312").trim(), resultSet.getString("CS-313").trim(),
                            resultSet.getString("CS-401").trim(), resultSet.getString("CS-402").trim(), resultSet.getString("CS-403").trim(),
                            resultSet.getString("EC-401").trim(), resultSet.getString("EC-402").trim(), resultSet.getString("CS-411").trim(),
                            resultSet.getString("CS-412").trim(), resultSet.getString("CS-413").trim(), resultSet.getString("CS-501").trim(),
                            resultSet.getString("CS-502").trim(), resultSet.getString("EC-501").trim(), resultSet.getString("Elective-1").trim(),
                            resultSet.getString("HU-501").trim(), resultSet.getString("CS-511").trim(), resultSet.getString("EC-511").trim(),
                            resultSet.getString("CS-591").trim(), resultSet.getString("CS-601").trim(), resultSet.getString("CS-602").trim(),
                            resultSet.getString("CS-603").trim(), resultSet.getString("CS-604").trim(), resultSet.getString("Elective-2").trim(),
                            resultSet.getString("CS-611").trim(), resultSet.getString("CS-612").trim(), resultSet.getString("HU-611").trim(),
                            resultSet.getString("CS-691").trim(), resultSet.getString("CS-701").trim(), resultSet.getString("CS-702").trim(),
                            resultSet.getString("Elective-3").trim(), resultSet.getString("Elective-4").trim(), resultSet.getString("CS-711").trim(),
                            resultSet.getString("CS-791").trim(), resultSet.getString("Elective-5").trim(), resultSet.getString("Elective-6").trim(),
                            resultSet.getString("Elective-7").trim(), resultSet.getString("CS-891").trim(), resultSet.getString("CS-892").trim()};
                    data[i] = sub;
                    i++;
                }
                String[] column = {"Name", "Reg No", "MA-101", "PH-101", "EC-101", "CS-101", "HU-101", "HU-102", "CS-111", "EC-111", "CS-201", "EC-201", "CS-202", "CS-203", "HU-201", "EC-211", "CS-211", "CS-212", "MA-301", "CS-301", "CS-302", "CS-303", "HU-301", "CS-311", "CS-312", "CS-313", "CS-401", "CS-402", "CS-403", "EC-401", "EC-402", "CS-411", "CS-412", "CS-413", "CS-501", "CS-502", "EC-501", "Elective-1", "HU-501", "CS-511", "EC-511", "CS-591", "CS-601", "CS-602", "CS-603", "CS-604", "Elective-2", "CS-611", "CS-612", "HU-611", "CS-691", "CS-701", "CS-702", "Elective-3", "Elective-4", "CS-711", "CS-791", "Elective-5", "Elective-6", "Elective-7", "CS-891", "CS-892"};
                JTable grades = new JTable();

                grades.setModel(new DefaultTableModel(data, column));

                for (int x = 0; x < 61; x++) {
                    grades.getColumnModel().getColumn(x).setMinWidth(50);
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
// regNo, "MA-101", "PH-101", "EC-101", "CS-101", "HU-101", "HU-102", "CS-111", "EC-111", "CS-201", "EC-201", "CS-202", "CS-203", "HU-201", "EC-211", "CS-211", "CS-212", "MA-301", "CS-301", "CS-302", "CS-303", "HU-301", "CS-311", "CS-312", "CS-313", "CS-401", "CS-402", "CS-403", "EC-401", "EC-402", "CS-411", "CS-412", "CS-413", "CS-501", "CS-502", "EC-501", "Elective-1", "HU-501", "CS-511", "EC-511", "CS-591", "CS-601", "CS-602", "CS-603", "CS-604", "Elective-2", "CS-611", "CS-612", "HU-611", "CS-691", "CS-701", "CS-702", "Elective-3", "Elective-4", "CS-711", "CS-791", "Elective-5", "Elective-6" ,"Elective-7" ,"CS-891", "CS-892"
