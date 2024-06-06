package com.example.issapp;

import Domain.Cont;
import Repository.DuplicateObjectException;
import Repository.RepositoryException;
import Service.ServiceCont;
import Service.ServiceUtilizator;
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

public class AdminController {
    public Button addAcc;
    public RadioButton adminRadButton;
    public RadioButton specRadButton;
    public TextField userTxt;
    public TextField psswdText;
    public TextField cnpText;
    public Button seatsButon;
    ServiceCont serviceCont = new ServiceCont();
    public TableView<Cont> table = new TableView<>();
    public TableColumn<Cont,String> user = new TableColumn<>("username");
    public TableColumn<Cont,String> psswd=new TableColumn<>("password");
    public TableColumn<Cont,String> cnp = new TableColumn<>("cnp");


    @FXML
    Button logoutBut;
    ObservableList<Cont> accounts = FXCollections.observableArrayList();
    ServiceUtilizator serviceUtilizator = new ServiceUtilizator();

    @FXML
    void handleLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage stg = (Stage) logoutBut.getScene().getWindow();
        stg.getScene().setRoot(root);

    }

    public void coloumnView(){
        user.setCellValueFactory(new PropertyValueFactory<Cont,String>("id"));
        psswd.setCellValueFactory(new PropertyValueFactory<Cont,String>("parola"));
        cnp.setCellValueFactory(new PropertyValueFactory<Cont,String>("cnp"));

    }
    @FXML
    public void viewAccounts(ActionEvent actionEvent)
    {
        coloumnView();
        try {
            accounts.clear();
            table.refresh();
            Collection<Cont> conturi = serviceCont.getAllT();
            for(Cont c:conturi){
                accounts.add(c);
            }
            table.setItems(accounts);


        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,e.getMessage());
            hello.show();
        }
    }

    public void addAccount(ActionEvent actionEvent) {
        try
        {
            boolean isAdmin;
            if(adminRadButton.isSelected())
                isAdmin=true;
            else
                isAdmin=false;
            String username = userTxt.getText();
            String password = psswdText.getText();
            String cnp = cnpText.getText();
            serviceCont.addCont(username,password,cnp);
            serviceUtilizator.addUtilizator(cnp,username,isAdmin);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congrats");
            alert.setHeaderText("Registration");
            alert.setContentText("The user was registered !");
            alert.showAndWait();


        }catch (DuplicateObjectException e) {
            Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hello.show();
        }

    }

    public void deleteAcc()
    {
        try {
            String username = userTxt.getText();
            serviceUtilizator.removeUtilizator(serviceCont.getById(username).getCnp());
            serviceCont.removeCont(username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congrats");
            alert.setHeaderText("Registration");
            alert.setContentText("The user was deleted !");
            alert.showAndWait();

        }catch (RepositoryException e){
            Alert hello = new Alert(Alert.AlertType.ERROR,"This accoun was deleted");
            hello.show();
        }
    }

    public void updateAcc()
    {
        //fac diseara oleacaaaa

    }

    public void seatsManagement(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seats1.fxml"));
        Parent root = loader.load();
        Stage stg = (Stage) logoutBut.getScene().getWindow();
        stg.getScene().setRoot(root);
    }
}
