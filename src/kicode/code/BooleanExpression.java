package kicode.code;

import java.awt.Color;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public abstract class BooleanExpression implements Expression {

    final Color color = new Color(102, 0, 102);

    abstract Boolean evaluate(VirtualMachine vm);
}
