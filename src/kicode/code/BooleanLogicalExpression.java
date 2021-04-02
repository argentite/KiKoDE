package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public abstract class BooleanLogicalExpression implements BooleanExpression {

    public BooleanExpression lhs;
    public BooleanExpression rhs;

    public BooleanLogicalExpression(BooleanExpression lhs, BooleanExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public BooleanLogicalExpression() {
        this.lhs = new NullBooleanExpression();
        this.rhs = new NullBooleanExpression();
    }

    public abstract String getText();

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        panel.add(lhs.buildComponents(panel, this));

        JLabel label = new JLabel(" " + getText() + " ");
        label.setFont(FONT);
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.add(rhs.buildComponents(panel, this));

        addChangeEvent(panel, parentComp, parentCode, this);
        return panel;
    }

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
        lhs = (BooleanExpression) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        lhs.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        if (xsr.getEventType() != XMLStreamReader.START_ELEMENT) {
            return false;
        }
        className = xsr.getLocalName();
        rhs = (BooleanExpression) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        rhs.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
