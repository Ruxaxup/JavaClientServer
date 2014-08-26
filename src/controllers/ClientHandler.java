package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientHandler {
	//public static final int PORT = 8080;
	private String serverName = "192.168.0.101";
	private Socket echoSocket;
	
	public String send(String message,String IP,int PORT, String name){
		try {
			
			echoSocket = new Socket(IP, PORT);
			System.out.println("Se ha conectado al servidor. ["+IP+"]");
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			out.println(name+"::"+message);			
			String response = in.readLine();
			
			out.close();
			in.close();
			echoSocket.close();
			return response;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
}
