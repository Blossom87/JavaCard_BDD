package fr.afpa.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import fr.afpa.App;
import fr.afpa.models.Contact;
import fr.afpa.models.ContactDAO;
import fr.afpa.serializers.Deserializer;
import fr.afpa.serializers.JSonSerializer;
import fr.afpa.serializers.Serializer;
import fr.afpa.serializers.VCardSerializer;

public class MainWindowController {

    @FXML
    private Button exportSelectionButton; // Assurez-vous que le fx:id correspond dans le fichier FXML

    @FXML
    private Button newContact;

    @FXML
    private Button exportAllButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button changeButton;

    @FXML
    private TableView<Contact> contactTableView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldSurnameField;

    @FXML
    private ChoiceBox<String> genderBox;

    private String[] genders = { "Man", "Woman", "Other" };

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField textFieldAdressField;

    @FXML
    private TextField textFieldZipCodeField;

    @FXML
    private TextField textFieldPersonalPhoneField;

    @FXML
    private TextField textFieldProfessionalPhoneField;

    @FXML
    private TextField textFieldMail;

    @FXML
    private TextField textFieldGitField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Contact> tableView2C;

    @FXML
    private TableColumn<Contact, String> tableCLName;

    @FXML
    private TableColumn<Contact, String> tableCFName;

    @FXML
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> exportTypesBox;

    @FXML
    private Label labelErrorMSG;

    ContactDAO contactDAO = new ContactDAO();

