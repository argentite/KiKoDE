package kicode.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import kicode.VirtualMachine;

public class CommandStatement extends Statement {

    public String text;

    final Color color = new Color(255, 102, 0);
    Dimension dimension;

    public CommandStatement() {
        text = "";
    }

    public CommandStatement(String description) {
        this.text = description;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        //panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);

        JButton addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        panel.add(addButton);

        JButton removeButton = new JButton("x");
        removeButton.setBorder(new EmptyBorder(0, 4, 0, 4));
        removeButton.setBackground(Color.RED);
        removeButton.addActionListener((e) -> {
            parent.remove(panel);
        });
        panel.add(removeButton);

        parent.add(panel);
    }

    @Override
    public void run(VirtualMachine vm) {
        vm.output += text + '\n';
    }
}
