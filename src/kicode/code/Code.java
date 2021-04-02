package kicode.code;

import java.lang.reflect.InvocationTargetException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import kicode.VirtualMachine;

public interface Code {

    void run(VirtualMachine vm);

    void save(XMLStreamWriter xsw) throws XMLStreamException;

    public Boolean load(XMLStreamReader xsr) throws
            XMLStreamException,
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            IllegalArgumentException,
            InvocationTargetException;
}