    @FXML
    public void initialize() {

        // Deserializer : Utilisation de ObservableList contact en List afin de charger
        // la liste serializer et de rendre les données lisibles dans le Main
        // controller.
        // La Deserialisation ne s'effectue QUE au lancement de l'application.

        /* Deserializer deserializer = new Deserializer(); */
        List<Contact> deserializedContacts = contactDAO.findAll();

        if (deserializedContacts == null) {
            System.err.println("Pas de contacts à charger.");
        } else {
            contacts.addAll(deserializedContacts);
        }

        ObservableList<String> exportTypes = FXCollections.observableArrayList();
        exportTypes.add("vCard");
        exportTypes.add("JSON");
        exportTypesBox.setItems(exportTypes);
        exportTypesBox.getSelectionModel().select(0);

        tableView2C.setItems(contacts);
        tableCLName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        tableCFName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        genderBox.getItems().addAll(genders);
        deleteButton.setVisible(false);
        changeButton.setVisible(false);
        exportTypesBox.setVisible(false);
        exportAllButton.setVisible(false);
        exportSelectionButton.setVisible(false);
        genderBox.setValue("Select your gender.");

        // FilteredList : Filtre le contenu d'une observable list par prédiction. Met à
        // jour en temps réel l'observable list avec le listener lié au searchField.
        FilteredList<Contact> filteredList = new FilteredList<>(contacts, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(contacts -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (contacts.getLastName().getValue().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (contacts.getFirstName().getValue().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Contact> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView2C.comparatorProperty());
        tableView2C.setItems(sortedList);
    }

    @FXML
    public void tableViewClicked(MouseEvent clickEvent) {

        // Methode tableViewClicked -> Declenchement sur un clique utilisateur dans le
        // tableau(tableview).
        // Utilise un objet de la classe TableView dans lequel on récupére un objet de
        // la classe SelectionModel détenant les informations de la
        // ou les lignes selectionnées.
        // getSelectedItem (methode de SelectionModel) recupere un objet de la classe
        // contact selectionné
        Contact selectedContact = tableView2C.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            deleteButton.setVisible(true);
            changeButton.setVisible(true);
            exportTypesBox.setVisible(true);
            exportAllButton.setVisible(true);
            exportSelectionButton.setVisible(true);
            textFieldFirstName.setText(selectedContact.getFirstName().getValue());
            textFieldLastName.setText(selectedContact.getLastName().getValue());
            textFieldSurnameField.setText(selectedContact.getSurname().getValue());
            dateOfBirthPicker.setValue(selectedContact.getBirthDate());

            if (selectedContact.getGender().getValue().equals("Man")) {
                genderBox.getSelectionModel().select(0);
            }

            textFieldAdressField.setText(selectedContact.getAddress().getValue());
            textFieldZipCodeField.setText(String.valueOf(selectedContact.getZipCode().getValue()));
            textFieldPersonalPhoneField.setText(String.valueOf(selectedContact.getPersonalPhone().getValue()));
            textFieldProfessionalPhoneField.setText(String.valueOf(selectedContact.getProfessionalPhone().getValue()));
            textFieldMail.setText(String.valueOf(selectedContact.getMail().getValue()));
            textFieldGitField.setText(String.valueOf(selectedContact.getGitLinks().getValue()));
        }
    }

    @FXML
    private void handleChangeContact() {
        // Récupérer le contact sélectionné
        Contact selectedContact = tableView2C.getSelectionModel().getSelectedItem();

        try {
            if (selectedContact != null) {
                // Mettre à jour les informations du contact
                selectedContact.setLastName(new SimpleStringProperty(textFieldLastName.getText()));
                selectedContact.setFirstName(new SimpleStringProperty(textFieldFirstName.getText()));
                selectedContact.setSurname(new SimpleStringProperty(textFieldSurnameField.getText()));
                selectedContact.setGender(new SimpleStringProperty(genderBox.getValue()));
                selectedContact.setBirthDate(dateOfBirthPicker.getValue());
                selectedContact.setAddress(new SimpleStringProperty(textFieldAdressField.getText()));
                selectedContact.setZipCode(new SimpleStringProperty(textFieldZipCodeField.getText()));
                selectedContact.setPersonalPhone(new SimpleStringProperty(textFieldPersonalPhoneField.getText()));
                selectedContact
                        .setProfessionalPhone(new SimpleStringProperty(textFieldProfessionalPhoneField.getText()));
                selectedContact.setMail(textFieldMail.getText());
                selectedContact.setGitLinks(textFieldGitField.getText());

                // Rafraîchir la TableView pour refléter les modifications
                tableView2C.refresh();

                // Afficher un message de confirmation
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le contact a été modifié avec succès.");
                alert.showAndWait();
            } else {
                // Alerter l'utilisateur si aucun contact n'est sélectionné
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Pas de sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un contact à modifier.");
                alert.showAndWait();
            }

            textFieldLastName.clear();
            textFieldFirstName.clear();
            textFieldSurnameField.clear();
            genderBox.getSelectionModel().clearSelection();
            dateOfBirthPicker.setValue(null);
            textFieldAdressField.clear();
            textFieldZipCodeField.clear();
            textFieldPersonalPhoneField.clear();
            textFieldProfessionalPhoneField.clear();
            textFieldMail.clear();
            textFieldGitField.clear();
            changeButton.setVisible(false);
            deleteButton.setVisible(false);
            exportTypesBox.setVisible(false);
            exportAllButton.setVisible(false);
            exportSelectionButton.setVisible(false);

            contactDAO.update(selectedContact);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    @FXML
    private void handleDeleteContact() {
        // Récupérer le contact sélectionné
        Contact selectedContact = tableView2C.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            // Confirmer la suppression avec l'utilisateur
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected contact?");

            // Afficher l'alerte et attendre la réponse
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer le contact de la TableView
                contacts.remove(selectedContact);
                System.out.println("Contact deleted successfully.");
            }
        } else {
            // Alerter l'utilisateur si aucun contact n'est sélectionné
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a contact to delete.");
            alert.showAndWait();

        }

        Serializer serializer = new Serializer();
        serializer.save(new ArrayList<>(contacts));

        App.deleteGIF();

        textFieldLastName.clear();
        textFieldFirstName.clear();
        textFieldSurnameField.clear();
        genderBox.getSelectionModel().clearSelection();
        dateOfBirthPicker.setValue(null);
        textFieldAdressField.clear();
        textFieldZipCodeField.clear();
        textFieldPersonalPhoneField.clear();
        textFieldProfessionalPhoneField.clear();
        textFieldMail.clear();
        textFieldGitField.clear();
        exportTypesBox.setVisible(false);
        exportAllButton.setVisible(false);
        exportSelectionButton.setVisible(false);

        contactDAO.deleteID(selectedContact);
    }

    @FXML
    private void handleExportAll() {
        // Récupérer les données de la TableView
        List<Contact> contacts = tableView2C.getItems();
        String selectedExportType = exportTypesBox.getSelectionModel().getSelectedItem();

        if (contacts.isEmpty()) {
            System.out.println("no contacts available.");
            return; // Sortir de la méthode si aucune sélection n'est faite
        }

        if (selectedExportType.equals("vCard")) {

            // Apelle de la méthode permettant de serializer tous les contacts de la
            // ObservableList.
            // Ici pour vCard. Après vérification de la combo box de type.
            VCardSerializer serializer = new VCardSerializer();
            serializer.exportMultipleContacts(contacts);

        } else {

            // Apelle de la méthode permettant de serializer tous les contacts de la
            // ObservableList.
            // Ici pour jSon. Après vérification de la combo box de type.
            JSonSerializer serializer = new JSonSerializer();
            serializer.exportMultipleContacts(contacts);
        }

        exportTypesBox.setVisible(false);
        exportAllButton.setVisible(false);
        exportSelectionButton.setVisible(false);
    }

    /**
     * Méthode qui se déclenche lors du clic pour l'export d'une sélection unique ou
     */
    @FXML
    private void handleExportSelection() {
        // Récupérer les données sélectionnées de la TableView

        List<Contact> selectedContacts = tableView2C.getSelectionModel().getSelectedItems();

        // Vérifier s'il y a des éléments sélectionnés
        if (selectedContacts.isEmpty()) {
            System.out.println("no contacts selected.");
            return; // Sortir de la méthode si aucune sélection n'est faite
        }

        // récupération de la valeur sélectionnée dans la ChoiceBox
        String selectedExportType = exportTypesBox.getSelectionModel().getSelectedItem();

        // 2 cas possible : soit vCard, soit JSon
        if (selectedExportType.equals("vCard")) {

            VCardSerializer serializer = new VCardSerializer();
            serializer.exportSingleContact(selectedContacts.get(0));

        } else {

            JSonSerializer serializer = new JSonSerializer();
            serializer.exportSingleContact(selectedContacts.get(0));
        }
    }

    @FXML
    private void handleNewContact() {
        // Récupérer les valeurs des champs
        String lastName = textFieldLastName.getText();
        String firstName = textFieldFirstName.getText();
        String surname = textFieldSurnameField.getText();
        String gender = genderBox.getValue();
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        String address = textFieldAdressField.getText();
        String zipCode = textFieldZipCodeField.getText();
        String personalPhone = textFieldPersonalPhoneField.getText();
        String professionalPhone = textFieldProfessionalPhoneField.getText();
        String email = textFieldMail.getText();
        String git = textFieldGitField.getText();

        // Créer un nouvel objet Contact
        Contact newContact = new Contact(lastName, firstName,
                surname, gender,
                dateOfBirth, address,
                zipCode, personalPhone,
                professionalPhone, email, git);

        // Vérification de l'instanciation des Fields Texts.
        if (newContact.getLastName().getValue().isEmpty() ||
                newContact.getFirstName().getValue().isEmpty() ||
                newContact.getGender().getValue().isEmpty() ||
                newContact.getAddress().getValue().isEmpty() ||
                newContact.getPersonalPhone().getValue().isEmpty() ||
                newContact.getMail().getValue().isEmpty() ||
                newContact.getZipCode().getValue().isEmpty()) {
            throw new IllegalArgumentException("Contact format error.");
        }
        ;

        newContact.setMail(email);
        newContact.setGitLinks(git);

        // Ajouter le nouveau contact à la TableView
        contacts.add(newContact);

        // Ajout à la base de données.
        contactDAO.add(newContact);

        // Serialization de la liste de contact. La serialization effectue une mise à
        // jour à l'ajout d'un nouveau contact.

        Serializer serializer = new Serializer();
        serializer.save(new ArrayList<>(contacts));

        // Réinitialiser les champs du formulaire
        textFieldLastName.clear();
        textFieldFirstName.clear();
        textFieldSurnameField.clear();
        genderBox.getSelectionModel().clearSelection();
        dateOfBirthPicker.setValue(null);
        textFieldAdressField.clear();
        textFieldZipCodeField.clear();
        textFieldPersonalPhoneField.clear();
        textFieldProfessionalPhoneField.clear();
        textFieldMail.clear();
        textFieldGitField.clear();

        // ouverture d'une popup
        App.successGif();
    }
}