package kicode.code;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import kicode.VirtualMachine;

/**
 * An expression whose value is a number
 */
public interface NumericExpression extends Expression {

    final Color color = new Color(204, 51, 0);

    abstract Double evaluate(VirtualMachine vm);

    default NumericExpression showChangeDialog(JComponent parentComp) {
        final String[] expressionTypes = {"Variable", "Number", "Addition", "Subtraction", "Multiplication", "Division"};

        int choice = JOptionPane.showOptionDialog(SwingUtilities.getRoot(parentComp), "Choose type of expression you want to create", "EXpression Type", 0, JOptionPane.QUESTION_MESSAGE, null, expressionTypes, null);

        switch (choice) {
            case 0:
                String name = JOptionPane.showInputDialog(SwingUtilities.getRoot(parentComp), "Variable name: ");
                if (name != null) {
                    return new Variable(name);
                }
                break;

            case 1:
                try {
                    String numStr = JOptionPane.showInputDialog(SwingUtilities.getRoot(parentComp), "Number: ");
                    if (numStr != null) {
                        Double num = Double.parseDouble(numStr);
                        return new NumberExpression(num);
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
                break;

            case 2:
                return new AddExpression();
            case 3:
                return new SubtractExpression();
            case 4:
                return new MultiplyExpression();

            case 5:
                return new DivideExpression();

        }

        return null;
    }

    default void addChangeEvent(JPanel panel, JComponent parentComp, Object parentCode, Expression currentExpression) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                NumericExpression expression = showChangeDialog(parentComp);
                if (expression != null) {
                    JComponent newcomp = expression.buildComponents(parentComp, parentCode);

                    if (parentCode instanceof NumericSymbolExpression) {
                        NumericSymbolExpression parentExpression = (NumericSymbolExpression) parentCode;

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
                    } else if (parentCode instanceof BooleanSymbolExpression) {
                        BooleanSymbolExpression parentExpression = (BooleanSymbolExpression) parentCode;

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
                    } else if (parentCode instanceof SetStatement) {
                        SetStatement parentStatement = (SetStatement) parentCode;
                        parentStatement.rhs = expression;

                        parentComp.remove(3);
                        parentComp.add(newcomp, 3);
                        parentComp.revalidate();
                        parentComp.repaint();

                    } else if (parentCode instanceof PrintNumberStatement) {
                        PrintNumberStatement parentStatement = (PrintNumberStatement) parentCode;
                        parentStatement.value = expression;

                        parentComp.remove(1);
                        parentComp.add(newcomp, 1);
                        parentComp.revalidate();
                        parentComp.repaint();

                    } else {
                        System.out.println(parentCode.getClass());
                    }
                }
            }
        });
    }
}
