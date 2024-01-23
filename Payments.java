package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payments {
    public static void main(int c, int cn) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Payment App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JPanel spanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.BLUE);
                    g.fillRect(0, 0, getWidth(), getHeight());

                }
            };
            spanel.setVisible(true);
            spanel.setLayout(new BorderLayout());


            JLabel titleLabel = new JLabel("<html><h1><b><font size='9'>Payment Details</font><b></h1></html>");
            titleLabel.setForeground(Color.BLUE);
            titleLabel.setLayout(new BorderLayout());
            JLabel label = new JLabel("Label 1:");


            JButton payButton = new JButton("<html><b><font color='white' size='10'>Pay</font></b></html>");


            frame.setLayout(new BorderLayout());
            titleLabel.setHorizontalAlignment(JLabel.CENTER);


            frame.add(titleLabel, BorderLayout.NORTH);

            JPanel in = new JPanel(new GridLayout(3, 2, 10, 40));

            JLabel label1 = new JLabel("<html><h3><font color='Brown' size='4'>Number of Tickets : </font></h3></html>");
            JTextField textField1 = new JTextField();
            textField1.setText(String.valueOf(c));
            textField1.setEditable(false);
            textField1.setPreferredSize(new Dimension(4, 4));
            label1.setHorizontalAlignment(JLabel.CENTER);
            textField1.setHorizontalAlignment(JTextField.CENTER);

            JLabel label2 = new JLabel("<html><h3><font color='Brown' size='4'>Amount:</font></h3></html>");
            JTextField textField2 = new JTextField();
            textField2.setText(String.valueOf(cn));
            textField2.setEditable(false);
            textField2.setPreferredSize(new Dimension(4, 4));
            label2.setHorizontalAlignment(JLabel.CENTER);
            textField2.setHorizontalAlignment(JTextField.CENTER);

            in.add(label1);
            in.add(textField1);
            in.add(label2);
            in.add(textField2);

            frame.add(in, BorderLayout.CENTER);



            frame.add(payButton, BorderLayout.SOUTH);
            payButton.setBackground(new Color(45, 200, 110));
            payButton.setForeground(Color.lightGray);
            payButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            payButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame paymentConfirmationFrame = new JFrame("Payment QR CODE");
                    paymentConfirmationFrame.setSize(1000, 1000);
                    paymentConfirmationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    ImageIcon j = new ImageIcon("C:\\Users\\Akash\\Desktop\\n.jpeg");
                    JLabel jl = new JLabel(j);
                    paymentConfirmationFrame.add(jl, BorderLayout.WEST);
                    JLabel confirmationLabel = new JLabel("<html><u><i><h3>PLEASE SCAN THE QR CODE</h3></i></u><html>");
                    paymentConfirmationFrame.add(confirmationLabel);
                    paymentConfirmationFrame.setVisible(true);
                    confirmationLabel.setForeground(Color.BLUE);
                    JLabel qq = new JLabel("<html><font color='violet' size='3'><h4>After Scanning Payment Will be Successful and you will Receive your Tickets</h4></font><html>");
                    paymentConfirmationFrame.add(qq);
                    JButton backtohome = new JButton("<html><font color='green' size='10'>Go Back To Home</font></html>");
                    backtohome.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            paymentConfirmationFrame.dispose();
                        }
                    });
                    paymentConfirmationFrame.add(backtohome, BorderLayout.SOUTH);
                }
            });
            frame.setVisible(true);

        });
    }
}
