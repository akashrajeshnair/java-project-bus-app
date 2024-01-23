package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayImageAndOpen extends JFrame {
    public DisplayImageAndOpen() {
        super("Display Image and Open Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create a JLabel as the content pane
        JLabel contentPane = new JLabel();
        setContentPane(contentPane);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Akash\\Desktop\\background.jpeg");
        Image scaledImage = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the background image on the JLabel
        contentPane.setIcon(scaledIcon);

        // Adding MouseAdapter to the contentPane
        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openProgram();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openProgram() {
        SwingUtilities.invokeLater(() -> new Open());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisplayImageAndOpen());
    }
}
