package kicode.code;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;
import kicode.ui.CanvasDrawable;

/*
 * This represents a sequence of statements
 */
public class Block implements Code, CanvasDrawable {

    public List<Statement> body;
    final Color color = Color.BLACK;

    public Block() {
        body = new LinkedList<>();
    }

    public Block(Statement[] s) {
        body = new LinkedList<>(Arrays.asList(s));
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(0, 32, 0, 0));
        body.forEach(s -> {
            panel.add(s.buildComponents(panel, this));
        });
        return panel;
    }

    @Override
    public void run(VirtualMachine vm) {
        body.forEach((s) -> s.run(vm));
    }

    @Override
    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement(getClass().getSimpleName());
        for (var s : body) {
            s.save(xsw);
        }
        xsw.writeEndElement();
    }

    @Override
    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        if (!xsr.hasNext()) {
            return false;
        }
        xsr.next();

        while (!(xsr.getEventType() == XMLStreamReader.END_ELEMENT && xsr.getLocalName().equals("Block"))) {
            if (xsr.getEventType() == XMLStreamReader.START_ELEMENT) {
                String className = xsr.getLocalName();
                System.out.println(className);
                Statement statement = (Statement) Class.forName("kicode.code." + className).getDeclaredConstructor().newInstance();
                statement.load(xsr);
                body.add(statement);
            }
            
            if (!xsr.hasNext()) {
                return false;
            }
            xsr.next();
        }

        return true;
    }
}
