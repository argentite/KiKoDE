package kicode.code;

import javax.swing.JComponent;
import kicode.ui.CanvasDrawable;

/**
 *
 * @author argentite
 */
public class CodeItem implements CanvasDrawable {

    int posX;
    int posY;

    public Event event;
    public Block body;

    public CodeItem(Block b) {
        event = new StartEvent();
        body = b;
    }

    @Override
    public void addComponent(JComponent parent) {
        event.addComponent(parent);
        body.addComponent(parent);
    }
};
