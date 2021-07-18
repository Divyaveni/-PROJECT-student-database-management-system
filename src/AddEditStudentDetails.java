import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class AddEditStudentDetails extends JFrame {
    // MySql DB connection
    private final Connection con = new ConnectDB().connection();
    private final List<String> validGrades = Arrays.asList("ex", "a", "b", "c", "d", "p", "f", "fr", "");
    // Views
    private JTextField nameField;
    private JTextField regNoField;
    private JTextField rollNoField;
    private JTextField semField;
    private JTextField emailField;
    private JPanel addEditPanel;
    private JTextField subject1Field;
    private JTextField subject2Field;
    private JTextField subject3Field;
    private JTextField subject4Field;
    private JTextField subject5Field;
    private JTextField subject6Field;
    private JTextField subject7Field;
    private JTextField subject8Field;
    private JTextField subject9Field;
    private JComboBox semSelection;
    private JLabel subject1Label;
    private JLabel subject2Label;
    private JLabel subject3Label;
    private JLabel subject4Label;
    private JLabel subject5Label;
    private JLabel subject6Label;
    private JLabel subject7Label;
    private JLabel subject8Label;
    private JLabel subject9Label;
    private JButton saveGradesButton;
    private JButton saveButton;
    private JLabel errorLabel;
    private JLabel successLabel;
    // Each Semester Grades
    private String sem1Grades = " , , , , , , , , ";
    private String sem2Grades = " , , , , , , , , ";
    private String sem3Grades = " , , , , , , , , ";
    private String sem4Grades = " , , , , , , , , ";
    private String sem5Grades = " , , , , , , , , ";
    private String sem6Grades = " , , , , , , , , ";
    private String sem7Grades = " , , , , , , , , ";
    private String sem8Grades = " , , , , , , , , ";
    private String allGrades = "";

    AddEditStudentDetails() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        semSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selected = semSelection.getSelectedIndex();
                setLabels(selected);
            }
        });

        saveGradesButton.addActionListener(e -> {
            if (isValidGrades()) {
                if (semSelection.getSelectedIndex() == 0) {
                    sem1Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 1) {
                    sem2Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 2) {
                    sem3Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 3) {
                    sem4Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 4) {
                    sem5Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 5) {
                    sem6Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 6) {
                    sem7Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 7) {
                    sem8Grades = getSemGrades();
                }
                setSuccessLabel("Grades Saved Successfully");
            } else {
                setErrorLabel("Enter Valid Grades");
            }
        });

        saveButton.addActionListener(e -> {
            if (isFormFilled()) {
                allGrades = getGrades();
                String[][] grades = new String[8][9];
                String[] grades_y = allGrades.split(";");
                for (int y = 0; y < 8; y++) {
                    String[] grades_x = grades_y[y].split(",");
                    grades[y] = grades_x;
                }
                String name = nameField.getText();
                int regNo = Integer.parseInt(regNoField.getText());
                int rollNo = Integer.parseInt(rollNoField.getText());
                int sem = Integer.parseInt(semField.getText());
                String email = emailField.getText();
                int year = sem / 2;
                if (con != null) {
                    try {
                        Statement statement = con.createStatement();
                        String sql = "INSERT INTO data_table(name, regNo, rollNo, year, semester, email, grades) VALUES (" + "'" + name + "'," + regNo + "," + rollNo + "," + year + "," + sem + "," + "'" + email + "'," + "'" + allGrades + "'" + ")";
                        if (statement.executeUpdate(sql) == 1) {
                            setSuccessLabel("Saved Successfully");
                            sql = "INSERT INTO grades_table(name, regNo, `MA-101`, `PH-101`, `EC-101`, `CS-101`, `HU-101`, `HU-102`, `CS-111`, `EC-111`, `CS-201`, `EC-201`, `CS-202`, `CS-203`, `HU-201`, `EC-211`, `CS-211`, `CS-212`, `MA-301`, `CS-301`, `CS-302`, `CS-303`, `HU-301`, `CS-311`, `CS-312`, `CS-313`, `CS-401`, `CS-402`, `CS-403`, `EC-401`, `EC-402`, `CS-411`, `CS-412`, `CS-413`, `CS-501`, `CS-502`, `EC-501`, `Elective-1`, `HU-501`, `CS-511`, `EC-511`, `CS-591`, `CS-601`, `CS-602`, `CS-603`, `CS-604`, `Elective-2`, `CS-611`, `CS-612`, `HU-611`, `CS-691`, `CS-701`, `CS-702`, `Elective-3`, `Elective-4`, `CS-711`, `CS-791`, `Elective-5`, `Elective-6` ,`Elective-7` ,`CS-891`, `CS-892`) VALUES(" + "'" + name + "'," + regNo + ",'" + grades[0][0] + "', '" + grades[0][1] + "', '" + grades[0][2] + "', '" + grades[0][3] + "', '" + grades[0][4] + "', '" + grades[0][5] + "', '" + grades[0][6] + "', '" + grades[0][7] + "', '" + grades[1][0] + "', '" + grades[1][1] + "', '" + grades[1][2] + "', '" + grades[1][3] + "', '" + grades[1][4] + "', '" + grades[1][5] + "', '" + grades[1][6] + "', '" + grades[1][7] + "', '" + grades[2][0] + "', '" + grades[2][1] + "', '" + grades[2][2] + "', '" + grades[2][3] + "', '" + grades[2][4] + "', '" + grades[2][5] + "', '" + grades[2][6] + "', '" + grades[2][7] + "', '" + grades[3][0] + "', '" + grades[3][1] + "', '" + grades[3][2] + "', '" + grades[3][3] + "', '" + grades[3][4] + "', '" + grades[3][5] + "', '" + grades[3][6] + "', '" + grades[3][7] + "', '" + grades[4][0] + "', '" + grades[4][1] + "', '" + grades[4][2] + "', '" + grades[4][3] + "', '" + grades[4][4] + "', '" + grades[4][5] + "', '" + grades[4][6] + "', '" + grades[4][7] + "', '" + grades[5][0] + "', '" + grades[5][1] + "', '" + grades[5][2] + "', '" + grades[5][3] + "', '" + grades[5][4] + "', '" + grades[5][5] + "', '" + grades[5][6] + "', '" + grades[5][7] + "', '" + grades[5][8] + "', '" + grades[6][0] + "', '" + grades[6][1] + "', '" + grades[6][2] + "', '" + grades[6][3] + "', '" + grades[6][4] + "', '" + grades[6][5] + "', '" + grades[7][0] + "', '" + grades[7][1] + "', '" + grades[7][2] + "', '" + grades[7][3] + "', '" + grades[7][4] + "'" + ")";
                            if (statement.executeUpdate(sql) == 1) {
                                setSuccessLabel("Saved Successfully");
                            } else {
                                setErrorLabel("Failed to update db");
                            }
                        } else {
                            setErrorLabel("Failed to update db");
                        }
                    } catch (SQLException throwable) {
                        setErrorLabel(throwable.getMessage());
                        throwable.printStackTrace();
                    }
                } else {
                    setErrorLabel("Unable to connect to database");
                }
            } else {
                setErrorLabel("Enter Valid Details");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    con.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        setContentPane(addEditPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setTitle("SDMS");
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
    }

    public AddEditStudentDetails(String name, int regNo, int rollNo, int semester, String email, String gradesEdit) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameField.setText(name);
        regNoField.setText(String.valueOf(regNo));
        rollNoField.setText(String.valueOf(rollNo));
        semField.setText(String.valueOf(semester));
        emailField.setText(email);

        regNoField.setEditable(false);

        String[][] grades = new String[8][9];
        String[] grades__y = gradesEdit.split(";");
        for (int y = 0; y < 8; y++) {
            String[] grades_x = grades__y[y].split(",");
            grades[y] = grades_x;
        }

        subject1Field.setText(grades[0][0].trim());
        subject2Field.setText(grades[0][1].trim());
        subject3Field.setText(grades[0][2].trim());
        subject4Field.setText(grades[0][3].trim());
        subject5Field.setText(grades[0][4].trim());
        subject6Field.setText(grades[0][5].trim());
        subject7Field.setText(grades[0][6].trim());
        subject8Field.setText(grades[0][7].trim());

        semSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selected = semSelection.getSelectedIndex();
                setLabels(selected);
                subject1Field.setText(grades[selected][0].trim());
                subject2Field.setText(grades[selected][1].trim());
                subject3Field.setText(grades[selected][2].trim());
                subject4Field.setText(grades[selected][3].trim());
                subject5Field.setText(grades[selected][4].trim());
                subject6Field.setText(grades[selected][5].trim());
                subject7Field.setText(grades[selected][6].trim());
                subject8Field.setText(grades[selected][7].trim());
                subject9Field.setText(grades[selected][8].trim());
            }
        });

        saveGradesButton.addActionListener(e -> {
            if (isValidGrades()) {
                if (semSelection.getSelectedIndex() == 0) {
                    sem1Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 1) {
                    sem2Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 2) {
                    sem3Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 3) {
                    sem4Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 4) {
                    sem5Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 5) {
                    sem6Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 6) {
                    sem7Grades = getSemGrades();
                } else if (semSelection.getSelectedIndex() == 7) {
                    sem8Grades = getSemGrades();
                }
                setSuccessLabel("Grades Saved Successfully");
            } else {
                setErrorLabel("Enter Valid Grades");
            }
        });

        saveButton.addActionListener(e -> {
            if (isFormFilled()) {
                allGrades = getGrades();
                String[] grades_y = allGrades.split(";");
                for (int y = 0; y < 8; y++) {
                    String[] grades_x = grades_y[y].split(",");
                    grades[y] = grades_x;
                }
                String nameEdit = nameField.getText();
                int regNoEdit = Integer.parseInt(regNoField.getText());
                int rollNoEdit = Integer.parseInt(rollNoField.getText());
                int semEdit = Integer.parseInt(semField.getText());
                String emailEdit = emailField.getText();
                int year = semEdit / 2;
                if (con != null) {
                    try {
                        Statement statement = con.createStatement();
                        String deleteSql = "DELETE FROM data_table WHERE regNo=" + regNo;
                        statement.execute(deleteSql);
                        String sql = "INSERT INTO data_table(name, regNo, rollNo, year, semester, email, grades) VALUES (" + "'" + nameEdit + "'," + regNoEdit + "," + rollNoEdit + "," + year + "," + semEdit + "," + "'" + emailEdit + "'," + "'" + allGrades + "'" + ")";
                        if (statement.executeUpdate(sql) == 1) {
                            setSuccessLabel("Saved Successfully");
                            deleteSql = "DELETE FROM grades_table WHERE regNo=" + regNo;
                            statement.execute(deleteSql);
                            sql = "INSERT INTO grades_table(name, regNo, `MA-101`, `PH-101`, `EC-101`, `CS-101`, `HU-101`, `HU-102`, `CS-111`, `EC-111`, `CS-201`, `EC-201`, `CS-202`, `CS-203`, `HU-201`, `EC-211`, `CS-211`, `CS-212`, `MA-301`, `CS-301`, `CS-302`, `CS-303`, `HU-301`, `CS-311`, `CS-312`, `CS-313`, `CS-401`, `CS-402`, `CS-403`, `EC-401`, `EC-402`, `CS-411`, `CS-412`, `CS-413`, `CS-501`, `CS-502`, `EC-501`, `Elective-1`, `HU-501`, `CS-511`, `EC-511`, `CS-591`, `CS-601`, `CS-602`, `CS-603`, `CS-604`, `Elective-2`, `CS-611`, `CS-612`, `HU-611`, `CS-691`, `CS-701`, `CS-702`, `Elective-3`, `Elective-4`, `CS-711`, `CS-791`, `Elective-5`, `Elective-6` ,`Elective-7` ,`CS-891`, `CS-892`) VALUES(" + "'" + nameEdit + "'," + regNoEdit + ",'" + grades[0][0] + "', '" + grades[0][1] + "', '" + grades[0][2] + "', '" + grades[0][3] + "', '" + grades[0][4] + "', '" + grades[0][5] + "', '" + grades[0][6] + "', '" + grades[0][7] + "', '" + grades[1][0] + "', '" + grades[1][1] + "', '" + grades[1][2] + "', '" + grades[1][3] + "', '" + grades[1][4] + "', '" + grades[1][5] + "', '" + grades[1][6] + "', '" + grades[1][7] + "', '" + grades[2][0] + "', '" + grades[2][1] + "', '" + grades[2][2] + "', '" + grades[2][3] + "', '" + grades[2][4] + "', '" + grades[2][5] + "', '" + grades[2][6] + "', '" + grades[2][7] + "', '" + grades[3][0] + "', '" + grades[3][1] + "', '" + grades[3][2] + "', '" + grades[3][3] + "', '" + grades[3][4] + "', '" + grades[3][5] + "', '" + grades[3][6] + "', '" + grades[3][7] + "', '" + grades[4][0] + "', '" + grades[4][1] + "', '" + grades[4][2] + "', '" + grades[4][3] + "', '" + grades[4][4] + "', '" + grades[4][5] + "', '" + grades[4][6] + "', '" + grades[4][7] + "', '" + grades[5][0] + "', '" + grades[5][1] + "', '" + grades[5][2] + "', '" + grades[5][3] + "', '" + grades[5][4] + "', '" + grades[5][5] + "', '" + grades[5][6] + "', '" + grades[5][7] + "', '" + grades[5][8] + "', '" + grades[6][0] + "', '" + grades[6][1] + "', '" + grades[6][2] + "', '" + grades[6][3] + "', '" + grades[6][4] + "', '" + grades[6][5] + "', '" + grades[7][0] + "', '" + grades[7][1] + "', '" + grades[7][2] + "', '" + grades[7][3] + "', '" + grades[7][4] + "'" + ")";
                            if (statement.executeUpdate(sql) == 1) {
                                setSuccessLabel("Saved Successfully");
                            } else {
                                setErrorLabel("Failed to update db");
                            }
                        } else {
                            setErrorLabel("Failed to update db");
                        }
                    } catch (SQLException throwable) {
                        setErrorLabel(throwable.getMessage());
                        throwable.printStackTrace();
                    }
                } else {
                    setErrorLabel("Unable to connect to database");
                }
            } else {
                setErrorLabel("Enter Valid Details");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    con.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        setContentPane(addEditPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setTitle("SDMS");
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
    }

    private boolean isFormFilled() {
        if (nameField.getText().isEmpty()) {
            setErrorLabel("Enter Valid Name");
            return false;
        } else if (regNoField.getText().isEmpty()) {
            setErrorLabel("Enter Valid Reg No");
            return false;
        } else if (rollNoField.getText().isEmpty()) {
            setErrorLabel("Enter Valid Roll No");
            return false;
        } else if (semField.getText().isEmpty()) {
            setErrorLabel("Enter Valid Semester");
            return false;
        } else if (emailField.getText().isEmpty()) {
            setErrorLabel("Enter Valid Email");
            return false;
        } else {
            return true;
        }
    }

    private void setErrorLabel(String message) {
        successLabel.setVisible(false);
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }

    private void setSuccessLabel(String message) {
        errorLabel.setVisible(false);
        successLabel.setVisible(true);
        successLabel.setText(message);
    }

    private String getSemGrades() {
        return subject1Field.getText() + " ," + subject2Field.getText() + " ," + subject3Field.getText() + " ," + subject4Field.getText() + " ," + subject5Field.getText() + " ," + subject6Field.getText() + " ," + subject7Field.getText() + " ," + subject8Field.getText() + " ," + subject9Field.getText() + " ";
    }

    private String getGrades() {
        return sem1Grades + ";" + sem2Grades + ";" + sem3Grades + ";" + sem4Grades + ";" + sem5Grades + ";" + sem6Grades + ";" + sem7Grades + ";" + sem8Grades;
    }

    private boolean isValidGrades() {
        return validGrades.contains(subject1Field.getText().trim().toLowerCase()) && validGrades.contains(subject2Field.getText().trim().toLowerCase()) && validGrades.contains(subject3Field.getText().trim().toLowerCase()) && validGrades.contains(subject4Field.getText().trim().toLowerCase()) && validGrades.contains(subject5Field.getText().trim().toLowerCase()) && validGrades.contains(subject6Field.getText().trim().toLowerCase()) && validGrades.contains(subject7Field.getText().trim().toLowerCase()) && validGrades.contains(subject8Field.getText().trim().toLowerCase()) && validGrades.contains(subject9Field.getText().trim().toLowerCase());
    }

    private void setLabels(int selected) {
        subject1Label.setVisible(true);
        subject2Label.setVisible(true);
        subject3Label.setVisible(true);
        subject4Label.setVisible(true);
        subject5Label.setVisible(true);
        subject6Label.setVisible(true);
        subject7Label.setVisible(true);
        subject8Label.setVisible(true);
        subject9Label.setVisible(true);
        subject1Field.setVisible(true);
        subject2Field.setVisible(true);
        subject3Field.setVisible(true);
        subject4Field.setVisible(true);
        subject5Field.setVisible(true);
        subject6Field.setVisible(true);
        subject7Field.setVisible(true);
        subject8Field.setVisible(true);
        subject9Field.setVisible(true);
        if (selected == 0) {
            subject1Label.setText("MA 101");
            subject2Label.setText("PH 101");
            subject3Label.setText("EC 101");
            subject4Label.setText("CS 101");
            subject5Label.setText("HU 101");
            subject6Label.setText("HU 102");
            subject7Label.setText("CS 111");
            subject8Label.setText("EC 111");
            subject9Label.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 1) {
            subject1Label.setText("CS 201");
            subject2Label.setText("EC 201");
            subject3Label.setText("CS 202");
            subject4Label.setText("CS 203");
            subject5Label.setText("HU 201");
            subject6Label.setText("EC 211");
            subject7Label.setText("CS 211");
            subject8Label.setText("CS 212");
            subject9Label.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 2) {
            subject1Label.setText("MA 301");
            subject2Label.setText("CS 301");
            subject3Label.setText("CS 302");
            subject4Label.setText("CS 303");
            subject5Label.setText("HU 301");
            subject6Label.setText("CS 311");
            subject7Label.setText("CS 312");
            subject8Label.setText("CS 313");
            subject9Label.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 3) {
            subject1Label.setText("CS 401");
            subject2Label.setText("CS 402");
            subject3Label.setText("CS 403");
            subject4Label.setText("EC 401");
            subject5Label.setText("EC 402");
            subject6Label.setText("CS 411");
            subject7Label.setText("CS 412");
            subject8Label.setText("CS 413");
            subject9Label.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 4) {
            subject1Label.setText("CS 501");
            subject2Label.setText("CS 502");
            subject3Label.setText("EC 501");
            subject4Label.setText("Elective 1");
            subject5Label.setText("HU 501");
            subject6Label.setText("CS 511");
            subject7Label.setText("EC 511");
            subject8Label.setText("CS 591");
            subject9Label.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 5) {
            subject1Label.setText("CS 601");
            subject2Label.setText("CS 602");
            subject3Label.setText("CS 603");
            subject4Label.setText("CS 604");
            subject5Label.setText("Elective 2");
            subject6Label.setText("CS 611");
            subject7Label.setText("CS 612");
            subject8Label.setText("CS 613");
            subject9Label.setText("CS 614");
            subject9Field.setVisible(true);
        } else if (selected == 6) {
            subject1Label.setText("CS 701");
            subject2Label.setText("CS 702");
            subject3Label.setText("Elective 3");
            subject4Label.setText("Elective 4");
            subject5Label.setText("CS 711");
            subject6Label.setText("CS 791");
            subject7Label.setVisible(false);
            subject8Label.setVisible(false);
            subject9Label.setVisible(false);
            subject7Field.setVisible(false);
            subject8Field.setVisible(false);
            subject9Field.setVisible(false);
        } else if (selected == 7) {
            subject1Label.setText("Elective 5");
            subject2Label.setText("Elective 6");
            subject3Label.setText("Elective 7");
            subject4Label.setText("CS 891");
            subject5Label.setText("CS 892");
            subject6Label.setVisible(false);
            subject7Label.setVisible(false);
            subject8Label.setVisible(false);
            subject9Label.setVisible(false);
            subject6Field.setVisible(false);
            subject7Field.setVisible(false);
            subject8Field.setVisible(false);
            subject9Field.setVisible(false);
        }
    }
}
