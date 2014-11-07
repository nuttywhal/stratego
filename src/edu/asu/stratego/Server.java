package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and accepts connections from every two 
 * players to form a session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 * 
 * @see edu.asu.stratego.game.ServerGameManager
 */
public class Server {
    private static int sessionNumber = 1;
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(4212);
            System.out.println("Server started @ " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket player1 = serverSocket.accept();
                System.out.println(new Date() + " - Session " + sessionNumber + " - Player 1 " + 
                                   "has joined session from " + player1.getRemoteSocketAddress());
                
                Socket player2 = serverSocket.accept();
                System.out.println(new Date() + " - Session " + sessionNumber + " - Player 2 " + 
                        "has joined session from " + player2.getRemoteSocketAddress());
                
                new Thread(new ServerGameManager(player1, player2, sessionNumber++)).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}