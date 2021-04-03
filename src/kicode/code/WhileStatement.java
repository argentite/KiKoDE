package kicode.code;

import java.awt.Color;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
 * Repeatedly run a set of statements while a Boolean expression is true
 */
public class WhileStatement extends Statement {

    final int INDENT_SIZE = 16;
    final Color color = new Color(51, 51, 204);
    public BooleanExpression condition;
    public Block body;

    public WhileStatement() {
        condition = new NullBooleanExpression();
        body = new Block();
    }

    public WhileStatement(BooleanExpression c, Block b) {
        condition = c;
        body = b;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(color);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel whileLabel = new JLabel("while");
        whileLabel.setForeground(Color.WHITE);
        header.add(whileLabel);

        header.add(condition.buildComponents(header, this));

        JButton addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener((e) -> {
            Statement s = showAppendStatementDialog(parentComp);

            if (s != null) {
                body.body.add(0, s);

                JPanel loopBodyPanel = (JPanel) panel.getComponent(1);

                loopBodyPanel.add(s.buildComponents(loopBodyPanel, body), 0);
                loopBodyPanel.revalidate();
                loopBodyPanel.repaint();
            }
        });
        header.add(addButton);

        header.add(buildRemoveButton(parentComp, panel, (Code) parentCode));

        panel.add(header);

        panel.add(body.buildComponents(panel, this));

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel footerLabel = new JLabel("end");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);

        addButton = new JButton("+");
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
        footer.add(addButton);

        panel.add(footer);

        return panel;
    }

    @Override
    public void run(VirtualMachine vm) {
        while (condition.evaluate(vm)) {
            body.run(vm);
        }
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        condition.save(xsw);
        body.save(xsw);
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
        condition = (BooleanExpression) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
        condition.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        if (xsr.getEventType() != XMLStreamReader.START_ELEMENT || (!xsr.getLocalName().equals("Block"))) {
            return false;
        }
        body.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
