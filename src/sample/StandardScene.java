package sample;

import javafx.scene.Scene;

public abstract class StandardScene {
    protected Scene scene;

    public StandardScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }
}
