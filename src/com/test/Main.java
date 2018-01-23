package com.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public final class Main extends Application {

    // this queue is used for all communication between the GUI and the GUIUpdater
    static private SynchronizedQueue myQueue;
    static private MediaView RECEIVER_mediaView;
    static Button Butt;
    static HBox mediaBar;
    static MediaPlayer player;

    public static void ButtonClick() {
        String message = "Press Play";
        while (!myQueue.put(message)) {
            Thread.currentThread().yield();
        }
    }
    @Override
    public void start(final Stage stage) {
        // Create and set the Scene.


        // Name and display the Stage.
        stage.setTitle("Hello Media");
        stage.show();
        final MediaView SENDER_mediaView = new MediaView();
        SENDER_mediaView.setFitHeight(400);
        SENDER_mediaView.setFitWidth(300);

        Butt = new Button();


        // Create the media source.
        MediaView mediaView = new MediaView(player);


        Scene scene = new Scene(new Group(), 300, 550);
        stage.setScene(scene);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(300);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

        final Button playButton = new Button("Play");
        mediaBar.getChildren().add(playButton);
        // setBottom(mediaBar);



        // Create the view and add it to the Scene.
        ((Group) scene.getRoot()).getChildren().addAll(mediaView, mediaBar);
        playButton.setOnAction(e -> ButtonClick());

    }



    public static void main(String[] args) {
        // Create the queue that will be used for communication
        // between the GUI thread that reacts to User Interaction and the GUIUpdater that updates the GUI
        myQueue = new SynchronizedQueue();
        //RECEIVER_mediaView = new MediaView();

        player = new MediaPlayer( new Media(Main.class.getResource("bleh.mp4").toExternalForm()));

        // Create and start the GUI updater thread
        GUIUpdater updater = new GUIUpdater(myQueue, player);
        Thread updaterThread = new Thread(updater);
        updaterThread.start();

        //Start the GUI thread

        Application.launch(args);
    }

}