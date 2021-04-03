package kicode.code;

import kicode.VirtualMachine;

/**
 * Logical and of two BooleanExpression
 */
public class AndExpression extends BooleanLogicalExpression {

    public AndExpression(BooleanExpression lhs, BooleanExpression rhs) {
        super(lhs, rhs);
    }

    public AndExpression() {
        super();
    }

    @Override
    public Boolean evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) && rhs.evaluate(vm);
    }

    @Override
    public String getText() {
        return "and";
    }

}
