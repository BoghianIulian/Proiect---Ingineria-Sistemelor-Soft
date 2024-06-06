package com.example.issapp;

import Domain.Cont;
import Domain.Loc;
import Domain.Rezervare;
import Repository.DuplicateObjectException;
import Repository.RepositoryException;
import Service.ServiceLoc;
import Service.ServiceRezervare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpecController {

    public TableView<Loc> table = new TableView<>();

    public TableColumn<Loc , Integer> rand = new TableColumn<>("rand");
    public TableColumn<Loc ,Integer> numar = new TableColumn<>("numar");
    public TableColumn<Loc,Float> pret = new TableColumn<>("pret");
    public TableView<Loc> table_rez = new TableView<>();
    public TableColumn<Loc,Integer> rand_rez=new TableColumn<>("rand");
    public TableColumn<Loc,Integer> numar_rez = new TableColumn<>("numar");
    public TableColumn<Loc,Float> pret_rez = new TableColumn<>("pret");
    public TextField nrTxt;
    public TextField randTxt;
    public TextField userTxt;

    ObservableList<Loc> seats = FXCollections.observableArrayList();

    ObservableList<Loc> rez_seats = FXCollections.observableArrayList();
    @FXML
    Button logoutBut;

    ServiceRezervare rez = new ServiceRezervare();
    ServiceLoc loc = new ServiceLoc();
    @FXML
    void handleLogout() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stg = (Stage) logoutBut.getScene().getWindow();
            stg.getScene().setRoot(root);
        }

    public void seatsView(){
        rand.setCellValueFactory(new PropertyValueFactory<Loc,Integer>("rand"));
        numar.setCellValueFactory(new PropertyValueFactory<Loc,Integer>("numar"));
        pret.setCellValueFactory(new PropertyValueFactory<Loc,Float>("pret"));
    }

    public void seatsView2(){
        rand_rez.setCellValueFactory(new PropertyValueFactory<Loc,Integer>("rand"));
        numar_rez.setCellValueFactory(new PropertyValueFactory<Loc,Integer>("numar"));
        pret_rez.setCellValueFactory(new PropertyValueFactory<Loc,Float>("pret"));
    }

    public void addSeat(){
        try {

            Rezervare r1 = rez.getById(userTxt.getText());
            ArrayList<Loc> lista = new ArrayList<>();
            String s = randTxt.getText() + "," + nrTxt.getText();
            Loc l = new Loc(s, 20, false);
            for(Loc loc:r1.getLista())
                lista.add(loc);
            lista.add(l);
            rez.updateRez(userTxt.getText(),lista);
            Loc l2 = loc.getById(s);
            loc.updateStare(s);
        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,"loc adaugat");
            hello.show();
        }

    }

    public void removeSeat(){
        String s = randTxt.getText() +","+nrTxt.getText();
        Loc l = new Loc(s,20,true);
        seats.add(l);
        l.setStare(false);
        rez_seats.remove(l);
    }

    @FXML
    public void viewSeats(ActionEvent actionEvent)
    {
        try {
            table_rez.refresh();
            rez_seats.clear();
            seatsView2();
            Rezervare r1 = rez.getById(userTxt.getText());
            for(Loc l:r1.getLista())
                rez_seats.add(l);
            table_rez.refresh();
            table_rez.setItems(rez_seats);
        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,"this account doesn't exist");
            hello.show();
        }

    }

    @FXML
    public void viewRez(ActionEvent actionEvent)
    {
        seatsView();
        try {
            seats.clear();
            table.refresh();
            Collection<Loc> locuri =loc.getAllT();
            for(Loc l:locuri){
                if(l.isStare())
                    seats.add(l);
            }
            table.setItems(seats);
        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,e.getMessage());
            hello.show();
        }
    }

    public void createRez() {
        try {
            String id = userTxt.getText();
            ArrayList<Loc> lista = new ArrayList<>();
            rez.addRez(id, lista);
        }catch (DuplicateObjectException e) {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }
    }
}

