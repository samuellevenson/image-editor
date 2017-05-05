import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Messing around with components on top of components,
 * and dragging components around.
 * 
 * @author A. Jacoby (21 May 2015)
 * @see DragHelper
 */
public class LayeredComponents {
  private static final String BACKGROUND_PATH = "/Users/alex/Documents/Dropbox/comics/dinosaur-checkLogicShiii.png";
  private static final String DRAG_LABEL_PATH = "/Users/alex/Downloads/drag me.png";
  private static final String DRAG_LABEL2_PATH = "/Users/alex/Downloads/drag me2.png";
  
  private JFrame _win;
  private JLabel _backgroundLbl;
  private JLabel _dragLbl; // Will use mouselistener directly for Drag and Drop (DnD)
  private JLabel _dragLbl2; // Will use mouselistener on parent component for DnD
  
  /** Point on label where mouse drag started. */
  private java.awt.Point _dragPt = null;
  
  public LayeredComponents() {
    init();
  }
  
  private void init() {
    _win = new JFrame("Layered Components");
    _win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = _win.getContentPane();
    final JLayeredPane layeredPane = new JLayeredPane();
    content.add(layeredPane);
    
    _backgroundLbl = new JLabel(new ImageIcon(BACKGROUND_PATH));
    Dimension bckSize = _backgroundLbl.getPreferredSize();
    _backgroundLbl.setBounds(0, 0, (int) bckSize.getWidth(), (int) bckSize.getHeight());
    _backgroundLbl.setOpaque(true);
    layeredPane.add(_backgroundLbl, JLayeredPane.DEFAULT_LAYER);
    DragHelper.makeDraggable(_backgroundLbl); // Just for fun :)
    
    // Create a draggable label the old-fashioned way
    _dragLbl = new JLabel(new ImageIcon(DRAG_LABEL_PATH));
    Dimension dragSize = _dragLbl.getPreferredSize();
    _dragLbl.setBounds(10, 10, (int) dragSize.getWidth(), (int) dragSize.getHeight());
    layeredPane.add(_dragLbl, JLayeredPane.DRAG_LAYER);
    _dragLbl.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent me) {
        if (_dragPt == null) {
          _dragPt = me.getPoint();
        } else {
          java.awt.Point newLocOnScreen = me.getLocationOnScreen();
          newLocOnScreen.x -= _dragPt.x;
          newLocOnScreen.y -= _dragPt.y;
          java.awt.Point parentLoc = layeredPane.getLocationOnScreen();
          java.awt.Point newLoc = new java.awt.Point(newLocOnScreen.x - parentLoc.x, newLocOnScreen.y - parentLoc.y);
          // Protect against dragging off left or top of window
          newLoc.x = Math.max(newLoc.x, 0);
          newLoc.y = Math.max(newLoc.y, 0);
          // Protect against dragging off right or bottom of window
          newLoc.x = Math.min(newLoc.x, layeredPane.getWidth() - _dragLbl.getWidth());
          newLoc.y = Math.min(newLoc.y, layeredPane.getHeight() - _dragLbl.getHeight());
          _dragLbl.setLocation(newLoc);
        }
        // System.out.println("x: " + me.getX() + ", y: " + me.getY());
      }
    });
    
    // Reset the _dragPt to null once mouse is released and drag is stopped
    _dragLbl.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent me) {
        _dragPt = null;
      }
    });
    
    // Create a draggable label the new, easy way, with DragHelper!
    _dragLbl2 = new JLabel(new ImageIcon(DRAG_LABEL2_PATH));
    Dimension dragSize2 = _dragLbl2.getPreferredSize();
    _dragLbl2.setBounds((int) (_dragLbl.getX() + dragSize.getWidth() + 10), 10, (int) dragSize2.getWidth(), (int) dragSize2.getHeight());
    layeredPane.add(_dragLbl2, JLayeredPane.DRAG_LAYER);
    DragHelper.makeDraggable(_dragLbl2);
    
    layeredPane.setPreferredSize(bckSize);
    
    _win.pack();
    _win.setVisible(true);
  } // end init()
  
  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        LayeredComponents lc = new LayeredComponents();
      }
    });
  }
}
