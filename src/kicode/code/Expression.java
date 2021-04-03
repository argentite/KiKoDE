package kicode.code;

import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.ui.CanvasDrawable;

/**
 * An unit (or whole) of expression that can be evaluated
 */
public abstract interface Expression extends CanvasDrawable {

    final Font FONT = new Font("SansSerif", Font.BOLD, 12);

    final int MARGIN_X = 8;
    final int MARGIN_Y = 4;

    public void save(XMLStreamWriter xsw) throws XMLStreamException;

    public Boolean load(XMLStreamReader xsr) throws
            XMLStreamException,
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            IllegalArgumentException,
            InvocationTargetException;

}
