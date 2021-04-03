package kicode.code;

import java.util.Objects;
import kicode.VirtualMachine;

/**
 * Equality of two expressions
 */
public class EqualExpression extends BooleanSymbolExpression {

    public EqualExpression(NumericExpression lhs, NumericExpression rhs) {
        super(lhs, rhs);
    }

    public EqualExpression() {
        super();
    }

    @Override
    public Boolean evaluate(VirtualMachine vm) {
        return Objects.equals(lhs.evaluate(vm), rhs.evaluate(vm));
    }

    @Override
    public String getSymbol() {
        return "=";
    }
}
