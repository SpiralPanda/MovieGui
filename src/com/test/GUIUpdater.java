package com.test;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class GUIUpdater implements Runnable {
    private SynchronizedQueue originalQueue;
    private MediaPlayer GUImediaplayer;



    GUIUpdater(SynchronizedQueue queue, MediaPlayer mediaplayer) {
        originalQueue = queue;
        GUImediaplayer = mediaplayer;

    }

    public void run() {
        while (!Thread.interrupted()) {
            // Ask queue for a file to open
            String next = originalQueue.get();
            while (next == null) {
                Thread.currentThread().yield();
                next = originalQueue.get();
            }
            // FINALLY I have a file to do something with
            GUImediaplayer.play();
        }
    }

}
