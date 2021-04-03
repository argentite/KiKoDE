package kicode.code;

import kicode.VirtualMachine;

/**
 * Division of two expressions
 */
public class DivideExpression extends NumericSymbolExpression {
    
    public DivideExpression(NumericExpression lhs, NumericExpression rhs) {
        super(lhs, rhs);
    }
    
    public DivideExpression() {
        super();
    }

    @Override
    public Double evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) / rhs.evaluate(vm);
    }

    @Override
    public String getSymbol() {
        return "÷";
    }
}
