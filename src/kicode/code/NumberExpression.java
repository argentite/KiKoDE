package kicode.code;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public class NumberExpression extends NumericExpression {

    public Double value;
    Dimension dimension;

    public NumberExpression() {
        value = 0.0;
    }

    public NumberExpression(double number) {
        value = number;
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return value;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(value.toString()));
        panel.setBackground(color);
        parent.add(panel);
    }
}
