package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

/**
 * Print a string
 */
public class PrintTextStatement extends Statement {

    public String text;

    final Color color = new Color(255, 102, 0);

    public PrintTextStatement() {
        text = "";
    }

    public PrintTextStatement(String description) {
        this.text = description;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(color);

        JLabel label = new JLabel("Display: " + text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        panel.add(label);

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
        vm.output += text + '\n';
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        xsw.writeAttribute("text", text);
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        text = xsr.getAttributeValue(null, "text");
        while (!(xsr.getEventType() != XMLStreamReader.END_ELEMENT && xsr.getLocalName().equals(getClass().getSimpleName()))) {
            if (!xsr.hasNext()) {
                return false;
            }
            xsr.next();
        }
        return true;
    }
}
