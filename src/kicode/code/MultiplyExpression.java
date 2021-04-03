package kicode.code;

import kicode.VirtualMachine;

/**
 * Multiplication of two expressions
 */
public class MultiplyExpression extends NumericSymbolExpression {
    
    public MultiplyExpression(NumericExpression lhs, NumericExpression rhs) {
        super(lhs, rhs);
    }
    
    public MultiplyExpression() {
        super();
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) * rhs.evaluate(vm);
    }

    @Override
    public String getSymbol() {
        return "×";
    }
}
