package kicode.code;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import kicode.VirtualMachine;

public interface BooleanExpression extends Expression {

    final Color color = new Color(102, 0, 102);

    abstract Boolean evaluate(VirtualMachine vm);

    default BooleanExpression showChangeDialog(JComponent parentComp) {
        final String[] expressionTypes = {"=", "<", ">", "and", "or"};

        int choice = JOptionPane.showOptionDialog(SwingUtilities.getRoot(parentComp), "Choose type of expression you want to create", "Expression Type", 0, JOptionPane.QUESTION_MESSAGE, null, expressionTypes, null);

        switch (choice) {
            case 0:
                return new EqualExpression();
            case 1:
                return new LessThanExpression();
            case 2:
                return new GreaterThanExpression();
            case 3:
                return new AndExpression();
            case 4:
                return new OrExpression();
        }

        return null;
    }

    default void addChangeEvent(JPanel panel, JComponent parentComp, Object parentCode, Expression currentExpression) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                BooleanExpression expression = showChangeDialog(parentComp);
                if (expression != null) {
                    JComponent newcomp = expression.buildComponents(parentComp, parentCode);

                    if (parentCode instanceof BooleanLogicalExpression) {
                        BooleanLogicalExpression parentExpression = (BooleanLogicalExpression) parentCode;

                        if (parentExpression.lhs == currentExpression) {
                            parentExpression.lhs = expression;

                            parentComp.remove(0);
                            parentComp.add(newcomp, 0);
                            parentComp.revalidate();
                            parentComp.repaint();
                        } else if (parentExpression.rhs == currentExpression) {
                            parentExpression.rhs = expression;

                            parentComp.remove(2);
                            parentComp.add(newcomp);
                            parentComp.revalidate();
                            parentComp.repaint();
                        }
                    } else if (parentCode instanceof WhileStatement) {
                        WhileStatement parentExpression = (WhileStatement) parentCode;

                        if (parentExpression.condition == currentExpression) {
                            parentExpression.condition = expression;

                            parentComp.remove(1);
                            parentComp.add(newcomp, 1);
                            parentComp.revalidate();
                            parentComp.repaint();
                        }
                    } else if (parentCode instanceof IfStatement) {
                        IfStatement parentExpression = (IfStatement) parentCode;

                        if (parentExpression.condition == currentExpression) {
                            parentExpression.condition = expression;

                            parentComp.remove(1);
                            parentComp.add(newcomp, 1);
                            parentComp.revalidate();
                            parentComp.repaint();
                        }
                    } else {
                        System.out.println(parentCode.getClass());
                    }
                }
            }
        });
    }
}
