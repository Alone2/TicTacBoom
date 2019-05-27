
package ch.blobber.tictacboom;

import ch.jeda.Data;
import ch.blobber.tictacboom.Grid;

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
        this.grid = new Grid(tac.view);
        this.tac = tac;
        this.connect();
    }
    
    
    public void connect() {
        String URL = grid.askForServer();
        tcp = new TcpClass(URL, this, port);
    }
    
    public void connectReturn(Data d) {
        // Definiert wie das Zeugs aussieht
        Boolean con = d.readBoolean("isConnected");
        if (!con) {
            this.connect();
        }
        myself = d.readInt("myself");
        playerSize = d.readInt("playerSize");
        grid.playerSize = playerSize;
        grid.draw(playerSize + 1);
        canPlay();
    }
    
    public void canPlay() {
        Data data = new Data();
        data.writeString("From", "canPlay");
        tcp.sendData(data);
    }
    
    public void canPlayReturn(Data d) {
        Boolean canPlay = d.readBoolean("canPlay");
        if (canPlay) {
            // long weil max => 8 Züge!
            long[] saveLongArr = d.readLongs("saveLongArr");
            for (int i : new int[playerSize]) {
                
                System.out.println(saveLongArr[i] );
            }
            
        }
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        
    }
    
    public void doMove(int x, int y) {
        // Bewegen
    }
    
    public void doMoveReturn(Data d) {
        
    }
    
    private void hasWon(Data d) {
        
    }
    
}
