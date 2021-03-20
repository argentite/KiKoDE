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
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public class WhileStatement extends Statement {

    final int INDENT_SIZE = 16;
    final Color color = new Color(51, 51, 204);
    public BooleanExpression condition;
    public Block body;

    public WhileStatement(BooleanExpression c, Block b) {
        condition = c;
        body = b;
    }
    
    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        //panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(color);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel whileLabel = new JLabel("while");
        whileLabel.setForeground(Color.WHITE);
        header.add(whileLabel);
        
        condition.addComponent(header);
        
        panel.add(header);

        body.addComponent(panel);

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
        while (condition.evaluate(vm)) {
            body.run(vm);
        }
    }
}
