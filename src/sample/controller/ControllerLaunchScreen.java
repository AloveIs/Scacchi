package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Pietro on 18/10/2016.
 */
public class ControllerLaunchScreen {
	@FXML
	Label titleLaunchScreen;
	@FXML
	VBox vboxStartScreen;

	Scene scene;

	public void initialize(){
		titleLaunchScreen.minWidthProperty().bind(vboxStartScreen.heightProperty());
		titleLaunchScreen.minHeightProperty().bind(vboxStartScreen.widthProperty());

	}

}
