package kicode.code;

import java.awt.Font;
import kicode.ui.CanvasDrawable;

/**
 *
 * @author argentite
 */
public abstract interface Expression extends CanvasDrawable {

    final Font FONT = new Font("SansSerif", 0, 12);

    final int MARGIN_X = 8;
    final int MARGIN_Y = 4;
}
