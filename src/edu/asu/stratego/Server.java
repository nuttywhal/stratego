package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and listens for connections from every 
 * two players to form a game session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 * 
 * @see edu.asu.stratego.game.ServerGameManager
 */
public class Server {
    private static int sessionNumber = 1;
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4212);
            System.out.println("Server started @ " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket player1 = serverSocket.accept();
                System.out.println("Session " + sessionNumber + ": Player 1 has joined the session");
                
                Socket player2 = serverSocket.accept();
                System.out.println("Session " + sessionNumber + ": Player 2 has joined the session");
                
                new Thread(new ServerGameManager(player1, player2, sessionNumber++)).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}