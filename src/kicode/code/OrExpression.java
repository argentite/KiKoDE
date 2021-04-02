package kicode.code;

import kicode.VirtualMachine;

public class OrExpression extends BooleanLogicalExpression {

    public OrExpression(BooleanExpression lhs, BooleanExpression rhs) {
        super(lhs, rhs);
    }

    public OrExpression() {
        super();
    }

    @Override
    public Boolean evaluate(VirtualMachine vm) {
        return lhs.evaluate(vm) || rhs.evaluate(vm);
    }

    @Override
    public String getText() {
        return "or";
    }

}
