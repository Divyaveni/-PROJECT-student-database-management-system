import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditStudents extends JFrame {

    private final Connection con = new ConnectDB().connection();

    EditStudents() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            int count = 0;

            if (con != null) {
                try {
                    Statement statement = con.createStatement();
                    String sql = "SELECT name, regNo FROM data_table";
                    ResultSet resultSet = statement.executeQuery(sql);
                    ArrayList<String> nameList = new ArrayList<>();
                    ArrayList<Integer> regNoList = new ArrayList<>();
                    while (resultSet.next()) {
                        nameList.add(resultSet.getString("name"));
                        regNoList.add(resultSet.getInt("regNo"));
                        count++;
                    }

                    add(new JLabel("Name"));
                    add(new JLabel("Reg No"));
                    add(new JLabel("Edit"));
                    add(new JLabel("Delete"));

                    JLabel[] name = new JLabel[count];
                    JLabel[] regNo = new JLabel[count];
                    JButton[] edit = new JButton[count];
                    JButton[] delete = new JButton[count];
                    for (int i = 0; i < count; i++) {
                        name[i] = new JLabel();
                        regNo[i] = new JLabel();
                        edit[i] = new JButton("Edit");
                        delete[i] = new JButton("Delete");
                        name[i].setText(nameList.get(i));
                        regNo[i].setText(String.valueOf(regNoList.get(i)));

                        int j = i;
                        delete[i].addActionListener(e -> {
                            try {
                                String deleteSql = "DELETE FROM data_table WHERE regNo=" + regNoList.get(j);
                                statement.execute(deleteSql);
                                deleteSql = "DELETE FROM grades_table WHERE regNo=" + regNoList.get(j);
                                statement.execute(deleteSql);
                                dispose();
                                new EditStudents();
                            } catch (SQLException throwable) {
                                throwable.printStackTrace();
                            }
                        });

                        edit[i].addActionListener(e -> {
                            String editSql = "SELECT * FROM data_table WHERE regNo=" + regNoList.get(j);
                            try {
                                ResultSet result = statement.executeQuery(editSql);
                                while (result.next()) {
                                    new AddEditStudentDetails(result.getString("name"), result.getInt("regNo"), result.getInt("rollNo"), result.getInt("semester"), result.getString("email"), result.getString("grades"));
                                }
                            } catch (SQLException throwable) {
                                throwable.printStackTrace();
                            }
                        });

                        add(name[i]);
                        add(regNo[i]);
                        add(edit[i]);
                        add(delete[i]);
                    }
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(700, 500);
            setTitle("SDMS");
            setLayout(new GridLayout(count + 1, 4));
            setIconImage(new ImageIcon("assets/logo.png").getImage());
            setVisible(true);
        });
    }
}