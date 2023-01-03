package com.example.gestion_college;
import java.util.Properties;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangementController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Etudiant> addStudents_tableView;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_studentNum;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_classe;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_niv;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_firstName;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_lastName;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_gender;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_birth;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_massar;

    @FXML
    private TextField addStudents_ID;
    @FXML
    private TextField addStudents_nom;
    @FXML
    private TextField addStudents_prenom;
    @FXML
    private TextField addStudents_classe;
    @FXML
    private TextField addStudents_massar;

    @FXML
    private TextArea addStudents_just;


    @FXML
    private DatePicker addStudents_dateNaiss;

    @FXML
    private ComboBox<String> addStudents_niv;
    @FXML
    private ComboBox<String> addStudents_sexe;


    @FXML
    Button Xbtn;
    @FXML
    Button Mbtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;




    public void addStudentsClear() {
        addStudents_prenom.setText("");
        addStudents_nom.setText("");
        addStudents_classe.setText("");
        addStudents_massar.setText("");
        addStudents_just.setText("");


    }

    public void sendEmail(String to, String subject, String body) throws AddressException, MessagingException {
        // Set the email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Set the authentication credentials
        final String username = "fabdel437@gmail.com";
        final String password = "";

        // Create a session with the email properties and authentication credentials
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        // Create the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // Send the email
        Transport.send(message);
    }


    public void addStudentsUpdate() throws MessagingException {

        sendEmail("soloraja006@gmail.com", "Demande de Changement pour " + "Etudiant : " +addStudents_nom.getText() +" "
                +addStudents_prenom.getText(), addStudents_just.getText());

    }


    public void exit(ActionEvent e ){
        Stage stage = (Stage) Mbtn.getScene().getWindow();
        stage.close();
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }


}
