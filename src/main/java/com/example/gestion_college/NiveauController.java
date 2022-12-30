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


    @FXML
    private TextField addProfs_ID1;

    @FXML
    private TextField addProfs_ID11;



    @FXML
    private TableColumn<?, ?> addProfs_col_lastName1;

    @FXML
    private TableColumn<?, ?> addProfs_col_niv;


    @FXML
    private TableColumn<?, ?> addProfs_col_profNum1;

    @FXML
    private ComboBox<String> addStudents_niv;

    @FXML
    private TextField addProfs_nom1;

    @FXML
    private TextField addProfs_nom11;

    @FXML
    private TableView<Classe> addProfs_tableView1;
//    @FXML
//    private ComboBox<?> addStudents_niv;

    @FXML
    private TableView<?> addProfs_tableView11;
    //lister les Classe de la bdd
    public ObservableList<Classe> addClasseListData(){
        ObservableList<Classe> listClasse = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Classe";


        try {
            Connection connect = getConnection();
            Classe profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Classe(result.getInt("id"),
                        result.getString("nom"),
                        result.getString("niveau")
                );

                listClasse.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listClasse;

    }

    private ObservableList<Classe> addClasseListD;

    public void addProfsShowListData1() {
        addClasseListD = addClasseListData();

        addProfs_col_profNum1.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProfs_col_lastName1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addProfs_col_niv.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        addProfs_tableView1.setItems(addClasseListD);

    }

    private String[] nivList = {"1ERE", "2EME", "3EME"};

    public void addStudentsSexeList() {

        List<String> SexeL = new ArrayList<>();


        try {
            connect = getConnection();
            prepare = connect.prepareStatement("select distinct (nom) from niveau");
            result = prepare.executeQuery();
            while (result.next()){
                SexeL.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ObservableList ObList = FXCollections.observableArrayList(SexeL);
        addStudents_niv.setItems(ObList);

    }

    //ajouter un Classe selectionné dans les champs pour faire MAJ
    public void addProfsSelect1() {

        Classe profD = (Classe) addProfs_tableView1.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView1.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addProfs_ID1.setText(String.valueOf(profD.getId()));
        addProfs_nom1.setText(profD.getNom());
        addStudents_niv.setValue(profD.getNiveau());
        //addStudents_niv.getSelectionModel().select(nivList[]);

    }


    //   create table Classe(id int primary key, nom varchar(30),prenom varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Classe varchar(15));
    //insert into Classe values (1,
    public void ajouterClasse() {

        String insertData = "INSERT INTO Classe (id, nom, niveau) VALUES(?,?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addProfs_ID1.getText().isEmpty()
                    || addProfs_nom1.getText().isEmpty()
                    || addStudents_niv.getSelectionModel().getSelectedItem() ==null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Classe WHERE id = '"
                        + addProfs_ID1.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Classe: " + addProfs_ID1.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addProfs_ID1.getText());
                    prepare.setString(2, addProfs_nom1.getText());
                    prepare.setString(3, (String) addStudents_niv.getSelectionModel().getSelectedItem());

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
        addProfs_ID1.setText("");
        addProfs_nom1.setText("");
    }

    public void addProfsUpdate1() {

        String updateData = "UPDATE Classe SET "
                + "id = '" + addProfs_ID1.getText()
                + "', nom = '" + addProfs_nom1.getText()
                + "', niveau = '" +(String) addStudents_niv.getSelectionModel().getSelectedItem()
                + "' WHERE id = '"
                + addProfs_ID1.getText() + "'";

        try {
            connect = getConnection();
            Alert alert;
            if (addProfs_ID1.getText().isEmpty()
                    || addProfs_nom1.getText().isEmpty()
                    || ((String) addStudents_niv.getSelectionModel().getSelectedItem()).isEmpty()
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
                alert.setContentText("etes-vous sure pour modifier " + addProfs_ID1.getText() + "?");
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
        addProfs_col_lastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addProfs_tableView.setItems(addProfsListD);

    }

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
        addProfsShowListData1();
        //addProfsYearList();
        addStudentsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}
