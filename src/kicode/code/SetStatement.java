package kicode.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

/**
 * Set the value of a variable
 */
public class SetStatement extends Statement {

    public Variable lhs;
    public NumericExpression rhs;

    final Color color = new Color(255, 102, 0);
    Dimension dimension;

    public SetStatement() {
        lhs = new Variable("x");
        rhs = new NullNumericExpression();
    }

    public SetStatement(Variable variable, NumericExpression value) {
        lhs = variable;
        rhs = value;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        JLabel label = new JLabel("Set");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.add(lhs.buildRenamableComponents(panel, this));

        label = new JLabel("to");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.add(rhs.buildComponents(panel, this));

        JButton addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener((e) -> {
            Statement s = showAppendStatementDialog(parentComp);

            if (s != null) {
                int index = ((Block) parentCode).body.indexOf(this) + 1;
                ((Block) parentCode).body.add(index, s);

                parentComp.add(s.buildComponents(parentComp, parentCode), index);
                parentComp.revalidate();
                parentComp.repaint();
            }
        });

        panel.add(addButton);

        panel.add(buildRemoveButton(parentComp, panel, (Code) parentCode));

        return panel;
    }

    @Override
    public void run(VirtualMachine vm) {
        vm.setVariable(lhs.name, rhs.evaluate(vm));
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        lhs.save(xsw);
        rhs.save(xsw);
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        if (xsr.getEventType() != XMLStreamReader.START_ELEMENT) {
            return false;
        }
        String className = xsr.getLocalName();
        lhs = (Variable) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        lhs.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        if (xsr.getEventType() != XMLStreamReader.START_ELEMENT) {
            return false;
        }
        className = xsr.getLocalName();
        rhs = (NumericExpression) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        rhs.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
