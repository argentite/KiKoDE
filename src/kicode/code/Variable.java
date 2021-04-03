package kicode.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

/**
 * An unit of code that a reference a variable by name
 */
public class Variable implements NumericExpression {

    public String name;
    final Color color = new Color(255, 51, 153);
    Dimension dimension;

    public Variable() {
        this.name = "x";
    }

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setBackground(color);

        JLabel label = new JLabel(name);
        label.setForeground(Color.WHITE);
        panel.add(label);

        addChangeEvent(panel, parentComp, parentCode, this);

        return panel;
    }

    public JComponent buildRenamableComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setBackground(color);

        JLabel label = new JLabel(name);
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                String newname = JOptionPane.showInputDialog(SwingUtilities.getRoot(parentComp), "Enter new variable name: ");
                if (newname != null) {
                    name = newname;
                    parentComp.remove(1);
                    parentComp.add(buildRenamableComponents(parentComp, parentCode), 1);
                    parentComp.revalidate();
                    parentComp.repaint();
                }
            }
        });

        return panel;
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return vm.getVariable(name);
    }

    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        xsw.writeAttribute("name", name);
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        String nameStr = xsr.getAttributeValue(null, "name");
        if (nameStr != null) {
            name = nameStr;
        }

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }

}
