package kicode;

import java.util.List;
import java.util.TreeMap;
import kicode.code.CodeItem;

/**
 *
 * @author argentite
 */
public class VirtualMachine {

    public List<CodeItem> code;
    public TreeMap<String, Double> variables;
    public String output;

    VirtualMachine(List<CodeItem> code) {
        this.code = code;
        variables = new TreeMap<>();
        output = "";
    }
    
    public void setVariable(String name, Double value) {
        variables.put(name, value);
    }
    
    public Double getVariable(String name) {
        return variables.get(name);
    }
    
    public void run() {
        code.forEach((item) -> item.body.run(this));
    }
}
