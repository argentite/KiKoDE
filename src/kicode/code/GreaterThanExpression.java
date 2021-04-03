package kicode.code;

import kicode.VirtualMachine;

/**
 * True if left operand is greater than right operand
 */
public class GreaterThanExpression extends BooleanSymbolExpression {

    public GreaterThanExpression(NumericExpression lhs, NumericExpression rhs) {
        super(lhs, rhs);
    }

    public GreaterThanExpression() {
        super();
    }

    @Override
    public Boolean evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) > rhs.evaluate(vm);
    }

    @Override
    public String getSymbol() {
        return ">";
    }
}
