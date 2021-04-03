package kicode.code;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

/**
 * A placeholder expression to fill a slot for a numeric expression
 */
public class NullNumericExpression implements NumericExpression {

    final Color color = new Color(128, 128, 128);

    @Override
    public Double evaluate(VirtualMachine vm) {
        return 0.0;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("    "));
        panel.setBackground(color);

        addChangeEvent(panel, parentComp, parentCode, this);

        return panel;
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
