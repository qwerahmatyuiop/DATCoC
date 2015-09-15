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
		// if it is for connection
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int defaultPort;
	private String defaultHost;


	String message = "";

	String username = "pogi";
	String server = "192.168.1.33";
	int port = 8000;

	GameFrame(String host, int port){
		super("Dat CoC");

		defaultPort = port;
		defaultHost = host;

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

		this.connect();
		//this.runChat();
	}
	void connect(){
		client = new Client(server, port, username, this);	
			if(!client.start()) 
				return;
			chatArea.setText("");
			connected = true;
	}
	void append(String str) {
		chatDisplay.append(str);
		chatDisplay.setCaretPosition(chatDisplay.getText().length() - 1);
	}
	void connectionFailed() {
		connected = false;
	}
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !(chatArea.getText().matches("^\\s*$"))){ //upon enter, chats
			message = chatArea.getText(); //everytime mageenter ng chat, magkakavalue yung 'message' since yun yung pinapasa sa server
			chatArea.setText("");	
			if(connected) {
			// just have to send the message
			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, message));				
			chatArea.setText("");
			return;
			}
		}
	}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}