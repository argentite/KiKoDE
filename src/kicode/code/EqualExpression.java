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
public class EqualExpression extends BooleanExpression {
    
    public NumericExpression lhs;
    public NumericExpression rhs;

    public EqualExpression(NumericExpression lhs, NumericExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        lhs.addComponent(panel);
        JLabel label = new JLabel("=");
        label.setFont(FONT);
        label.setForeground(Color.WHITE);
        panel.add(label);
        rhs.addComponent(panel);
        
        parent.add(panel);
    }

    @Override
    Boolean evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) == rhs.evaluate(vm);
    }
}
