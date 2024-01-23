package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Open extends JFrame {
    private JButton getStartedButton;
    private JButton exitButton;

    public Open() {
        super(" App Name");
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
                g2d.fillRect(0, 0, width, height);
            }
        };

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        getStartedButton = new JButton("Get Started");
        getStartedButton.setBackground(new Color(46, 204, 113));
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getStartedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() ->  new DisplayPageSwingApp());
            }
        });
        buttonPanel.add(getStartedButton);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApp();
            }
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        JLabel headingLabel = new JLabel("Welcome to Your App Name");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headingLabel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openLoginSignupSwingApp() {

        SwingUtilities.invokeLater(() ->  new LoginSignupSwingApp());
        dispose();
    }

    private void exitApp() {

        System.out.println("Exiting the app...");
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Open());
    }
}
