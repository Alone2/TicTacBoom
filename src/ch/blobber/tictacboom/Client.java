
package ch.blobber.tictacboom;

import ch.jeda.Data;
import ch.blobber.tictacboom.Grid;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alain.sinzig
 */
public class Client
{
    Grid grid;
    TicTacBoom tac;
    TcpClass tcp;
    int port = 6465;
    int playerSize;
    int myself;
    
    public Client(TicTacBoom tac) {
        this.grid = new Grid(tac.view, this);
        this.tac = tac;
        this.connect();
    }
    
    
    public void connect() {
        String URL = grid.askForServer();
        grid.message("connecting...");
        tcp = new TcpClass(URL, this, port);
    }
    
    public void connectReturn(Data d) {
        grid.message("waiting for players...");
        // Definiert wie das Zeugs aussieht
        Boolean con = d.readBoolean("isConnected");
        if (!con) {
            this.connect();
        }
        myself = d.readInt("myself");
        playerSize = d.readInt("playerSize");
        grid.draw(playerSize);
        canPlay(false);
    }
    
    public void canPlay(boolean again) {
        if (!again) {
            grid.message("");
        }
        Data data = new Data();
        data.writeString("From", "canPlay");
        data.writeBoolean("again", again);
        data.writeInt("myself", myself);
        try {
            tcp.sendData(data);
        } catch (Exception e) {
            canPlay(false);
        }
    }
    
    public void canPlayReturn(Data d) {
        if (d.readBoolean("again")) {
            grid.clean(); 
            grid.draw(playerSize);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] saveLongArr = d.readStrings("saveStringArr");
        for (int i = 0; i < playerSize; i++) {
            if (saveLongArr[i] != null) {
                grid.drawDots(saveLongArr[i], i);
            }          
        }
        
        Boolean canPlay = d.readBoolean("canPlay");
        Boolean isFinished = d.readBoolean("isFinished");
        Boolean hasWon = d.readBoolean("hasWon");

        if (isFinished) {
            finished(hasWon);
            return;
        }
        
        if (canPlay) {
            grid.canMove = true;
            grid.message("Your turn!");
            return; 
        }
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println(e);
        }
        canPlay(false);
        
    }
    
    public void doMove(String xy) {
        Data data = new Data();
        data.writeString("From", "doMove");
        data.writeInt("myself", myself);
        data.writeString("move", xy);
        tcp.sendData(data);
    }
    
    public void doMoveReturn(Data d) {
        canPlay(false);
    }
    
    public void finished(boolean hasWon) {
        if (hasWon) {
            grid.message("You won!");
        } else {
            grid.message("You didn't win!");
        }
        // Sleep, but how? Thred.sleep doesn't work
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            System.err.println(e);
        }
         
        this.canPlay(true);
    }
    
}
