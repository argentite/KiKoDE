package kicode.code;

import java.awt.Font;
import kicode.ui.CanvasDrawable;

/**
 *
 * @author argentite
 */
public abstract class Statement implements Code, CanvasDrawable {

    final Font font = new Font("SansSerif", 0, 12);

    final int MARGIN_X = 16;
    final int MARGIN_Y = 8;
}
