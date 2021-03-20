package kicode.ui;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BoxLayout;
import kicode.code.Block;
import kicode.code.CodeItem;
import kicode.code.Event;

public class CodeCanvas extends JPanel {
    public List<CodeItem> code;

    public CodeCanvas() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println(x + "," + y);
            }
        });
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //add(Box.createVerticalGlue());
    }

    public void buildUI() {
        removeAll();
        if (code != null) {
            for (var item : code) {
                item.addComponent(this);
            }
        }
    }


    /*@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (code != null) {
            for (var item : code) {
                item.body.draw(g, item.posX, item.posY);
            }
        }
    }*/
}
