package fr.afpa.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends DAO<Contact> {

    public List<Contact> findAll() {

        ArrayList<Contact> sqlAllContact = new ArrayList<Contact>();

        try {
            Statement stm = connect.createStatement();
            
            ResultSet result = stm.executeQuery("SELECT * FROM contact");
            

            while (result.next()) {
                
                int id = result.getInt("id");
                String lastName = result.getString("last_name");
                String firstName = result.getString("first_name");
                String gender = result.getString("gender");
                LocalDate birthDate = LocalDate.parse(result.getString("birth_date"));
                String pseudo = result.getString("pseudo");
                String adress = result.getString("adress");
                String personalPhoneNumber = result.getString("personal_phone_number");
                String professionnalPhoneNumber = result.getString("professionnal_phone_number");
                String email = result.getString("email");
                String zipcode = result.getString("zipcode");
                String gitLink = result.getString("gitlink");

                Contact contact = new Contact(lastName, firstName, pseudo, gender, birthDate, email, adress,
                        zipcode, personalPhoneNumber, professionnalPhoneNumber, gitLink);

                contact.setId(id);
                sqlAllContact.add(contact);

            }
            

        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }

        return sqlAllContact;
    }

    public Contact find(int id) {
        Contact cont = new Contact();
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE)
                    .executeQuery(
                            "SELECT * FROM contact WHERE id = " + id);
            if (result.first())
                cont = new Contact(
                        
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("pseudo"),
                        result.getString("gender"),
                        LocalDate.parse(result.getString("birth_date")),
                        result.getString("adress"),
                        result.getString("zipcode"),
                        result.getString("personal_phone_number"),
                        result.getString("professionnal_phone_number"),
                        result.getString("email"),
                        result.getString("gitlink"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cont;

    }

    public Contact update(Contact updateContact) {

        int id = updateContact.getId();

        try {
            
            PreparedStatement stm = this.connect
                    .prepareStatement(
                            "UPDATE public.contact SET zipcode=?,professionnal_phone_number=?,email=?,gender=?,birth_date=?,gitlink=?,adress=?,personal_phone_number=?,first_name=?,last_name=?,pseudo=? WHERE id=?");

            stm.setString(1, updateContact.getZipCode().get());
            stm.setString(2, updateContact.getProfessionalPhone().get());
            stm.setString(3, updateContact.getMail().get());
            stm.setString(4, updateContact.getGender().get());
            stm.setDate(5, Date.valueOf(updateContact.getBirthDate()));
            stm.setString(6, updateContact.getGitLinks().get());
            stm.setString(7, updateContact.getAddress().get());
            stm.setString(8, updateContact.getPersonalPhone().get());
            stm.setString(9, updateContact.getFirstName().get());
            stm.setString(10, updateContact.getLastName().get());
            stm.setString(11, updateContact.getSurname().get());
            stm.setInt(12, id);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateContact;
    }

    public boolean delete(int id) {

        try {
            PreparedStatement stm = connect.prepareStatement("DELETE FROM contact WHERE id=?");

            stm.setInt(1, id);
            
            stm.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Contact add(Contact newContact) {

        try {
            PreparedStatement stm = this.connect.prepareStatement(
                    "INSERT INTO public.contact (first_name,last_name,pseudo,gender,birth_date,email,adress,zipcode,personal_phone_number,professionnal_phone_number,gitlink) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            stm.setString(1, newContact.getFirstName().get());
            stm.setString(2, newContact.getLastName().get());
            stm.setString(3, newContact.getSurname().get());
            stm.setString(4, newContact.getGender().get());
            stm.setDate(5, Date.valueOf(newContact.getBirthDate()));
            stm.setString(6, newContact.getMail().get());
            stm.setString(7, newContact.getAddress().get());
            stm.setString(8, newContact.getZipCode().get());
            stm.setString(9, newContact.getPersonalPhone().get());
            stm.setString(10, newContact.getProfessionalPhone().get());
            stm.setString(11, newContact.getGitLinks().get());

            System.err.println(stm.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newContact;
    }

    public boolean deleteID(Contact contact) {

        try {
        PreparedStatement stm = connect.prepareStatement("DELETE FROM contact WHERE first_name=? and last_name=?");

            stm.setString(1, contact.getFirstName().get());
            stm.setString(2, contact.getLastName().get());

            System.err.println(stm.executeUpdate());

            return true;

        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());

        }

        return false;
    }
}

