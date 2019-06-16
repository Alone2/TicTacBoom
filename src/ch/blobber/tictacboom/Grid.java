
package ch.blobber.tictacboom;

import ch.jeda.Jeda;
import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.event.PointerDownListener;
import ch.jeda.event.PointerEvent;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Element;
import ch.jeda.ui.InputField;
import ch.jeda.ui.Text;
import ch.jeda.ui.View;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alain.sinzig
 */
public class Grid implements PointerDownListener
{
    View view;
    int playerSize;
    boolean canMove;
    ArrayList<Circle> circles;
    Canvas canvas;
    boolean[] spaceTaken;
    Client client;
    
    final private long seed;
    private Text text;
    
    public Grid(View view, Client c)
    {
        this.client = c;
        this.view = view;
        this.canvas = view.getBackground();
        this.seed = System.currentTimeMillis();
        this.circles = new ArrayList<Circle>();
        this.canMove = false;
        
        view.addEventListener(this);
    }
    
    public String askForServer() {
        return "localhost";
    }
    
    public void draw(int playerSize)
    {
        this.playerSize = playerSize;
        this.spaceTaken = new boolean[(int) Math.pow(playerSize + 1, 2)];
        
        int size = playerSize + 1;
        canvas.setColor(Color.BLACK);
        for (int i = 0; i < size; i++)
        {
            int count = i + 1;
            canvas.drawPolyline(0, (canvas.getHeight() / size) * count, canvas.getWidth(), canvas.getHeight() / size * count);
            canvas.drawPolyline((canvas.getHeight() / size) * count, 0, canvas.getHeight() / size * count, canvas.getWidth());
        }
        
        
        // ignore! : just some crap
        /*for (int i = 0; i < 250; i++)
        {
            Canvas newCanv =  new Canvas(500,500);
            newCanv.setColor(Color.WHITE);
            newCanv.fillRectangle(0, 0, canvas.getWidth(), canvas.getHeight());
            drawGrid(i, newCanv);
            canvas.drawCanvas(0, 0, newCanv);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }*/

    }
    
    public void clean() {
        canvas.setColor(Color.WHITE);
        canvas.fillRectangle(0, 0, view.getHeightDp(), view.getHeightDp());
        for (Element e : view.getElements()) {
            view.remove(e);
        }
    }
    
    public void drawDots(String code, int player)
    {
        String newCode[] = code.split("9");
        
        for (int i = 0; i < newCode.length; i++)
        {
            int[] xy = new int[2];
            //System.out.println(newCode[i].charAt(0));
            xy[0] = Integer.valueOf(String.valueOf(newCode[i].charAt(0)));
            xy[1] = Integer.valueOf(String.valueOf(newCode[i].charAt(1)));
            
            int space = cordsToSpace(xy);
            if (spaceTaken[space])
                continue;
            
            spaceTaken[space] = true;
            int truecords[] = rightPlace(xy);
            Circle c = new Circle(truecords[0], truecords[1], view.getWidthDp() / (playerSize + 1) - 50, player + seed);
            view.add(c);
            circles.add(c);
        }
        
    }
    
    public void message(String str) {
        try {
            view.remove(text);
        } catch (Exception e) {
        }
        text = new Text(0, 0, str);
        view.add(text);
    }

    private int[] rightPlace(int[] cords) {
         Float balance = view.getWidthDp() / (playerSize + 1);
         int[] xy = new int[2];
         xy[0] = (int) (balance * cords[0] + balance/2);
         xy[1] = (int) (balance * cords[1]  + balance/2);
         
         return xy;
    }
    
    private int cordsToSpace(int[] xy) {
        int number = xy[0] + xy[1]*(playerSize + 1);
        return number;
    }
    
    @Override
    public void onPointerDown(PointerEvent pe) {
        if (!canMove) 
            return;
        
        int x = pe.getX();
        int y = pe.getY();
        
        int[] cords = new int[2];
        
        Float balance = view.getWidthDp() / (playerSize + 1);
        
        boolean foundX = false, foundY = false;
        for (int i = playerSize; i >= 0; i--) {
            if (i * balance < x && !foundX){
                cords[0] = i;
                foundX = true;
            }
            if (i * balance < y && !foundY){
                cords[1] = i;
                foundY = true;
            }
            if (foundX && foundY) break;
        }
        
        int space = cordsToSpace(cords);
        if (spaceTaken[space])
            return;
        view.remove(text);
        String xyStr = String.valueOf(cords[0]) + String.valueOf(cords[1]);
        drawDots(xyStr + "9", client.myself);
        canMove = false;
        client.doMove(xyStr);                
    }
}
