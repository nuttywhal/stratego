package edu.asu.stratego.gui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The ConnectionStage class, which inherits from the JavaFX Stage class, is a 
 * preset Stage for facilitating easy navigation between scenes in the Client 
 * application.
 */
public class ClientStage extends Stage {
    
    /**
     * Creates a new instance of ClientStage.
     */
    public ClientStage() {
        this.setScene(new Scene(new Pane(), 300, 150));
        this.setTitle("ASU Stratego");
        this.setResizable(false);
        this.show();
    }
    
    /**
     * Switch to the Connection Scene.
     * @see edu.asu.stratego.gui.ConnectionScene
     */
    public void setConnectionScene() {
        this.setScene((new ConnectionScene()).scene);
    }
}