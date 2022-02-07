package com.example.q1;

import java.io.*;
import java.util.*;

public class PhoneBook implements Iterator<String> {
    private TreeMap<String, String> contacts;
    private Iterator<String> iterator;

    public PhoneBook(){
        contacts = new TreeMap<String, String>();
        //Collections.sort(listToSort, String.CASE_INSENSITIVE_ORDER);
    }

    public void addContact(String name, String phoneNumber){
        contacts.put(name, phoneNumber);
    }

    public void loadFromFile(String filename) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileReader(filename));
        while (reader.hasNextLine())
        {
            String line = reader.nextLine().trim();
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(" ");
            // System.out.println("name" + name + ". number" + number);
            addContact(parts[0], parts[1]);
        }
    }

    public void saveToFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        for (String name: contacts.keySet()) {
            writer.println(name + " " + getNumber(name));
        }
        writer.close();

    }

    public Set<String> GetNames(){
        return contacts.keySet();
    }

    public String getNumber(String name) {
        return contacts.get(name);
    }

    public void Delete(String name) {
        contacts.remove(name);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        iterator = contacts.keySet().iterator();
        return iterator.next();
    }
}
