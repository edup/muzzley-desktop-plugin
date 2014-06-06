package com.desktop.gui.layout;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/** @see http://stackoverflow.com/questions/7254488 */
public class JPopupMenuEx extends JPopupMenu {
	
    private static class JSeparatorEx extends JSeparator {

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            if (d.height == 0) {
                d.height = 4;
            }
            return d;
        }
    }
    
    
    {
        // need to disable that to work
        setLightWeightPopupEnabled(false);
    }
	
    private MouseAdapter mouseListener = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            ((JMenuItem) e.getSource()).setArmed(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((JMenuItem) e.getSource()).setArmed(false);
        }
    };

    @Override
    public void addSeparator() {
        add(new JSeparatorEx());
    }

    @Override
    public JMenuItem add(JMenuItem menuItem) {
        menuItem.addMouseListener(mouseListener);
        return super.add(menuItem);
    }

/*    @Override
    public void setVisible(boolean visible) {
        if (visible == isVisible())
            return;
        super.setVisible(visible);
        if (visible) {
            // attempt to set tranparency
            try {
                Window w = SwingUtilities.getWindowAncestor(this);
                w.setOpacity(0.667F);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
    
}