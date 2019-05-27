package ch.blobber.tictacboom;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;
import ch.blobber.tictacboom.Circle;

public class TicTacBoom extends Program
{

    View view;
    Canvas canvas;
    Client client;

    @Override
    public void run()
    {
        view = new View(500, 500, ViewFeature.ORIENTATION_LANDSCAPE);
        //drawGrid(250);

        client = new Client(this);
  

    }
    
    public void reset() {
        
    }
}
