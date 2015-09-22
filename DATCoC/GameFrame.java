import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GameFrame extends JFrame implements KeyListener{
	//Background gamePanel;
	JPanel gamePanel;

	JPanel base;
	JPanel loginPanel;
	JPanel chatPanel;
	JPanel chatInput;

	JButton submitChat;
	JButton createGame;
	JButton joinGame;
	
	JTextField login;
	JTextArea chatArea;
	JTextArea chatDisplay;
	JScrollPane chatbox;
	JScrollPane input;

	CardLayout cards;
	Image bgImage;
		// if it is for connection
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int defaultPort;
	private String defaultHost;


	String message = "";

	String username = "";
	String server = "10.0.5.181";
	int port = 8000;

	GameFrame(String host, int port){
		super("Dat CoC");

		defaultPort = port;
		defaultHost = host;

		JPanel mainPanel = new JPanel();
		//gamePanel = new Background();\
		gamePanel = new JPanel();
		base = new JPanel();
		loginPanel = new JPanel();
		chatPanel = new JPanel();
		chatInput = new JPanel();

		submitChat = new JButton("Submit");
		createGame = new JButton("Create Game");
		joinGame = new JButton("Join Game");

		login = new JTextField();
		chatArea = new JTextArea();
		chatDisplay = new JTextArea();
		chatbox = new JScrollPane(chatDisplay);
		input = new JScrollPane(chatArea);

		cards = new CardLayout();

		chatDisplay.setEditable(false);

		submitChat.setPreferredSize(new Dimension(75,50));
		chatArea.addKeyListener(this); //dito lang yung may KeyListener, para pag sa game naka focus yung mouse at napindot enter di magcchat
		login.addKeyListener(this);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(base, BorderLayout.CENTER);
		mainPanel.add(chatPanel, BorderLayout.EAST);

		base.setLayout(cards);
		base.add(loginPanel, "loginPanel");
		base.add(gamePanel, "gamePanel");

		//loginPanel.setBackground(Color.RED);
		loginPanel.add(login);
		login.setPreferredSize(new Dimension(300,100));

		chatPanel.setPreferredSize(new Dimension(250,100));
		chatPanel.setLayout(new BorderLayout());
		chatPanel.add(chatbox, BorderLayout.CENTER);
		chatPanel.add(chatInput, BorderLayout.SOUTH);

		chatInput.setLayout(new BorderLayout());
		chatInput.add(input, BorderLayout.CENTER);
		chatInput.add(submitChat, BorderLayout.EAST);

		gamePanel.setBackground(new Color(255,0,255));
		gamePanel.add(createGame);
		gamePanel.add(joinGame);

		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.pack();
		this.setVisible(true);
		
		try{
			bgImage = ImageIO.read(this.getClass().getResource("res/bg.png"));
		}catch(Exception e){}
		//gamePanel.setImage(new ImageIcon(bgImage));

		
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
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER && (login.getText().matches("^\\w+$"))){
			cards.show(base, "gamePanel");
			username = login.getText();

			this.connect();
		}
	}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
