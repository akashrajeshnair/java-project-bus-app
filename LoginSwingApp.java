package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginSwingApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton; // Added back button

    public LoginSwingApp() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(52, 152, 219); // Blue
                Color color2 =  new Color(46, 204, 113); // Green
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        loginPanel.setOpaque(false);

        loginPanel.setLayout(new GridLayout(3, 100, 100, 150));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(46, 204, 113)); // Green color
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        backButton = new JButton("Back");
        backButton.setBackground(new Color(46, 204, 113)); // Green color (you can adjust the color)
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate login using JDBC
        boolean isValid = validateLogin(username, password);

        if (isValid) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose(); // close the login popup
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Try again.");
        }
    }

    private boolean validateLogin(String username, String password) {
        // JDBC connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/project";
        String dbUsername = "root";
        String dbPassword = "admin";

        // SQL query
        String sqlQuery = "Select u_mail, u_pass from sys_user where u_mail=? and u_pass=?";

        try (
                Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void goBack() {
        SwingUtilities.invokeLater(() -> {
            new LoginSignupSwingApp();
            dispose(); // Dispose the current LoginSwingApp window
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSwingApp());
    }
}