package kicode;

import java.util.Arrays;
import kicode.code.*;
import kicode.ui.EditorWindow;

public class Kicode {

    public static void main(String[] args) {
        EditorWindow frame = new EditorWindow();

        CodeItem c1 = new CodeItem(new Block(new Statement[]{
            new PrintTextStatement("Hello, World!"),
            new PrintTextStatement("Another line of output"),
            new SetStatement(new Variable("speed"), new AddExpression(new NumberExpression(16), new NumberExpression(16))),
            new IfStatement(
            new EqualExpression(new NumberExpression(42), new AddExpression(new AddExpression(new Variable("speed"), new NumberExpression(5)), new NumberExpression(5))),
            new Block(new Statement[]{
                new PrintTextStatement("Logic holds!")}),
            new Block(new Statement[]{
                new PrintTextStatement("Something is wrong...")})
            ),
            new SetStatement(new Variable("counter"), new NumberExpression(1)),
            new WhileStatement(
            new LessThanExpression(new Variable("counter"), new NumberExpression(5)),
            new Block(new Statement[]{
                new PrintNumberStatement(new Variable("counter")),
                new PrintTextStatement("Hello"),
                new SetStatement(new Variable("counter"), new AddExpression(new Variable("counter"), new NumberExpression(1)))})
            ),
            new PrintTextStatement("Yet another statement!")
        }));
        CodeItem[] testcode = {c1};

        frame.canvas.code = Arrays.asList(testcode);
        frame.canvas.buildUI();

        frame.setVisible(true);
    }
}
