
package ch.blobber.tictacboom;

import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.InputField;
import ch.jeda.ui.View;

/**
 *
 * @author alain.sinzig
 */
public class Grid implements KeyDownListener
{
    View view;
    int playerSize;
    boolean canMove = false;
    Circle[] circles;
    InputField input;
    Canvas canvas;
    int[] spaceTaken;
    
    public Grid(View view)
    {
        this.view = view;
        this.canvas = view.getBackground();
    }
    
    public String askForServer() {
        
        /*input = new InputField(100, 100, Alignment.LEFT)
        {            
                        
            @Override
            protected void characterDeleted()
            {
            }
            
            @Override
            protected void characterTyped(char c)
            {
            }
            
        };
        
        input.setHighlightColor(Color.BLACK);
        input.setInputHidden(false);
        input.setHintText("Type something");
        view.add(input);*/
        return "localhost";
    }
    
    public void draw()
    {
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
    
    public void drawDots(String code, boolean firsttime)
    {
        if (firsttime) {
            spaceTaken = new int[(int) Math.pow(playerSize + 1, 2)];
        }
        String newCode[] = code.split("9");
        for (int i = 0; i < newCode.length; i++)
        {
            int[] xy = new int[2];
            newCode[i].charAt(0)
        }
        
    }

    private int[] rightPlace(int[] cords) {
         Float balance = view.getWidthDp() / (playerSize + 1);
         int[] xy = new int[2];
         xy[0] = (int) (balance * cords[0] + balance/2);
         xy[1] = (int) (balance * cords[1]  + balance/2);
         
         return xy;
    }
    
    @Override
    public void onKeyDown(KeyEvent ke)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
