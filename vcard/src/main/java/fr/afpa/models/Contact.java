package fr.afpa.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty surName;
    private StringProperty gender;
    private LocalDate birthDate;
    private StringProperty adress;
    private StringProperty zipCode;
    private StringProperty personalPhone;
    private StringProperty professionalPhone;
    private StringProperty mail;
    private StringProperty gitLinks;

    public Contact(String firstName, String lastName, String surName, String gender, LocalDate birthDate, String adress,
            String zipCode, String personalPhone, String professionalPhone, String mail, String gitLinks) {
        if (lastName == null || firstName == null || adress == null || personalPhone == null || mail == null) {
            throw new IllegalArgumentException("Required fields cannot be null");
        }
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.surName = new SimpleStringProperty(surName);
        this.birthDate = birthDate;
        this.gender = new SimpleStringProperty(gender);
        this.adress = new SimpleStringProperty(adress);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.personalPhone = new SimpleStringProperty(personalPhone);
        this.professionalPhone = new SimpleStringProperty(professionalPhone);
        this.mail = new SimpleStringProperty(mail);
        this.gitLinks = new SimpleStringProperty(gitLinks);
    }

    public Contact() {
    };

    public StringProperty getFirstName() {
        return firstName;
    }

    public void setFirstName(StringProperty firstName) {
        this.firstName = firstName;
    }

    public StringProperty getLastName() {
        return lastName;
    }

    public void setLastName(StringProperty lastName) {
        this.lastName = lastName;
    }

    public StringProperty getSurname() {
        return surName;
    }

    public void setSurname(StringProperty surName) {
        this.surName = surName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public StringProperty getGender() {
        return gender;
    }

    public void setGender(StringProperty gender) {
        if(gender.get().equals("Man") || gender.get().equals("Woman") || gender.get().equals("Other")) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Unexpected gender.");
        }
        
    }

    public StringProperty getAddress() {
        return adress;
    }

    public void setAddress(StringProperty adress) {
        this.adress = adress;
    }

    public StringProperty getZipCode() {
        return zipCode;
    }

    public void setZipCode(StringProperty zipCode) {
        this.zipCode = zipCode;
    }

    public StringProperty getPersonalPhone() {
        return personalPhone;
    }

    public void setPersonalPhone(StringProperty personalPhone) {
        this.personalPhone = personalPhone;
    }

    public StringProperty getProfessionalPhone() {
        return professionalPhone;
    }

    public void setProfessionalPhone(StringProperty professionalPhone) {
        this.professionalPhone = professionalPhone;
    }

    public StringProperty getMail() {
        return mail;
    }

    public void setMail(String mail) {

        if (!mail.isEmpty()) {
            if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", mail)) {
                throw new IllegalArgumentException("Wrong mail format.");
            }
        }
        this.mail.set(mail);
    }

    public StringProperty getGitLinks() {
        return gitLinks;
    }

    public void setGitLinks(String gitHub) {

        if (!gitHub.isEmpty()) {
            if (!Pattern.matches("^https?://github.com/([A-Za-z0-9._-]+)$", gitHub)) {
                throw new IllegalArgumentException("Wrong link format.");
            }
        }
        this.gitLinks.set(gitHub);
    }

    /**
     * Méthode appelée lors de la sérialisation binaire (cf. code situé dans la
     * classe "Serializer")
     * 
     * @param out Le stream "Object" de sortie
     * @throws IOException Une exception jetée lors d'un erreur lors de l'écriture
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.firstName.getValue());
        out.writeObject(this.lastName.getValue());
        out.writeObject(this.surName.getValue());
        out.writeObject(this.gender.getValue());
        out.writeObject(this.birthDate);
        out.writeObject(this.adress.getValue());
        out.writeObject(this.zipCode.getValue());
        out.writeObject(this.personalPhone.getValue());
        out.writeObject(this.professionalPhone.getValue());
        out.writeObject(this.mail.getValue());
        out.writeObject(this.gitLinks.getValue());
    }

    /**
     * Méthode appelée lors de la désérilaisation d'un objet de la classe "Contact".
     * 
     * @param in Le stream "Object" de lecture de l'objet
     * @throws IOException            Exception jetée lors d'un problème de lecture
     *                                (fichier innaccessible, mauvais formattage du
     *                                contenu du fichier...)
     * @throws ClassNotFoundException Exception jetée lors d'une transformation
     *                                d'Object en un autre type
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.firstName = new SimpleStringProperty((String) in.readObject());
        this.lastName = new SimpleStringProperty((String) in.readObject());
        this.surName = new SimpleStringProperty((String) in.readObject());
        this.gender = new SimpleStringProperty((String) in.readObject());
        this.birthDate = (LocalDate) in.readObject();
        this.adress = new SimpleStringProperty((String) in.readObject());
        this.zipCode = new SimpleStringProperty((String) in.readObject());
        this.personalPhone = new SimpleStringProperty((String) in.readObject());
        this.professionalPhone = new SimpleStringProperty((String) in.readObject());
        this.mail = new SimpleStringProperty((String) in.readObject());
        this.gitLinks = new SimpleStringProperty((String) in.readObject());
    }

    @Override
    public String toString() {
        return "\nContact [id=" + id + ", lastName=" + lastName.get() + ", firstName=" + firstName.get() + ", pseudo=" + surName.get() + "]";
}
}