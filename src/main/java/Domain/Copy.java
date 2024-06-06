package Domain;

import Repository.RepositoryException;
import Service.ServiceLoc;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class Copy {


    public Button backButton;
    public Button release;
    ServiceLoc serviceLoc = new ServiceLoc();

    public void start(){

    }

    GridPane gridPane = new GridPane();


    @FXML
    void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();
        Stage stg = (Stage) backButton.getScene().getWindow();
        stg.getScene().setRoot(root);

    }

    public void releaseSeats(ActionEvent actionEvent) {
        try {
            Collection<Loc> seats = serviceLoc.getAllT();
            for(Loc l : seats)
            {
                l.setStare(true);
                setButtonColor(getButtonByLoc(l.getNumar()),true);

            }
        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,e.getMessage());
            hello.show();}
    }

    private Button getButtonByLoc(int numarLoc) {
            for (int i = 0; i < gridPane.getChildren().size(); i++) {
                if (GridPane.getRowIndex(gridPane.getChildren().get(i)) != null &&
                        GridPane.getColumnIndex(gridPane.getChildren().get(i)) != null &&
                        GridPane.getRowIndex(gridPane.getChildren().get(i)) * 10 +
                                GridPane.getColumnIndex(gridPane.getChildren().get(i)) + 1 == numarLoc) {
                    return (Button) gridPane.getChildren().get(i);
                }
            }
            return null;
            }


    private void setButtonColor(Button button,boolean stare) {
        if(!stare)
        {
            button.setStyle("-fx-background-color: #FF0000;");
        }
        else button.setStyle("-fx-background-color: #00FF00;");
    }
}
