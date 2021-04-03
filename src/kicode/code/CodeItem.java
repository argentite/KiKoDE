package kicode.code;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JComponent;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.ui.CanvasDrawable;

/**
 * An unit of code that can run in response to a its corresponding event
 */
public class CodeItem implements CanvasDrawable {

    int posX;
    int posY;

    public Event event;
    public Block body;

    public CodeItem(Block b) {
        event = new StartEvent();
        body = b;
    }

    public CodeItem() {
        event = null;
        body = new Block();
    }

    @Override
    public JComponent buildComponents(JComponent parentComp, Object parentCode) {
        parentComp.add(event.buildComponents(parentComp, null));
        parentComp.add(body.buildComponents(parentComp, null));

        return null;
    }

    public void save(XMLStreamWriter xsw) throws XMLStreamException {
        xsw.writeStartElement("Code");
        xsw.writeAttribute("event", event.getClass().getSimpleName().replace("Event", ""));
        body.save(xsw);
        xsw.writeEndElement();
    }

    public Boolean load(XMLStreamReader xsr) throws XMLStreamException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        String eventClass = xsr.getAttributeValue(null, "event");
        if (eventClass != null) {
            eventClass += "Event";
        }
        
        event = (Event) Class.forName("kicode.code." + eventClass).getDeclaredConstructor().newInstance();
        
        if (!xsr.hasNext()) return false;
        xsr.next();
        if (!xsr.getLocalName().equals("Block")) return false;
        
        return body.load(xsr);
    }
};
