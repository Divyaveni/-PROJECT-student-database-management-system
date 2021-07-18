import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginScreen extends JFrame {
    // MySql DB connection
    private final Connection con = new ConnectDB().connection();

    // Views
    private JTextField username;
    private JTextField name;
    private JPasswordField password;
    private JComboBox accountType;
    private JButton loginButton;
    private JButton createAccountButton;
    private JPanel loginPanel;
    private JLabel icon;
    private JLabel errorField;
    private JLabel nameLabel;
    private JLabel orLabel;
    private JButton goBackButton;
    private JTextField regNo;
    private JLabel regNoLabel;

    LoginScreen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Login Button On Click Listener
        loginButton.addActionListener(e -> {
            // If login form is valid try logging in user
            if (isFormFilled()) {
                loginUser(username.getText(), password.getText());
            }
        });

        // Create Account Button On Click Listener
        createAccountButton.addActionListener(e -> {
            // if in registration page
            if (name.isVisible()) {
                // create user
                if (isFormFilled()) {
                    createUser(name.getText(), username.getText(), password.getText(), accountType.getSelectedIndex());
                }
            } else {
                // else set views to display registration page
                errorField.setText("");
                name.setVisible(true);
                nameLabel.setVisible(true);
                regNo.setVisible(true);
                regNoLabel.setVisible(true);
                goBackButton.setVisible(true);
                accountType.setVisible(true);
                loginButton.setVisible(false);
                orLabel.setVisible(false);
            }
        });

        // Go Back Button On Click Listener
        goBackButton.addActionListener(e -> {
            // set views to display login page
            errorField.setText("");
            name.setVisible(false);
            nameLabel.setVisible(false);
            goBackButton.setVisible(false);
            regNo.setVisible(false);
            regNoLabel.setVisible(false);
            accountType.setVisible(false);
            loginButton.setVisible(true);
            orLabel.setVisible(true);
        });

        // Account Type Selector
        accountType.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Object item = e.getItem();
                // If select type is Lecturer hide Reg No input view else show it
                if (item.toString().equals("Lecturer")) {
                    regNo.setVisible(false);
                    regNoLabel.setVisible(false);
                } else {
                    regNo.setVisible(true);
                    regNoLabel.setVisible(true);
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

        // Display entire page
        setContentPane(loginPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setTitle("SDMS");
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
    }

    private void createUser(String name, String username, String password, int selectedIndex) {
        if (con != null) {
            try {
                boolean isStudent, isAdmin;
                int regNo;
                if (selectedIndex == 1) {
                    isStudent = false;
                    isAdmin = true;
                    regNo = 0;
                } else {
                    isStudent = true;
                    isAdmin = false;
                    regNo = Integer.parseInt(this.regNo.getText());
                }
                Statement statement = con.createStatement();
                String sql = "INSERT INTO login_table(name, username, password, regNo, isStudent, isAdmin) VALUES (" + "'" + name + "'," + "'" + username + "'," + "'" + password + "'," + regNo + "," + isStudent + "," + isAdmin + ")";
                if (statement.executeUpdate(sql) == 1) {
                    con.close();
                    dispose();
                    setUserLoggedIn(username);
                    new MainScreen();
                }
            } catch (SQLException throwable) {
                errorField.setText(throwable.getMessage());
            }
        }
    }

    private void setUserLoggedIn(String username) {
        try {
            File file = new File("user.txt");
            FileWriter userDetails = new FileWriter(file);
            userDetails.write(username);
            userDetails.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginUser(String username, String password) {
        if (con != null) {
            try {
                Statement statement = con.createStatement();
                String sql = "SELECT name, username, password, regNo, isAdmin, isStudent FROM login_table WHERE username=" + "'" + username + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    if (resultSet.getString("password").equals(password)) {
                        setUserLoggedIn(resultSet.getString("username"));
                        new MainScreen();
                        con.close();
                        dispose();
                        break;
                    } else {
                        errorField.setText("Enter a Valid Password");
                    }
                }
            } catch (SQLException throwable) {
                errorField.setText(throwable.getMessage());
                throwable.printStackTrace();
            }
        }
    }

    private boolean isFormFilled() {
        // Form Validation
        if (username.getText().isEmpty()) {
            errorField.setText("Enter Valid User Name");
            return false;
        } else if (password.getPassword().length < 6) {
            errorField.setText("Enter Valid Password");
            return false;
        } else if (name.isVisible() && name.getText().isEmpty()) {
            errorField.setText("Enter Your Name");
            return false;
        } else {
            errorField.setText("");
            return true;
        }
    }
}
