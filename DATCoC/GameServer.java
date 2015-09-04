import java.net.*;
import java.io.*;

public class GameServer extends Thread
{
   private ServerSocket serverSocket;
   private String chat = "";

   public GameServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port); //insert missing line here for binding a port to a socket
      serverSocket.setSoTimeout(10000000);			// client and server will connect for 10 seconds
   }

   public void run()
   {
      String temp = "";
      boolean connected = true;
      while(connected)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            
            /* Start accepting data from the ServerSocket */
            Socket server = serverSocket.accept(); //insert missing line for accepting connection from client here]

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            
            /* Read data from the ClientSocket */
            DataInputStream in = new DataInputStream(server.getInputStream());
            //System.out.println(in.readUTF());
            temp = in.readUTF();
            if(!temp.equals("")) chat += temp;
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
           
            /* Send data to the ClientSocket */
            //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            out.writeUTF(chat);
            System.out.println(chat);
            server.close();
           // connected = false;
            System.out.println("Server ended the connection to "+ server.getRemoteSocketAddress());
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            //e.printStackTrace();
            System.out.println("Usage: java GameServer <port no.>");
            break;
         }
      } 
   }
   public static void main(String [] args)
   {
      
      try
      {
         int port = Integer.parseInt(args[0]);
         Thread t = new GameServer(port);
         t.start();
      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Usage: java GameServer <port no.>");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GameServer <port no.> ");
      }
   }
}