package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSignupSwingApp extends JFrame {
    private JButton loginButton;
    private JButton signupButton;
    private JButton backButton;

    public LoginSignupSwingApp() {
        super("Login and Signup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());


        JPanel buttonPanel = new JPanel() {
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
                g2d.fillRoundRect(0, 0, width, height, 50, 50);
            }
        };

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219)); // Blue color
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        buttonPanel.add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(46, 204, 113));
        signupButton.setForeground(Color.WHITE);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });
        buttonPanel.add(signupButton);
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
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.CENTER);

        JLabel headingLabel = new JLabel("Login or Signup to continue");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(headingLabel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {

        SwingUtilities.invokeLater(() -> new LoginSwingApp());
    }

    private void signup() {

        SwingUtilities.invokeLater(() -> new SignupDetailsSwingApp());
    }
    private void goBack() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignupSwingApp());
    }
}
