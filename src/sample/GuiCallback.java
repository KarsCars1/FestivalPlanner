package sample;

import javafx.scene.Scene;

public interface GuiCallback {
    void setStage(Scene scene);
    void setStage(Scene scene, String Name);
    void closeStage();
}
