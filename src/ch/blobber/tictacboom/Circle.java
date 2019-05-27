
package ch.blobber.tictacboom;

import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Element;

/**
 *
 * @author alain.sinzig
 */
public class Circle extends Element
{
    // Circle ist ein random Farbiger Kreis
    double radius;
    Color color;
    
    protected Circle(double x, double y, double width) {
        this.setPosition(x, y);
        this.radius = width/2;
        color = getRandomColor();
        this.setDrawOrder(0);
    }
    
    private Color getRandomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        
        return new Color(r,g,b);
    }
    
    @Override
    protected void draw(Canvas canvas)
    {        
        canvas.setColor(color);
        canvas.fillCircle(radius, radius, radius);
    }
}
