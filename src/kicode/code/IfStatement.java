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

public class IfStatement extends Statement {

    final int INDENT_SIZE = 16;
    final Color color = new Color(0, 102, 204);
    public BooleanExpression condition;
    public Block ifBody;
    public Block elseBody;

    public IfStatement() {
        condition = new NullBooleanExpression();
        ifBody = new Block();
        elseBody = new Block();
    }

    public IfStatement(BooleanExpression c, Block ifb, Block elseb) {
        condition = c;
        ifBody = ifb;
        elseBody = elseb;
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.white));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(color);

        JPanel ifHeader = new JPanel();
        ifHeader.setOpaque(false);
        ifHeader.setLayout(new FlowLayout(FlowLayout.LEADING));

        JLabel ifLabel = new JLabel("if");
        ifLabel.setForeground(Color.WHITE);
        ifHeader.add(ifLabel);

        ifHeader.add(condition.buildComponents(ifHeader, this));

        JButton addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener((e) -> {
            Statement s = showAppendStatementDialog(parentComp);

            if (s != null) {
                ifBody.body.add(0, s);

                JPanel loopBodyPanel = (JPanel) panel.getComponent(1);

                loopBodyPanel.add(s.buildComponents(loopBodyPanel, ifBody), 0);
                loopBodyPanel.revalidate();
                loopBodyPanel.repaint();
            }
        });
        ifHeader.add(addButton);

        ifHeader.add(buildRemoveButton(parentComp, panel, (Code) parentCode));

        panel.add(ifHeader);

        panel.add(ifBody.buildComponents(panel, this));

        JPanel elseHeader = new JPanel();
        elseHeader.setOpaque(false);
        elseHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel elseLabel = new JLabel("else");
        elseLabel.setForeground(Color.WHITE);
        elseHeader.add(elseLabel);

        addButton = new JButton("+");
        addButton.setBorder(new EmptyBorder(0, 2, 0, 2));
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener((e) -> {
            Statement s = showAppendStatementDialog(parentComp);

            if (s != null) {
                elseBody.body.add(0, s);

                JPanel elseBodyPanel = (JPanel) panel.getComponent(3);

                elseBodyPanel.add(s.buildComponents(elseBodyPanel, elseBody), 0);
                elseBodyPanel.revalidate();
                elseBodyPanel.repaint();
            }
        });
        elseHeader.add(addButton);

        panel.add(elseHeader);

        panel.add(elseBody.buildComponents(panel, this));

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
        if (condition.evaluate(vm)) {
            ifBody.run(vm);
        } else {
            elseBody.run(vm);
        }
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        condition.save(xsw);
        ifBody.save(xsw);
        elseBody.save(xsw);
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
            System.err.println("IFFFF");
            return false;
        }
        ifBody.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        if (xsr.getEventType() != XMLStreamReader.START_ELEMENT || (!xsr.getLocalName().equals("Block"))) {
            System.err.println("ELLLSSSEEE");
            return false;
        }
        elseBody.load(xsr);

        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();
        return xsr.getEventType() == XMLStreamReader.END_ELEMENT;
    }
}
