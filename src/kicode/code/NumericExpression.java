package kicode.code;

import java.awt.Color;
import kicode.VirtualMachine;

/**
 *
 * @author argentite
 */
public abstract class NumericExpression implements Expression {

    final Color color = new Color(204, 51, 0);

    abstract Double evaluate(VirtualMachine vm);
}
