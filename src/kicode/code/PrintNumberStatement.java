package kicode.code;

import java.awt.Color;
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

public class PrintNumberStatement extends Statement {

    public NumericExpression value;

    final Color color = new Color(255, 102, 0);

    public PrintNumberStatement() {
        value = new NullNumericExpression();
    }

    public PrintNumberStatement(NumericExpression value) {
        this.value = value;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        JLabel label = new JLabel("Display ");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.add(value.buildComponents(panel, this));

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
        vm.output += value.evaluate(vm);
    }

    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        value.save(xsw);
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
        value = (NumericExpression) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        value.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;

    }
}
