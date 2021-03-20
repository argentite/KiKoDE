package kicode;

import java.util.Arrays;
import javax.swing.JFrame;
import kicode.code.AddExpression;
import kicode.code.Block;
import kicode.code.CodeItem;
import kicode.code.CommandStatement;
import kicode.code.EqualExpression;
import kicode.code.IfStatement;
import kicode.code.LessThanExpression;
import kicode.code.NumberExpression;
import kicode.code.SetStatement;
import kicode.code.Statement;
import kicode.code.Variable;
import kicode.code.WhileStatement;

/**
 *
 * @author argentite
 */
public class Kicode {

    public static void main(String[] args) {
        // TODO code application logic here
        MainWindow frame = new MainWindow();

        CodeItem c1 = new CodeItem(new Block(new Statement[]{
            new CommandStatement("Hello, World!"),
            new SetStatement(new Variable("speed"), new NumberExpression(42)),
            new CommandStatement("Another statement!"),
            new CommandStatement("Yet another statement!")
        }));

        CodeItem c2 = new CodeItem(new Block(new Statement[]{
            new CommandStatement("Hello, World!"),
            new CommandStatement("Another statement!"),
            new IfStatement(
            new EqualExpression(new Variable("speed"), new NumberExpression(69)),
            new Block(new Statement[]{
                new CommandStatement("Nice")}),
            new Block(new Statement[]{
                new CommandStatement("Sad")})
            ),
            new SetStatement(new Variable("counter"), new NumberExpression(1)),
            new WhileStatement(
            new LessThanExpression(new Variable("counter"), new NumberExpression(5)),
            new Block(new Statement[]{
                new CommandStatement("Hello"),
                new SetStatement(new Variable("counter"), new AddExpression(new Variable("counter"), new NumberExpression(1)))})
            ),
            new CommandStatement("Yet another statement!")
        }));
        CodeItem[] testcode = {c1, c2};

        frame.canvas.code = Arrays.asList(testcode);
        frame.canvas.buildUI();

        //VirtualMachine vm = new VirtualMachine(testcode);
        frame.setVisible(true);
    }
}
