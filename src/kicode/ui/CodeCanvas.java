package kicode.ui;

import javax.swing.JPanel;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import kicode.code.CodeItem;

/**
 * A custom component that houses UI for code
 */
public class CodeCanvas extends JPanel {

    public List<CodeItem> code;

    public CodeCanvas() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void buildUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        removeAll();
        if (code != null) {
            code.forEach(item -> {
                JComponent childComp = item.buildComponents(this, null);
                if (childComp != null) {
                    add(childComp);
                }
            });
        }
        
        revalidate();
        repaint();
    }
}
