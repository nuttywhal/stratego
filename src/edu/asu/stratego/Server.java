package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Stratego Server creates a socket and listens for connections from every 
 * two players to form a game session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 */
public class Server {
    
    private static ServerSocket serverSocket = null;
    private static int sessionNumber = 1;
    
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(4212);
            System.out.println("Server started @ " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket playerOne = serverSocket.accept();
                System.out.println("Session " + sessionNumber + ": Player 1 has joined the session");
                
                Socket playerTwo = serverSocket.accept();
                System.out.println("Session " + sessionNumber + ": Player 2 has joined the session");
                
                // TODO Implement the ServerGameManager.
                // Thread session = new ServerGameManager(playerOne, playerTwo,sessionNumber++);
                // session.setDaemon(true);
                // session.start();
            }
        }
        catch (IOException e) {
            // TODO Handle this error somehow...
            e.printStackTrace();
        }
    }
}