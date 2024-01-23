package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;
import java.util.Random;

public class SignupDetailsSwingApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneNumField;
    private JButton signupButton;
    private JButton backButton;

    public SignupDetailsSwingApp() {
        super("Signup Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        JPanel signupPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(52, 152, 219);
                Color color2 = new Color(46, 204, 113);
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        signupPanel.setOpaque(false);

        signupPanel.setLayout(new GridLayout(7, 2, 5, 5));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Email:");
        usernameLabel.setForeground(Color.WHITE);
        signupPanel.add(usernameLabel);

        usernameField = new JTextField();
        signupPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        signupPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        signupPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        signupPanel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        signupPanel.add(confirmPasswordField);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);
        signupPanel.add(firstNameLabel);

        firstNameField = new JTextField();
        signupPanel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);
        signupPanel.add(lastNameLabel);

        lastNameField = new JTextField();
        signupPanel.add(lastNameField);

        JLabel phoneNumLabel = new JLabel("Phone Number:");
        phoneNumLabel.setForeground(Color.WHITE);
        signupPanel.add(phoneNumLabel);

        phoneNumField = new JTextField();
        signupPanel.add(phoneNumField);


        signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(46, 204, 113)); // Green color
        signupButton.setForeground(Color.WHITE);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        signupPanel.add(signupButton);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(52, 152, 219)); // Blue color
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        signupPanel.add(backButton);

        add(signupPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void goBack() {
        SwingUtilities.invokeLater(() -> {
            new LoginSignupSwingApp();
            dispose(); // Dispose the current SignupDetailsSwingApp window
        });
    }

    private void performRegistration(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String phoneNum = phoneNumField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        boolean isValid = validateRegistration(username, password, confirmPassword, phoneNum, firstName, lastName);

        if (isValid){
            JOptionPane.showMessageDialog(this, "Registration successful");
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"Invalid Details");
        }
    }

    boolean validateRegistration(String username, String password, String confirmPassword, String phoneNum, String firstName, String lastName){
        if(!Objects.equals(password, confirmPassword)){
            JOptionPane.showMessageDialog(this, "passwords do not match");
            return false;
        }
        else{
            String jdbcUrl = "jdbc:mysql://localhost:3306/project";
            String dbUsername = "root";
            String dbPassword = "admin";

            Random r = new Random();
            int userID = r.nextInt(10000);
            boolean isUserIDValid = !(validateUserID(userID));

            if (isUserIDValid) {
                String sqlQuery = "insert into sys_user(user_id, first_name, last_name, mobile, u_mail, u_pass) values (?,?,?,?,?,?)";

                try (
                        Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ) {
                    preparedStatement.setInt(1,userID);
                    preparedStatement.setString(2, firstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, phoneNum);
                    preparedStatement.setString(5, username);
                    preparedStatement.setString(6, password);

                    preparedStatement.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else
                return false;
        }
    }

    boolean validateUserID(int i){
        String jdbcUrl = "jdbc:mysql://localhost:3306/project";
        String dbUsername = "root";
        String dbPassword = "admin";
        String sqlQuery = "Select user_id from sys_user where user_id = ?";
        try(
                Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)
        ){
            preparedStatement.setInt(1,i);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();;
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignupDetailsSwingApp());
    }
}