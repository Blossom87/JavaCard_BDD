package fr.afpa.serializers;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import fr.afpa.models.Contact;

public class Serializer {

    public void save(Contact contact) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("contact.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(contact);
            objectOutputStream.close();
        } catch (Exception exception) {
            System.out.println("Error Serializer" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void save(List<Contact> contacts) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("contacts.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(contacts);
            objectOutputStream.close();
        } catch (Exception exception) {
            System.out.println("Error Serializers" + exception.getMessage());
            exception.printStackTrace();
        }
    }
}