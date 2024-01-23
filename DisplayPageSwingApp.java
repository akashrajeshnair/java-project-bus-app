package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DisplayPageSwingApp extends JFrame {

    public DisplayPageSwingApp() {
        super("Display Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setBackground(new Color(52, 152, 219)); // Set background color

        JPanel displayPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton bookTicketsButton = new JButton("Book Tickets \uD83C\uDFAB"); // 🎫 Symbol for ticket
        bookTicketsButton.setBackground(new Color(46, 204, 113)); // Green color
        bookTicketsButton.setForeground(Color.WHITE);
        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTickets();
            }
        });
        displayPanel.add(bookTicketsButton);

        JButton contactUsButton = new JButton("Signup \uD83D\uDC65");
        contactUsButton.setBackground(new Color(46, 204, 113)); // Green color
        contactUsButton.setForeground(Color.WHITE);
        contactUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        displayPanel.add(contactUsButton);

        JButton listStationsButton = new JButton("List of Stations \uD83D\uDDFA"); // 🚺 Symbol for station
        listStationsButton.setBackground(new Color(46, 204, 113)); // Green color
        listStationsButton.setForeground(Color.WHITE);
        listStationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listStations();
            }
        });
        displayPanel.add(listStationsButton);

        add(displayPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void bookTickets() {
        // Link to ChooseStationsSwingApp
        SwingUtilities.invokeLater(() -> new ChooseStationsSwingApp());
        // Close the current window
        dispose();
    }

    private void signUp() {
        // Implement the logic for contacting
        SwingUtilities.invokeLater(() -> new LoginSignupSwingApp());
    }

    private void listStations() {
        String[] stationList = new String[50];
        int i = 0;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "admin");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select sta_name from stations");
            while (rs.next()) {
                String value = rs.getString("sta_name");
                stationList[i] = value;
                i++;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        StringBuilder stationText = new StringBuilder("Available Stations:\n");
        for (String station : stationList) {
            stationText.append(station).append("\n");
        }
        JOptionPane.showMessageDialog(this, stationText.toString(), "List of Stations", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisplayPageSwingApp());
    }
}