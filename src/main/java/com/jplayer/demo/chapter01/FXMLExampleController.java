package com.jplayer.demo.chapter01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 * @author Willard
 * @date 2019/9/5
 */
public class FXMLExampleController {
    @FXML
    private Text actiontarget;

    @FXML
    protected void handleSubmitBttonAction(ActionEvent actionEvent){
        actiontarget.setText("Sign in button pressed");
    }
}
