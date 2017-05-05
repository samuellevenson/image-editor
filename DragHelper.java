import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Swing utility to make a component drag-and-droppable.
 * <p/>
 * Note: Probably will only work if parent container of component has a null
 * layout manager.
 * <p/>
 * Note 2: Doesn't currently do anything about z-order of draggable component.
 * <p/>
 * Example:<pre>
 *  JLabel label = new JLabel(...); // Create label
 *  label.setBounds(...); // Set location
 *  layeredPane.add(label, JLayeredPane.DRAG_LAYER); // Add to parent
 *  <b>DragHelper.makeDraggable(label);</b> // Make it DRAGGABLE!
 * </pre>
 * 
 * @author A. Jacoby, 1 June 2015
 */
public class DragHelper extends MouseInputAdapter {
  /** Component that will be draggable. */
  private final Component _comp;
  /**
   * Location of mouse click, relative to top-left of draggable component,
   * if we're in the middle of a drag operation.  Otherwise null.
   */
  private java.awt.Point _dragOffset;
  
  /**
   * Makes given component draggable - component must already belong to a
   * container.
   * Note: If you want to make the component NON-draggable later, you must
   * use the constructor instead.
   */
  public static DragHelper makeDraggable(Component comp) {
    return new DragHelper(comp);
  }
  
  /** 
   * Creates new DragHelper for given component and registers itself with component's parent container
   * as a MouseListener and MouseMotionListener.
   */
  public DragHelper(Component comp) {
    _comp = comp;
    comp.getParent().addMouseListener(this);
    comp.getParent().addMouseMotionListener(this);
  }
  
  /** Returns whether or not mouse event applies to our component (always yes if drag started!). */
  private boolean onMyComponent(MouseEvent me) {
    if (dragStarted()) { return true; }
    Container parent = _comp.getParent();
    // Make sure this drag event is for our component: bail out if not
    Component comp = parent.getComponentAt(me.getPoint());
    return (comp == _comp);
  }
  
  /** Returns whether or not user has started a drag event on our component. */
  private boolean dragStarted() {
    return _dragOffset != null;
  }
  
  /** If mouse pressed on our component, starts a drag event on it. */
  public void mousePressed(MouseEvent me) {
    if (!onMyComponent(me)) { return; }
    // If start of drag, record the mouse position (drag offset)
    if (_dragOffset != null) {
      throw new IllegalStateException("Drag starting, but offset already non-null.");
    } else {
      _dragOffset = _comp.getMousePosition();
      if (_dragOffset == null) {
        throw new IllegalStateException("I was dragged but mouse wasn't over me: " + me);
      }
    }
  }
  
  /**
   * If our component is currently being dragged, moves our component
   * (only within bounds of parent container).
   */
  public void mouseDragged(MouseEvent me) {
    if (!dragStarted()) { return; }
    // Note: MouseEvent will be on parent component of dragable thing,
    // so we'll have to translate coordinates by the dragOffset recorded
    // at start of drag (mouse location should stay constant relative to
    // dragged component).
    Container parent = _comp.getParent();
    java.awt.Point newLoc = me.getPoint();
    newLoc.x -= _dragOffset.x;
    newLoc.y -= _dragOffset.y;
    // Protect against dragging off left or top of window
    newLoc.x = Math.max(newLoc.x, 0);
    newLoc.y = Math.max(newLoc.y, 0);
    // Protect against dragging off right or bottom of window
    newLoc.x = Math.min(newLoc.x, parent.getWidth() - _comp.getWidth());
    newLoc.y = Math.min(newLoc.y, parent.getHeight() - _comp.getHeight());
    // Sets new location
    _comp.setLocation(newLoc);
  }
  
  /** Ends any drag event on our component. */
  public void mouseReleased(MouseEvent me) {
    _dragOffset = null;
  }
  
}