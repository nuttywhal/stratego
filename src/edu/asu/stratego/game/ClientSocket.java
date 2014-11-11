package edu.asu.stratego.game;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client socket that connects to the server. The client should only use one 
 * socket to connect to the server.
 */
public final class ClientSocket {
    
    private static Socket socket = null;
    
    /**
     * Prevents an instance of this class from being instantiated.
     */
    private ClientSocket() { /* Intentionally Empty */ }
    
    /**
     * Attempts a connection to the server.
     * 
     * @param serverIP server IP address
     * @param port server port number
     */
    public static void connect(String serverIP, int port) 
            throws UnknownHostException, IOException {
        socket = new Socket(serverIP, port);
    }
    
    /**
     * Returns the one and only instance of the client socket.
     * 
     * @return the Socket used to establish a connection between the client 
     * and the server. The socket may be null.
     */
    public static Socket getInstance() {
        return socket;
    }
}