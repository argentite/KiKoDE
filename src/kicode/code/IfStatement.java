package kicode.code;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public class IfStatement extends Statement {

    final int INDENT_SIZE = 16;
    final Color color = new Color(0, 102, 204);
    public BooleanExpression condition;
    public Block ifBody;
    public Block elseBody;

    public IfStatement(BooleanExpression c, Block ifb, Block elseb) {
        condition = c;
        ifBody = ifb;
        elseBody = elseb;
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(color);

        JPanel ifHeader = new JPanel();
        ifHeader.setOpaque(false);
        ifHeader.setLayout(new FlowLayout(FlowLayout.LEADING));

        JLabel ifLabel = new JLabel("if");
        ifLabel.setForeground(Color.WHITE);
        ifHeader.add(ifLabel);
        
        condition.addComponent(ifHeader);

        panel.add(ifHeader);

        ifBody.addComponent(panel);

        JPanel elseHeader = new JPanel();
        elseHeader.setOpaque(false);
        elseHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel elseLabel = new JLabel("else");
        elseLabel.setForeground(Color.WHITE);
        elseHeader.add(elseLabel);
        panel.add(elseHeader);

        elseBody.addComponent(panel);

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel footerLabel = new JLabel("end");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);
        panel.add(footer);

        parent.add(panel);
    }

    @Override
    public void run(VirtualMachine vm) {
        if (condition.evaluate(vm)) {
            ifBody.run(vm);
        } else {
            elseBody.run(vm);
        }
    }
}
