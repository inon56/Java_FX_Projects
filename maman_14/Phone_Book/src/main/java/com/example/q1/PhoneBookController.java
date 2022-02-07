/*
Author Inon Levi
The PhoneBookController controller class responsible for the functionality.
The application is loading text of contact's.
The user can add contact and change the phone number by writing the name
at the according field and write the new desirable phone number.
Also, can search for a phone number by name, and for bring back the whole list press Clear search button.
For deleting a contact select the row you want to delete and press the Remove selected row button.
If you want to save your changes on the file you can do it by pressing the Save button.
 */

package com.example.q1;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;

public class PhoneBookController {
    PhoneBook phoneBook = new PhoneBook();
    private static String filename = "contacts.txt";

    @FXML
    private TableView<Contact> tableView;
    @FXML
    private TableColumn<Contact , String> nameColumn;
    @FXML
    private TableColumn<Contact, String> phoneColumn;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPhone;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClearSearch;
    @FXML
    private TextField textSearchBox;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSave;
    @FXML
    private Label labelMessage;

    public PhoneBookController() {
    }

    @FXML
    public void initialize() {
        labelMessage.setDisable(true);
        labelMessage.setVisible(false);
        tableView.setVisible(true);
        tableView.setEditable(true);
        textName.setPromptText("name");
        textPhone.setPromptText("phone");
        textSearchBox.setPromptText("search");
        nameColumn.setCellValueFactory(sp -> sp.getValue().Name);
        phoneColumn.setCellValueFactory(sp -> sp.getValue().Number);
        loadFromFile();
        Refresh();
    }

    private void loadFromFile() {
        try {
            phoneBook.loadFromFile(filename);
        } catch (FileNotFoundException e) {
            labelMessage.setText(("Couldn't read from file:" + filename + "error:" + e.getMessage() ));
        }
    }

    private void saveToFile() {
        try {
            phoneBook.saveToFile(filename);
        } catch (IOException e) {
            labelMessage.setText(("Couldn't write to file:" + filename + "error:" + e.getMessage()));
        }
    }

    public void Refresh(){
        ObservableList<Contact> observableList = FXCollections.observableArrayList();
        for (String name: phoneBook.GetNames()) {
            observableList.add(
                new Contact(
                        new SimpleStringProperty(name),
                        new SimpleStringProperty(phoneBook.getNumber(name))));
        }
        tableView.setItems(observableList);
    }

    public void showQuery(String name, String number) {
        ObservableList<Contact> observableList = FXCollections.observableArrayList();
        if (number != null) {
            observableList.add(
                    new Contact(
                            new SimpleStringProperty(name),
                            new SimpleStringProperty(number)));
        }
        tableView.setItems(observableList);
    }

    @FXML
    void buttonPressed(ActionEvent event) {
        if (event.getSource() == btnAdd){
            phoneBook.addContact(textName.getText(),textPhone.getText());
            Refresh();
        }
        if (event.getSource() == btnDelete){
            phoneBook.Delete(tableView.getSelectionModel().getSelectedItem().Name.getValue());
            tableView.getSelectionModel().clearSelection(); // remove from the GUI
            Refresh();
        }
        if (event.getSource() == btnClearSearch){
            Refresh();
        }
        if(event.getSource() == btnSearch){
            String name = textSearchBox.getText();
            String number = phoneBook.getNumber(name);
            showQuery(name, number);
        }
        if(event.getSource() == btnSave){
            saveToFile();
        }
    }
}