package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalleController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private TextField addLignes_ID;

    @FXML
    private TableColumn<?, ?> addLignes_col_idLigne;

    @FXML
    private TableColumn<?, ?> addLignes_col_idSalle;

    @FXML
    private TableColumn<?, ?> addLignes_col_materiel;

    @FXML
    private TextField addLignes_idSalle;

    @FXML
    private TextField addLignes_materiel;

    @FXML
    private TableView<Salle> addProfs_tableView;

    @FXML
    private TableView<Ligne> addProfs_tableView1;

    @FXML
    private TextField addSalles_ID;

    @FXML
    private TableColumn<?, ?> addSalles_col_SalleID;

    @FXML
    private TableColumn<?, ?> addSalles_col_type;

    @FXML
    private TextField addSalles_type;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les Ligne de la bdd
    public ObservableList<Ligne> addLigneListData(){
        ObservableList<Ligne> listLigne = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Ligne";


        try {
            Connection connect = getConnection();
            Ligne profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Ligne(result.getInt("id"),
                        result.getString("materiel"),
                        result.getInt("idSalle")
                );

                listLigne.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLigne;

    }

    private ObservableList<Ligne> addLigneListD;

    public void addProfsShowListData1() {
        addLigneListD = addLigneListData();

        addLignes_col_idLigne.setCellValueFactory(new PropertyValueFactory<>("id"));
        addLignes_col_materiel.setCellValueFactory(new PropertyValueFactory<>("materiel"));
        addLignes_col_idSalle.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        addProfs_tableView1.setItems(addLigneListD);

    }

    //ajouter un Ligne selectionné dans les champs pour faire MAJ
    public void addProfsSelect1() {

        Ligne profD = (Ligne) addProfs_tableView1.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView1.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addLignes_ID.setText(String.valueOf(profD.getId()));
        addLignes_materiel.setText(profD.getMateriel());
        addLignes_idSalle.setText(String.valueOf(profD.getIdSalle()));

    }


    //   create table Ligne(id int primary key, type varchar(30),pretype varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Ligne varchar(15));
    //insert into Ligne values (1,
    public void ajouterLigne() {

        String insertData = "INSERT INTO Ligne (id, materiel, idSalle) VALUES(?,?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addLignes_ID.getText().isEmpty()
                    || addLignes_materiel.getText().isEmpty()
                    || addLignes_idSalle.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Ligne WHERE id = '"
                        + addLignes_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ligne: " + addLignes_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addLignes_ID.getText());
                    prepare.setString(2, addLignes_materiel.getText());
                    prepare.setString(3, addLignes_idSalle.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    addProfsShowListData1();
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProfsClear1() {
        addLignes_ID.setText("");
        addLignes_materiel.setText("");
        addLignes_idSalle.setText("");
    }

    public void addProfsUpdate1() {

        String updateData = "UPDATE Ligne SET "
                + "id = '" + addLignes_ID.getText()
                + "', materiel = '" + addLignes_materiel.getText()
                + "', idSalle = '" + addLignes_idSalle.getText()
                + "' WHERE id = '"
                + addLignes_ID.getText() + "'";

        try {
            connect = getConnection();
            Alert alert;
            if (addLignes_ID.getText().isEmpty()
                    || addLignes_materiel.getText().isEmpty()
                    || addLignes_idSalle.getText().isEmpty()
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
                alert.setContentText("etes-vous sure pour modifier " + addLignes_ID.getText() + "?");
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
                    addProfsShowListData1();
                    // TO CLEAR THE FIELDS
                    addProfsClear1();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lister les Salle de la bdd
    public ObservableList<Salle> addprofListData(){
        ObservableList<Salle> listSalle = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Salle";


        try {
            Connection connect = getConnection();
            Salle profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Salle(result.getInt("id"),
                        result.getString("type")
                );

                listSalle.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSalle;

    }

    private ObservableList<Salle> addProfsListD;

    public void addProfsShowListData() {
        addProfsListD = addprofListData();

        addSalles_col_SalleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addSalles_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProfs_tableView.setItems(addProfsListD);

    }

    //ajouter un Salle selectionné dans les champs pour faire MAJ
    public void addProfsSelect() {

        Salle profD = (Salle) addProfs_tableView.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addSalles_ID.setText(String.valueOf(profD.getId()));
        addSalles_type.setText(profD.getType());

    }

    //   create table Salle(id int primary key, type varchar(30),pretype varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Ligne varchar(15));
    //insert into Salle values (1,
    public void ajouterSalle() {

        String insertData = "INSERT INTO Salle (id, type ) VALUES(?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addSalles_ID.getText().isEmpty()
                    || addSalles_type.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Salle WHERE id = '"
                        + addSalles_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Salle: " + addSalles_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addSalles_ID.getText());
                    prepare.setString(2, addSalles_type.getText());

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
        addSalles_ID.setText("");
        addSalles_type.setText("");
    }

    public void addProfsUpdate() {

        String updateData = "UPDATE Salle SET "
                + "id = '" + addSalles_ID.getText()
                + "', type = '" + addSalles_type.getText()
                + "' WHERE id = '"
                + addSalles_ID.getText() + "'";

        try {
            connect = getConnection();
            Alert alert;
            if (addSalles_ID.getText().isEmpty()
                    || addSalles_type.getText().isEmpty()
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
                alert.setContentText("etes-vous sure pour modifier " + addSalles_ID.getText() + "?");
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
        stage.close();
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProfsShowListData();
        addProfsShowListData1();
        //addProfsYearList();
        //addStudentsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}
