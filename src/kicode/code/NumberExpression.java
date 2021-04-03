package kicode.code;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

/**
 * A numeric expression that just represents a constant number
 */
public class NumberExpression implements NumericExpression {

    public Double value;

    public NumberExpression() {
        value = 0.0;
    }

    public NumberExpression(double number) {
        value = number;
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return value;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setBackground(color);

        JLabel label = new JLabel(value.toString());
        label.setForeground(Color.WHITE);
        panel.add(label);

        addChangeEvent(panel, parentComp, parentCode, this);

        return panel;
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        xsw.writeAttribute("value", value.toString());
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        String numStr = xsr.getAttributeValue(null, "value");
        if (numStr != null) {
            value = Double.parseDouble(numStr);
        }
        
        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
