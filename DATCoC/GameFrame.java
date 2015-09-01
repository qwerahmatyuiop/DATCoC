import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;


public class GameFrame extends JFrame {
	JPanel gamePanel;
	JPanel chatPanel;

	public GameFrame(){
		super("Dat CoC");

		JPanel mainPanel = new JPanel();
		gamePanel = new JPanel();
		chatPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(chatPanel, BorderLayout.EAST);

		chatPanel.setPreferredSize(new Dimension(250,100));

		gamePanel.setBackground(new Color(255,00,255));
		chatPanel.setBackground(Color.BLUE);

		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.pack();
		this.setVisible(true);
		
	}

}