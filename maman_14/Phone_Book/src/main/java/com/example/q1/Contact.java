package com.example.q1;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    public SimpleStringProperty Name;
    public SimpleStringProperty Number;

    public Contact(SimpleStringProperty name, SimpleStringProperty number) {
        this.Name = name;
        this.Number = number;
    }
}
