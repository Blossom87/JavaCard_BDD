package fr.afpa.serializers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.afpa.models.Contact;

public class Deserializer {

    public Contact load() throws IOException {

        Contact deserializedContact = null;

        try {
            FileInputStream fileInputStream = new FileInputStream("contact.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            deserializedContact = (Contact) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception exception) {
            System.out.println("Error Deserializer" + exception.getMessage());
        }
        return deserializedContact;
    }

    @SuppressWarnings("unchecked")
    public List<Contact> loadList() {

        ArrayList<Contact> deserializedContact = null;

        try {
            FileInputStream fileInputStream = new FileInputStream("contacts.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            deserializedContact = (ArrayList<Contact>) objectInputStream.readObject();

            objectInputStream.close();
        } catch (Exception exception) {
            System.out.println("Error Deserializer: " + exception.getMessage());
        }
        return deserializedContact;
    }
}
