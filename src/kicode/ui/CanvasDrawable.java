package kicode.ui;

import javax.swing.JComponent;

/**
 * An interface to be implemented by any code that has GUI
 */
public interface CanvasDrawable {
    public JComponent buildComponents(JComponent parentComp, Object parentCode);
}
