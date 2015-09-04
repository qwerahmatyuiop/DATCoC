import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GameFrame extends JFrame implements KeyListener{
	Background gamePanel;

	JPanel chatPanel;
	JPanel chatInput;
	JButton submitChat;
	JTextArea chatArea;
	JTextArea chatDisplay;
	JScrollPane chatbox;
	JScrollPane input;

	Image bgImage;

	String message = "";

	public GameFrame(){
		super("Dat CoC");

		JPanel mainPanel = new JPanel();
		gamePanel = new Background();
		chatPanel = new JPanel();
		chatInput = new JPanel();
		submitChat = new JButton("Submit");
		chatArea = new JTextArea();
		chatDisplay = new JTextArea();
		chatbox = new JScrollPane(chatDisplay);
		input = new JScrollPane(chatArea);

		chatDisplay.setEditable(false);

		submitChat.setPreferredSize(new Dimension(75,50));
		chatArea.addKeyListener(this); //dito lang yung may KeyListener, para pag sa game naka focus yung mouse at napindot enter di magcchat

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(chatPanel, BorderLayout.EAST);

		chatPanel.setPreferredSize(new Dimension(250,100));
		chatPanel.setLayout(new BorderLayout());
		chatPanel.add(chatbox, BorderLayout.CENTER);
		chatPanel.add(chatInput, BorderLayout.SOUTH);

		chatInput.setLayout(new BorderLayout());
		chatInput.add(input, BorderLayout.CENTER);
		chatInput.add(submitChat, BorderLayout.EAST);

		gamePanel.setBackground(new Color(255,0,255));

		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.pack();
		this.setVisible(true);
		
		try{
			bgImage = ImageIO.read(this.getClass().getResource("res/bg.png"));
		}catch(Exception e){}
		gamePanel.setImage(new ImageIcon(bgImage));

		this.runChat();
	}

	public void runChat(){
		try
      	{
   	  	String serverName = "192.168.1.110"; //get IP address of server from first param
		int port = 8000; //get port from second param

		/* Open a ClientSocket and connect to ServerSocket */
		System.out.println("Connecting to " + serverName + " on port " + port);
		while(true){	
			try{
				Thread.sleep(100);
			}catch(Exception e){}
			Socket client = new Socket(serverName, port);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			
			//Send data to server, dito sinesend yung message sa server bago iupdate yung chat
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(message);
			message = ""; //magiging empty yung message after isend sa server

			//Recieve data sa server, dito inuupdate yung chat
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			chatDisplay.setText(""); //clear yung dinidisplay sa chat
			chatDisplay.setText(in.readUTF()); //tapos idisplay yung updated na chat
			client.close();
	    }
	      
      }catch(IOException e)
      {
         //e.printStackTrace();
      	System.out.println("Cannot find Server");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
      }
	}

	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !(chatArea.getText().matches("^\\s*$"))){ //upon enter, chats
			message = chatArea.getText(); //everytime mageenter ng chat, magkakavalue yung 'message' since yun yung pinapasa sa server
			chatArea.setText("");		
		}
	}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}