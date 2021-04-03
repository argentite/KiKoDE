package kicode.code;

import kicode.VirtualMachine;

public class SubtractExpression extends NumericSymbolExpression {

    public SubtractExpression(NumericExpression lhs, NumericExpression rhs) {
        super(lhs, rhs);
    }

    public SubtractExpression() {
        super();
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) - rhs.evaluate(vm);
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
