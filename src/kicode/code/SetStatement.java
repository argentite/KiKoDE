package kicode.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public class SetStatement extends Statement {

    public Variable lhs;
    public NumericExpression rhs;

    final Color color = new Color(255, 102, 0);
    Dimension dimension;

    public SetStatement(Variable variable, NumericExpression value) {
        lhs = variable;
        rhs = value;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        //panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        JLabel label = new JLabel("Set");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);
        
        lhs.addComponent(panel);
        
        label = new JLabel("to");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);
        
        rhs.addComponent(panel);

        JButton addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        panel.add(addButton);

        JButton removeButton = new JButton("x");
        removeButton.setBorder(new EmptyBorder(0, 4, 0, 4));
        removeButton.setBackground(Color.RED);
        /*removeButton.addActionListener(new ActionListener() {
            void actionPerformed() {}
        });*/
        panel.add(removeButton);

        parent.add(panel);
    }

    @Override
    public void run(VirtualMachine vm) {
        vm.setVariable(lhs.name, rhs.evaluate(vm));
    }
}
