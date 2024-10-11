package fr.afpa.serializers;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

import fr.afpa.models.Contact;

public class VCardSerializer {

    /**
     * Méthode de sérialisation vCard d'un objet de la classe "Contact"
     * 
     * @param contact
     */
    public String serialize(Contact contact) {

        /// 1 construire la chaîne de caractères correspondant au contenu vCard
        String vCardContent = "BEGIN:VCARD\nVERSION:4.0";

        // Construction du nom
        vCardContent = vCardContent + "N:" + contact.getLastName().get() + ";" + contact.getFirstName().get() + ";"
                + contact.getGender().get() + "\n";

        // Construction du "full name"
        vCardContent = vCardContent + "FN:" + contact.getFirstName().get() + " " + contact.getLastName().get() + "\n";
        vCardContent = vCardContent + "ADR:" + contact.getAddress().get() + ";" + contact.getZipCode().get() + "\n";
        vCardContent = vCardContent + "EMAIL:" + contact.getMail().get() + "\n";
        vCardContent = vCardContent + "TEL:" + contact.getPersonalPhone().get() + ";"
                + contact.getProfessionalPhone().get() + "\n";
        vCardContent = vCardContent + "ORG:" + contact.getGitLinks().get() + "\n";
        vCardContent = vCardContent + "END:VCARD" + "\n";
        vCardContent = vCardContent + "\n";

        return vCardContent;
    }

    // Instanciation de vCardContent vide, utilisation de List afin récuperer les informations de contact en String.
    public void exportMultipleContacts(List<Contact> contacts) {
        String vCardContent = "";
        for (Contact contact : contacts) {
            vCardContent += this.serialize(contact);
        }

        // 2 écrire cette chaîne dans un fichier
        File file = new File("contacts" + LocalDate.now().toString() + ".vcard");
        createNewFileVCard(file, vCardContent);
    }

    public void exportSingleContact(Contact contact) {
        String vCardContent = this.serialize(contact);

        // 2 écrire cette chaîne dans un fichier
        File file = new File(contact.getFirstName().get() + contact.getLastName().get() + ".vcard");
        createNewFileVCard(file, vCardContent);
    }

    public void createNewFileVCard(File vCardFile, String vCardContent) {

        FileWriter fileWriter = null;
        try {
            // instanciation du FileWriter
            fileWriter = new FileWriter(vCardFile);

            // écriture de la chaîne de caractères
            fileWriter.write(vCardContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}