package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kicode.VirtualMachine;
import static kicode.code.Expression.FONT;

/**
 *
 * @author argentite
 */
public class LessThanExpression extends BooleanExpression {

    public NumericExpression lhs;
    public NumericExpression rhs;

    public LessThanExpression(NumericExpression lhs, NumericExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    Boolean evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) < rhs.evaluate(vm);
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        lhs.addComponent(panel);
        JLabel label = new JLabel("<");
        label.setFont(FONT);
        label.setForeground(Color.WHITE);
        panel.add(label);
        rhs.addComponent(panel);

        parent.add(panel);
    }

}
