package ch.blobber.tictacboom;

import ch.jeda.Connection;
import ch.jeda.Data;
import ch.jeda.TcpConnection;
import ch.jeda.TcpServer;
import ch.jeda.event.ConnectionAcceptedListener;
import ch.jeda.event.ConnectionEvent;
import ch.jeda.event.MessageEvent;
import ch.jeda.event.MessageReceivedListener;

import java.net.InetAddress;

/**
 *
 * @author alain.sinzig
 */
public class TcpClass implements MessageReceivedListener
{

    TcpConnection c;
    Client client;
    
    public TcpClass(String ip, Client client, int port)
    {
        c = new TcpConnection();
        this.client = client;
        
        c.open(ip, port);
                
        if (c.isOpen()) {
            System.out.println("Tries to connect..");
            Data data = new Data();
            data.writeString("From", "isHere");
        
            c.sendData(data);
        } else {
            System.err.println("Error: Connection cannot be established");
        }

    }
    
    public void sendData(Data msg) {
        c.sendData(msg);
    }

    @Override
    public void onMessageReceived(MessageEvent me)
    {
        Data d = me.getData();

        String from = d.readString("From");
        if ("isHere".equals(from))
            client.connectReturn(d);
        if ("canPlay".equals(from))
            client.canPlayReturn(d);
        if ("doMove".equals(from))
            client.doMoveReturn(d);
           
    }

}
