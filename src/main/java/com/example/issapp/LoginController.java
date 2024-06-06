package com.example.issapp;

import Domain.Utilizator;
import Repository.DuplicateObjectException;
import Repository.RepositoryException;
import Service.ServiceCont;
import Service.ServiceUtilizator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;





import java.io.IOException;

public class LoginController {


    Stage mainStage;
    ServiceUtilizator serviceUtil = new ServiceUtilizator();

    ServiceCont serviceCont = new ServiceCont();
    boolean isAdmin;
    //TypeUser typeUser;
    //TypeUser typeUserSignup;
    Utilizator rootUser;
@FXML
    public TextField cnpText;


    @FXML
    TextField loginUsernameText;
    @FXML
    TextField loginPasswordText;
    @FXML
    TextField signupUsernameText;
    @FXML
    TextField signupPasswordText;
    @FXML
    TextField signupConfirmPasswordText;
    @FXML
    Button loginBut;
    @FXML
    Button signupBut;
    @FXML
    RadioButton specLogin;
    @FXML
    RadioButton adminLogin;
    @FXML
    ToggleGroup tgroup = new ToggleGroup();
    @FXML
    RadioButton specSignup;
    @FXML
    RadioButton adminSignup;
    /*

    public void setServices(Stage primaryStage, ServiceUtilizator service){
        this.mainStage = primaryStage;
        this.serviceUtil = service;
    }

    @FXML
    public void initialize(){

    }
*/
    @FXML
    void handleLogin() throws IOException {
        try {
            String username = loginUsernameText.getText();
            String password = loginPasswordText.getText();
            String cnp = serviceCont.getById(username).getCnp();
            Utilizator util = serviceUtil.getById(cnp);
            if (adminLogin.isSelected() ) {
                if(util.isAdmin()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                Parent root = loader.load();
                Stage stg = (Stage) loginBut.getScene().getWindow();
                stg.getScene().setRoot(root);}
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Something went wrong");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("The user in not an admin!");
                    clearLogin();
                }
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("spectator.fxml"));
                Parent root = loader.load();
                Stage stg = (Stage) loginBut.getScene().getWindow();
                stg.getScene().setRoot(root);
            }
        }catch (RepositoryException e){
            clearLogin();
            Alert hello = new Alert(Alert.AlertType.ERROR,"this account doesn't exist");
            hello.show();
        }

    }

    @FXML
    void handleSignup() {
        String username = signupUsernameText.getText();
        String password = signupPasswordText.getText();
        String confirmPassword = signupConfirmPasswordText.getText();
        String cnp = cnpText.getText();
        if (!Validator.verificaParola(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Password must contain at least 10 characters (one uppercase and one digit)!");
            alert.showAndWait();
        } else if (!confirmPassword.equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Password doesn't match !");
            alert.showAndWait();
        } else if (!Validator.verificaCNP(cnp)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("CNP doesn't exist !");
            alert.showAndWait();


        } else if (username.length() < 8) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Username must contain at least 8 characters !");
            alert.showAndWait();


        } else {
            try {
                serviceCont.addCont(username, password, cnp);
                serviceUtil.addUtilizator(cnp,username,false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congrats");
                alert.setHeaderText("Registration");
                alert.setContentText("The user was registered !");
                alert.showAndWait();
                clearSignup();
            } catch (DuplicateObjectException e) {
                Alert hello = new Alert(Alert.AlertType.ERROR, e.getMessage());
                hello.show();
            }
        }
    }

    private void clearLogin(){
        loginUsernameText.clear();
        loginPasswordText.clear();
    }

    private void clearSignup(){
        signupUsernameText.clear();
        signupPasswordText.clear();
        signupConfirmPasswordText.clear();
        cnpText.clear();
    }



    @FXML
    void handleSpectatorRadioLogin(){

    }



    @FXML
    void handleAdminRadoLogin(){

    }
}


