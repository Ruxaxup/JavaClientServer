package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controllers.ClientHandler;

public class ClientGUI extends JFrame implements ActionListener, KeyListener{

	private JButton sendButton;
	private  JTextArea messageArea;
	private JTextArea serverResponse;
	private ClientHandler client;
	private JTextField tfAddress;
	private JTextField tfPort;
	private JTextField tfName;
	
	public ClientGUI(){
		setTitle("Client");
		setPreferredSize(new Dimension(640,480));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		pack();
		
		client = new ClientHandler();
	}
	
	
	private void initComponents() {
		JPanel mainPanel = new JPanel();
		JPanel panelIP, panelMsg;
		panelIP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelMsg = new JPanel(new GridLayout(2,1));
		mainPanel.setLayout(new BorderLayout());
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		
		messageArea = new JTextArea(30,30);
		messageArea.setBorder(new TitledBorder("Message"));
		messageArea.addKeyListener(this);
		
		serverResponse = new JTextArea(30,30);
		serverResponse.setBorder(new TitledBorder(null,"Server Response",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,null,Color.white));
		serverResponse.setEditable(false);
		serverResponse.setBackground(Color.black);
		serverResponse.setForeground(Color.green.brighter());
		serverResponse.setText("Escribe: hola o help\n");
		
		tfAddress = new JTextField("187.176.32.181");
		tfPort = new JTextField("8080");
		tfName = new JTextField("TuNombre");
		
		panelIP.add(new JLabel("IP:"));
		panelIP.add(tfAddress);
		panelIP.add(new JLabel("Port:"));
		panelIP.add(tfPort);
		panelIP.add(new JLabel("Name:"));
		panelIP.add(tfName);

		panelMsg.add(messageArea);
		JScrollPane spResponse = new JScrollPane(serverResponse,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelMsg.add(spResponse);
		
		mainPanel.add(BorderLayout.NORTH,panelIP);
		mainPanel.add(BorderLayout.CENTER,panelMsg);
		mainPanel.add(BorderLayout.SOUTH,sendButton);
		
		add(mainPanel);
	}
	
	private void send(){
		String texto = messageArea.getText();
		messageArea.setText(null);
		System.out.println("Probando: ["+texto+"]");
		try{
			int port = Integer.parseInt(tfPort.getText());
			String response = client.send(texto,tfAddress.getText(),port,tfName.getText());
			if(response != null){
				serverResponse.append(response+"\n");
			}
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Incorrect Port format. Use numbers.","Port Format Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton){
			send();
		}
		
	}
	
	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI();
		clientGUI.setVisible(true);
	}


	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			send();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
