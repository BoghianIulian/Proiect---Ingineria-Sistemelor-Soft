package com.example.issapp;

import Domain.Loc;
import Repository.RepositoryException;
import Service.ServiceLoc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class SeatsController  {

    public Button button1;

    private Map<Integer,Button> buttonMap =new HashMap<>();

    public Button backButton;
    public Button release;
    ServiceLoc serviceLoc = new ServiceLoc();


    @FXML
    GridPane gridPane = new GridPane();

    public void addButton(int id,Button but){
        buttonMap.put(id,but);
    }

    public void sortlist(List<Loc> lista)
    {
        for(int i=0;i<lista.size()-1;i++)
            for(int j=i;j<lista.size();j++)
                if(lista.get(i).getNumar()>lista.get(j).getNumar())
                {
                    Loc aux=new Loc(lista.get(i).getId(),lista.get(i).getPret(),lista.get(i).isStare());
                    lista.set(i,lista.get(j));
                    lista.set(j,aux);
                }
    }


    public void initialize() {
        int randuri = 5;
        int locuriPeRand = 5;
        gridPane.setHgap(10); // Spațiu orizontal între butoane
        gridPane.setVgap(10); // Spațiu vertical între butoane
        try{

        Collection<Loc> seats = serviceLoc.getAllT();
        List<Loc> listaLocuri = new ArrayList<>(seats);
        sortlist(listaLocuri);




        for (int i = 0; i < randuri; i++) {
            // Adaugă locurile din stânga
            for (int j = 0; j < locuriPeRand; j++) {
                int numarLoc = i*10 +j+1;
                Loc locst = listaLocuri.get(numarLoc-1);
                Button button = new Button("Loc " + numarLoc);
                button.setStyle("-fx-background-color:" +(locst.isStare() ? "green" : "red"));
                button.setOnAction(event -> toggleButton(locst, button));
                gridPane.add(button, j, i);
                // Adaugă un event handler pentru fiecare buton pentru a schimba culoarea sau alte acțiuni dorite

            }

            // Adaugă calea de acces în mijloc
            Button buttonCaleAcces = new Button("Cale acces");
            gridPane.add(buttonCaleAcces, locuriPeRand, i);

            // Adaugă locurile din dreapta
            for (int j = 0; j < locuriPeRand; j++) {
                int numarLoc = i*10 +j+6;
                System.out.println(listaLocuri.size());
                Loc locst = listaLocuri.get(numarLoc-1);
                Button button = new Button("Loc " + numarLoc);
                button.setStyle("-fx-background-color:" +(locst.isStare() ? "green" : "red"));
                button.setOnAction(event -> toggleButton(locst, button));
                gridPane.add(button, locuriPeRand + 1 + j, i);
                // Adaugă un event handler pentru fiecare buton pentru a schimba culoarea sau alte acțiuni dorite
            }
        }
        }
        catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,e.getMessage());
            hello.show();}
    }


    private void toggleButton(Loc loc, Button button) {
        try {
           serviceLoc.updateStare(loc.getId());
           loc.setStare(!loc.isStare());
            button.setStyle("-fx-background-color: " + (loc.isStare() ? "green" : "red")); // Schimbă culoarea butonului
        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,e.getMessage());
            hello.show();}
    }

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