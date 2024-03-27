package view;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JLabel label;

    public MainFrame(String title) {
        super(title);

        label = new JLabel();
        updateLabelText("");

        JPanel panel = new JPanel();
        panel.add(label);

        this.add(panel);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void updateLabelText(String text) {
        label.setText(text);
    }
}
