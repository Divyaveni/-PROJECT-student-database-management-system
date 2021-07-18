import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainScreen extends JFrame {
    // MySql DB connection
    private final Connection con = new ConnectDB().connection();

    private JPanel mainPanel;
    private JButton logOutButton;
    private JButton addStudentsButton;
    private JButton editStudentsButton;
    private JLabel icon;
    private JLabel logLabel;
    private JButton showAllStudentsGradesButton;
    private JButton publishGradesButton;
    private JButton showYourAccountDetailsButton;
    private JButton showYourGradesButton;
    private JButton studentsListButton;

    MainScreen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder username = new StringBuilder();
        try {
            FileReader fileReader = new FileReader("user.txt");
            int i;
            while ((i = fileReader.read()) != -1) {
                username.append((char) i);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addStudentsButton.setMinimumSize(new Dimension(200, 10));
        editStudentsButton.setMinimumSize(new Dimension(200, 10));
        showAllStudentsGradesButton.setMinimumSize(new Dimension(200, 10));

        if (con != null) {
            try {
                Statement statement = con.createStatement();
                String sql = "SELECT * FROM login_table WHERE username=" + "'" + username.toString() + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    if (!resultSet.getBoolean("isAdmin")) {
                        addStudentsButton.setVisible(false);
                        editStudentsButton.setVisible(false);
                        publishGradesButton.setVisible(false);
                        showAllStudentsGradesButton.setVisible(false);
                    } else if (!resultSet.getBoolean("isStudent")) {
                        showYourGradesButton.setVisible(false);
                    }
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

        // Log Out Button onclick listener
        logOutButton.addActionListener(e -> {
            // Get User Credential file
            File userDetails = new File("user.txt");
            // if File Exits delete it and logout else just logout
            try {
                Files.deleteIfExists(Paths.get(userDetails.getAbsolutePath()));
                dispose();
                new LoginScreen();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // Add Students Button onclick listener
        addStudentsButton.addActionListener(e -> new AddEditStudentDetails());

        editStudentsButton.addActionListener(e -> new EditStudents());

        showAllStudentsGradesButton.addActionListener(e -> new ShowAllStudentsGrades());

        studentsListButton.addActionListener(e -> new ShowStudents());

        publishGradesButton.addActionListener(e -> {
            if (con != null) {
                int input = JOptionPane.showConfirmDialog(null, "You want to Publish Result");
                if (input == 0) {
                    try {
                        Statement statement = con.createStatement();
                        String sql = "SELECT * FROM data_table";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            String[][] grades = new String[8][9];
                            String[] grades__y = resultSet.getString("grades").split(";");
                            for (int y = 0; y < 8; y++) {
                                String[] grades_x = grades__y[y].split(",");
                                grades[y] = grades_x;
                            }
                            String msg = "MA-101 : " + grades[0][0] + "\n" +
                                    "PH-101 : " + grades[0][0] + "\n" +
                                    "EC-101 : " + grades[0][1] + "\n" +
                                    "CS-101 : " + grades[0][2] + "\n" +
                                    "HU-101 : " + grades[0][3] + "\n" +
                                    "HU-102 : " + grades[0][4] + "\n" +
                                    "CS-111 : " + grades[0][5] + "\n" +
                                    "EC-111 : " + grades[0][6] + "\n" +
                                    "CS-201 : " + grades[1][7] + "\n" +
                                    "EC-201 : " + grades[1][1] + "\n" +
                                    "CS-202 : " + grades[1][2] + "\n" +
                                    "CS-203 : " + grades[1][3] + "\n" +
                                    "HU-201 : " + grades[1][4] + "\n" +
                                    "EC-211 : " + grades[1][5] + "\n" +
                                    "CS-211 : " + grades[1][6] + "\n" +
                                    "CS-212 : " + grades[1][7] + "\n" +
                                    "MA-301 : " + grades[2][0] + "\n" +
                                    "CS-301 : " + grades[2][1] + "\n" +
                                    "CS-302 : " + grades[2][2] + "\n" +
                                    "CS-303 : " + grades[2][3] + "\n" +
                                    "HU-301 : " + grades[2][4] + "\n" +
                                    "CS-311 : " + grades[2][5] + "\n" +
                                    "CS-312 : " + grades[2][6] + "\n" +
                                    "CS-313 : " + grades[2][7] + "\n" +
                                    "CS-401 : " + grades[3][0] + "\n" +
                                    "CS-402 : " + grades[3][1] + "\n" +
                                    "CS-403 : " + grades[3][2] + "\n" +
                                    "EC-401 : " + grades[3][3] + "\n" +
                                    "EC-402 : " + grades[3][4] + "\n" +
                                    "CS-411 : " + grades[3][5] + "\n" +
                                    "CS-412 : " + grades[3][6] + "\n" +
                                    "CS-413 : " + grades[3][7] + "\n" +
                                    "CS-501 : " + grades[4][0] + "\n" +
                                    "CS-502 : " + grades[4][1] + "\n" +
                                    "EC-501 : " + grades[4][2] + "\n" +
                                    "Elective-1 : " + grades[4][3] + "\n" +
                                    "HU-501 : " + grades[4][4] + "\n" +
                                    "CS-511 : " + grades[4][5] + "\n" +
                                    "EC-511 : " + grades[4][6] + "\n" +
                                    "CS-591 : " + grades[4][7] + "\n" +
                                    "CS-601 : " + grades[5][0] + "\n" +
                                    "CS-602 : " + grades[5][1] + "\n" +
                                    "CS-603 : " + grades[5][2] + "\n" +
                                    "CS-604 : " + grades[5][3] + "\n" +
                                    "Elective-2 : " + grades[5][4] + "\n" +
                                    "CS-611 : " + grades[5][5] + "\n" +
                                    "CS-612 : " + grades[5][6] + "\n" +
                                    "HU-611 : " + grades[5][7] + "\n" +
                                    "CS-691 : " + grades[5][8] + "\n" +
                                    "CS-701 : " + grades[6][0] + "\n" +
                                    "CS-702 : " + grades[6][1] + "\n" +
                                    "Elective-3 : " + grades[6][2] + "\n" +
                                    "Elective-4 : " + grades[6][3] + "\n" +
                                    "CS-711 : " + grades[6][4] + "\n" +
                                    "CS-791 : " + grades[6][5] + "\n" +
                                    "Elective-5 : " + grades[7][0] + "\n" +
                                    "Elective-6 : " + grades[7][1] + "\n" +
                                    "Elective-7 : " + grades[7][2] + "\n" +
                                    "CS-891 : " + grades[7][3] + "\n" +
                                    "CS-892 : " + grades[7][4];
                            mail(resultSet.getString("email"), msg);
                        }
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });

        showYourAccountDetailsButton.addActionListener(e -> {
            if (con != null) {
                try {
                    Statement statement = con.createStatement();
                    String sql = "SELECT * FROM login_table WHERE username=" + "'" + username.toString() + "'";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Name : " + resultSet.getString("name") + "\n" + "User Name : " + resultSet.getString("username") + "\n" + "Student : " + resultSet.getBoolean("isStudent") + "\n" + "Admin : " + resultSet.getBoolean("isAdmin") + "\n" + "Reg No : " + resultSet.getInt("regNo"), "Account Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        showYourGradesButton.addActionListener(e -> {
            if (con != null) {
                try {
                    Statement statement = con.createStatement();
                    String sql = "SELECT * FROM login_table WHERE username=" + "'" + username.toString() + "'";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        sql = "SELECT * FROM data_table WHERE regNo=" + resultSet.getInt("regNo");
                        resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            String[][] grades = new String[8][9];
                            String[] grades__y = resultSet.getString("grades").split(";");
                            for (int y = 0; y < 8; y++) {
                                String[] grades_x = grades__y[y].split(",");
                                grades[y] = grades_x;
                            }
                            String msg = "MA-101 : " + grades[0][0] + "                         " +
                                    "PH-101 : " + grades[0][0] + "\n" +
                                    "EC-101 : " + grades[0][1] + "                         " +
                                    "CS-101 : " + grades[0][2] + "\n" +
                                    "HU-101 : " + grades[0][3] + "                         " +
                                    "HU-102 : " + grades[0][4] + "\n" +
                                    "CS-111 : " + grades[0][5] + "                         " +
                                    "EC-111 : " + grades[0][6] + "\n" +
                                    "CS-201 : " + grades[1][7] + "                         " +
                                    "EC-201 : " + grades[1][1] + "\n" +
                                    "CS-202 : " + grades[1][2] + "                         " +
                                    "CS-203 : " + grades[1][3] + "\n" +
                                    "HU-201 : " + grades[1][4] + "                         " +
                                    "EC-211 : " + grades[1][5] + "\n" +
                                    "CS-211 : " + grades[1][6] + "                         " +
                                    "CS-212 : " + grades[1][7] + "\n" +
                                    "MA-301 : " + grades[2][0] + "z" +
                                    "CS-301 : " + grades[2][1] + "\n" +
                                    "CS-302 : " + grades[2][2] + "                         " +
                                    "CS-303 : " + grades[2][3] + "\n" +
                                    "HU-301 : " + grades[2][4] + "                         " +
                                    "CS-311 : " + grades[2][5] + "\n" +
                                    "CS-312 : " + grades[2][6] + "                         " +
                                    "CS-313 : " + grades[2][7] + "\n" +
                                    "CS-401 : " + grades[3][0] + "                         " +
                                    "CS-402 : " + grades[3][1] + "\n" +
                                    "CS-403 : " + grades[3][2] + "                         " +
                                    "EC-401 : " + grades[3][3] + "\n" +
                                    "EC-402 : " + grades[3][4] + "                         " +
                                    "CS-411 : " + grades[3][5] + "\n" +
                                    "CS-412 : " + grades[3][6] + "                         " +
                                    "CS-413 : " + grades[3][7] + "\n" +
                                    "CS-501 : " + grades[4][0] + "                         " +
                                    "CS-502 : " + grades[4][1] + "\n" +
                                    "EC-501 : " + grades[4][2] + "                         " +
                                    "Elective-1 : " + grades[4][3] + "\n" +
                                    "HU-501 : " + grades[4][4] + "                         " +
                                    "CS-511 : " + grades[4][5] + "\n" +
                                    "EC-511 : " + grades[4][6] + "                         " +
                                    "CS-591 : " + grades[4][7] + "\n" +
                                    "CS-601 : " + grades[5][0] + "                         " +
                                    "CS-602 : " + grades[5][1] + "\n" +
                                    "CS-603 : " + grades[5][2] + "                         " +
                                    "CS-604 : " + grades[5][3] + "\n" +
                                    "Elective-2 : " + grades[5][4] + "                         " +
                                    "CS-611 : " + grades[5][5] + "\n" +
                                    "CS-612 : " + grades[5][6] + "                         " +
                                    "HU-611 : " + grades[5][7] + "\n" +
                                    "CS-691 : " + grades[5][8] + "                         " +
                                    "CS-701 : " + grades[6][0] + "\n" +
                                    "CS-702 : " + grades[6][1] + "                         " +
                                    "Elective-3 : " + grades[6][2] + "\n" +
                                    "Elective-4 : " + grades[6][3] + "                         " +
                                    "CS-711 : " + grades[6][4] + "\n" +
                                    "CS-791 : " + grades[6][5] + "                         " +
                                    "Elective-5 : " + grades[7][0] + "\n" +
                                    "Elective-6 : " + grades[7][1] + "                         " +
                                    "Elective-7 : " + grades[7][2] + "\n" +
                                    "CS-891 : " + grades[7][3] + "                         " +
                                    "CS-892 : " + grades[7][4];
                            JOptionPane.showMessageDialog(null, msg);
                        }
                    }
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        // Set IIITK Logo
        try {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon("assets/logo.png").getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH));
            icon.setIcon(imageIcon);
            icon.setMaximumSize(new Dimension(1, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setTitle("SDMS");
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
    }

    private void mail(String to, String msg) {
        logLabel.setVisible(true);
        logLabel.setText("Sending Mail to - " + to);
        String from = "vineel.sai73@gmail.com";
        String password = "skpiovntdnmchvvg";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your Grades");
            message.setText(msg);
            Transport.send(message);
            logLabel.setText("Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
