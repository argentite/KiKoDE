package kicode.code;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import kicode.VirtualMachine;
import kicode.ui.CanvasDrawable;

public class Block implements Code, CanvasDrawable {

    public List<Statement> body;
    final Color color = Color.BLACK;

    public Block() {
        body = new LinkedList();
    }

    public Block(Statement[] s) {
        body = new LinkedList(Arrays.asList(s));
    }

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        //panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(color);
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(0, 16, 0, 0));
        for (var s : body) {
            s.addComponent(panel);
        }
        //panel.setMaximumSize(panel.getPreferredSize());
        parent.add(panel);
    }

    @Override
    public void run(VirtualMachine vm) {
        body.forEach((s) -> s.run(vm));
    }
}
