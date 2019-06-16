
package ch.blobber.tictacboom;

import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Element;
import java.util.Random;

/**
 *
 * @author alain.sinzig
 */
public class Circle extends Element
{
    // Circle ist ein random Farbiger Kreis
    double radius;
    Color color;
    
    protected Circle(double x, double y, double width, long seed) {
        this.radius = width/2;
        this.setPosition(x - radius, y - radius);
        color = getRandomColor(seed);
        this.setDrawOrder(0);
    }
    
    private Color getRandomColor(long seed) {
        Random rand = new Random(seed);
        int r = (int) (rand.nextDouble() * 255);
        int g = (int) (rand.nextDouble() * 255);
        int b = (int) (rand.nextDouble() * 255);
        
        return new Color(r,g,b);
    }
    
    @Override
    protected void draw(Canvas canvas)
    {        
        canvas.setColor(color);
        canvas.fillCircle(radius, radius, radius);
    }
}
