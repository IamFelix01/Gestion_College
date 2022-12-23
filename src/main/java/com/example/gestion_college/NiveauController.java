package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class NiveauController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private TextField addProfs_ID;

    @FXML
    private TableColumn<?, ?> addProfs_col_lastName;

    @FXML
    private TableColumn<?, ?> addProfs_col_profNum;

    @FXML
    private TextField addProfs_nom;

    @FXML
    private TableView<Niveau> addProfs_tableView;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les Niveau de la bdd
    public ObservableList<Niveau> addprofListData(){
        ObservableList<Niveau> listNiveau = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Niveau";


        try {
            Connection connect = getConnection();
            Niveau profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Niveau(result.getInt("id"),
                        result.getString("nom")
                );

                listNiveau.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNiveau;

    }

    private ObservableList<Niveau> addProfsListD;

    public void addProfsShowListData() {
        addProfsListD = addprofListData();

        addProfs_col_profNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProfs_col_lastName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addProfs_tableView.setItems(addProfsListD);

    }

    //add les niveaux au list
//    private String[] niveauList = {"1ERE", "2EME", "3EME"};
//
//    public void addProfsYearList() {
//
//        List<String> yearL = new ArrayList<>();
//
//        for (String data : niveauList) {
//            yearL.add(data);
//        }
//
//        ObservableList ObList = FXCollections.observableArrayList(yearL);
//        addProfs_niv.setItems(ObList);
//
//    }
//
//    private String[] SexeList = {"Homme", "Femme"};
//
//    public void addProfsSexeList() {
//
//        List<String> SexeL = new ArrayList<>();
//
//        for (String data : SexeList) {
//            SexeL.add(data);
//        }
//
//        ObservableList ObList = FXCollections.observableArrayList(SexeL);
//        addProfs_sexe.setItems(ObList);
//
//    }


    //ajouter un Niveau selectionné dans les champs pour faire MAJ
    public void addProfsSelect() {

        Niveau profD = (Niveau) addProfs_tableView.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addProfs_ID.setText(String.valueOf(profD.getId()));
        addProfs_nom.setText(profD.getNom());


    }

    //   create table Niveau(id int primary key, nom varchar(30),prenom varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Classe varchar(15));
    //insert into Niveau values (1,
    public void ajouterNiveau() {

        String insertData = "INSERT INTO Niveau (id, nom) VALUES(?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addProfs_ID.getText().isEmpty()
                    || addProfs_nom.getText().isEmpty()
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Niveau WHERE id = '"
                        + addProfs_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Niveau: " + addProfs_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addProfs_ID.getText());
                    prepare.setString(2, addProfs_nom.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    addProfsShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProfsClear() {
        addProfs_ID.setText("");
        addProfs_nom.setText("");
    }

    public void addProfsUpdate() {

        String updateData = "UPDATE Niveau SET "
                + "id = '" + addProfs_ID.getText()
                + "', nom = '" + addProfs_nom.getText()
                + "' WHERE id = '"
                + addProfs_ID.getText() + "'";




        try {
            connect = getConnection();
            Alert alert;
            if (addProfs_ID.getText().isEmpty()
                    || addProfs_nom.getText().isEmpty()
                    ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour modifier " + addProfs_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addProfsShowListData();
                    // TO CLEAR THE FIELDS
                    addProfsClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws Exception {
        try {
            String driver  = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/college";
            String user = "root";
            String pass = "root";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected Now");
            return conn;

        }catch(Exception e) {
            System.out.println("Something is gone wrong error: " + e);
        }
        return null;
    }


    public void exit(ActionEvent e ){
        System.exit(0);
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProfsShowListData();
        //addProfsYearList();
        //addProfsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}
