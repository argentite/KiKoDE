package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author argentite
 */
public class StartEvent extends Event {

    final Color color = new Color(0, 102, 0);

    @Override
    public void addComponent(JComponent parent) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel whileLabel = new JLabel("At start");
        whileLabel.setForeground(Color.WHITE);
        panel.add(whileLabel);

        parent.add(panel);
    }
}
