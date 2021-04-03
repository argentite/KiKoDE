package kicode.code;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import kicode.ui.CanvasDrawable;

public abstract class Statement implements Code, CanvasDrawable {

    final Font font = new Font("SansSerif", 0, 12);

    JButton buildRemoveButton(JComponent parentComp, JPanel panel, Code parentCode) {
        JButton removeButton = new JButton("x");
        removeButton.setBorder(new EmptyBorder(0, 4, 0, 4));
        removeButton.setBackground(Color.RED);
        removeButton.addActionListener((e) -> {
            ((Block) parentCode).body.remove(this);

            parentComp.remove(panel);
            parentComp.revalidate();
            parentComp.repaint();
        });
        return removeButton;
    }

    Statement showAppendStatementDialog(JComponent parentComp) {
        final String[] statementTypes = {"while", "if", "set variable", "display text", "display number"};

        int choice = JOptionPane.showOptionDialog(SwingUtilities.getRoot(parentComp), "Choose type of statement you want to create", "Statement Type", 0, JOptionPane.QUESTION_MESSAGE, null, statementTypes, null);

        switch (choice) {
            case 0:
                return new WhileStatement();
            case 1:
                return new IfStatement();
            case 2:
                return new SetStatement();
            case 3:
                String text = JOptionPane.showInputDialog(SwingUtilities.getRoot(parentComp), "Enter text to display: ", "Display text", 0);
                if (text != null) {
                    return new PrintTextStatement(text);
                } else {
                    return null;
                }
            case 4:
                return new PrintNumberStatement();
        }

        return null;
    }
}
