package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * An event that is triggered when starting the execution
 */
public class StartEvent extends Event {

    final Color color = new Color(0, 102, 0);

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel whileLabel = new JLabel("At start");
        whileLabel.setForeground(Color.WHITE);
        panel.add(whileLabel);

        return panel;
    }
}
