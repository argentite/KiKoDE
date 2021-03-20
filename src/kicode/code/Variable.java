package kicode.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public class Variable extends NumericExpression {

    public String name;
    final Color color = new Color(255, 51, 153);
    Dimension dimension;
    
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(name));
        panel.setBackground(color);
        parent.add(panel);
    }

    @Override
    Double evaluate(VirtualMachine vm) {
        return vm.variables.get(name);
    }
}
