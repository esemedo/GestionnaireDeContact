package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe Contact posséde les caractéristiques d'un contact et des méthodes pour les modifier/affecter.
 * Elle implémente l'interface Comparable
 */
public class Contact implements Comparable<Contact> {
    /**
     * Nom de famille
     */
    private String lastname;
    /**
     * Prénom
     */
    private String firstname;
    /**
     * Mail
     */
    private String mail;
    /**
     * Télephone
     */
    private String telephone;
    /**
     * Date de naissance
     */
    private Date birthdate;
    /**
     * Point-virgule
     */
    private static final String SEPARATEUR = ";";
    /**
     * getter de lastname
     * @return lastname - nom du contact
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * Modifie la valeur du nom de famille.
     * @param lastname nom sous chaine de caractère
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * getter de firstname
     * @return firstname - prénom du contact
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Modifie la valeur du prénom.
     * @param firstname prénom de type chaine de caractère
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * getter de mail
     * @return mail - e-mail du contact
     */
    public String getMail() {
        return mail;
    }
    /**
     * Modifie la valeur de l'e-mail et vérifie que l'email entré soit correcte.
     * @param mail e-mail de type chaine de caractère
     * @throws ParseException  si email incorrecte
     */
    public void setMail(String mail) throws ParseException {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(mail);
        if (m.matches()) {
            this.mail = mail;
        } else {
            throw new ParseException("Le format du mail est incorrect.", 0);
        }
    }
    /**
     * getter de téléphone
     * @return telephone - numéro du contact
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * Modifie la valeur du numéro de téléphone et vérifie qu'il soit correcte.
     * @param telephone numéro de type chaine de caractère
     * @throws ParseException  si numéro incorrecte
     * */
    public void setTelephone(String telephone) throws ParseException {
        Pattern p = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher m = p.matcher(telephone);
        if (m.matches()) {
            this.telephone = telephone;
        } else {
            throw new ParseException("Le format du numéro est incorrect.", 0);
        }
    }
    /**
     * getter de birthdate
     * @return birthdate - date de naissance du contact
     */
    public String getBirthdate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(birthdate);
    }
    /**
     * Parse la String en Date et modifie donc sa valeur.
     * @param birthdate date de naissance de type chaine de caractère
     * @throws ParseException  si date incorrecte
     * */
    public void setBirthdate(String birthdate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.birthdate = format.parse(birthdate);
    }

    /**
     * Enregistre dans un fichier csv les contacts.
     * @throws IOException  si il ya un probléme avec l'écriture dans le fichier
     */
    public void enregistrer() throws IOException {
        try (PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)))) {
            pw2.println(this.toString());
        }
    }
    /**
     * Liste tous les contacts provenant d'un fichier csv s'il y en a.
     * @return list - ArrayList de Contact
     * @throws IOException s'il y a un problème avec la lecture du fichier
     * @throws ParseException causer par les méthodes setEmail, setTelephone et setBirthdate
     */
    public static ArrayList<Contact> lister() throws  IOException, ParseException {
        ArrayList<Contact> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("contacts.csv"))) {
            String ligne = br.readLine();
            while (ligne != null) {
                String[] tab = ligne.split(SEPARATEUR);
                Contact c = new Contact();
                c.setLastname(tab[0]);
                c.setFirstname(tab[1]);
                c.setMail(tab[2]);
                c.setTelephone(tab[3]);
                c.setBirthdate(tab[4]);
                list.add(c);
                ligne = br.readLine();

            }
        }
        return list;
    }
    /**
     * Modifie les informations d'un contact
     * @param contact  instance de la classe contact
     * @param mail String
     * @return contact - instance de la classe contact
     * @throws ParseException causer par les méthodes setEmail, setTelephone et setBirthdate
     */
    public static Contact modify(Contact contact, String mail) throws ParseException {
                Scanner _scanner = new Scanner(System.in);
                ArrayList<String> list = new ArrayList<>();


                if (contact.getMail().equals(mail)) {
                    list.add("Que voulez-vous modifier ?");
                    list.add("N - Nom");
                    list.add("P - Prénom");
                    list.add("M - Mail");
                    list.add("T - Téléphone");
                    list.add("D - Date de naissance");
                    for (String elem : list) {
                        System.out.println(elem);
                    }
                    System.out.println("Entrer votre réponse: ");
                    String res = _scanner.nextLine();

                    switch (res) {
                        case "N":
                            System.out.println("Nouveau nom : ");
                            String lastname = _scanner.nextLine();
                            contact.setLastname(lastname);
                            return contact;
                        case "P":
                            System.out.println("Nouveau prénom: ");
                            String firstname = _scanner.nextLine();
                            contact.setFirstname(firstname);

                            break;
                        case "M":
                            try{
                                System.out.println("Nouvelle email : ");
                                String email = _scanner.nextLine();
                                contact.setMail(email);
                            }
                            catch (ParseException e){
                                System.out.println("Email incorrect");
                                modify(contact,mail);
                            }

                            break;
                        case "T":
                            try{
                                System.out.println("Nouveau numéro : ");
                                String telephone = _scanner.nextLine();
                                contact.setTelephone(telephone);
                            }

                            catch (ParseException e){
                            System.out.println("Numéro incorrect");
                            modify(contact,mail);
                        }
                            break;
                        case "D":
                            try {
                                System.out.println("Nouvelle date de naissance : ");
                                String DateBirth = _scanner.nextLine();
                                contact.setBirthdate(DateBirth);
                            }
                            catch (ParseException e){
                            System.out.println("Date de naissance incorrect");
                            modify(contact,mail);
                            }
                            break;

                        default:
                            modify(contact,mail);
                    }
                }
                return contact;

    }

    /**
     * Transforme l'objet en String.
     * @return Chaine de caractères
     */
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(this.getLastname());
        build.append(SEPARATEUR);
        build.append(this.getFirstname());
        build.append(SEPARATEUR);
        build.append(this.getMail());
        build.append(SEPARATEUR);
        build.append(this.getTelephone());
        build.append(SEPARATEUR);
        build.append(this.getBirthdate());
        return build.toString();
    }

    /**
     * Fais une comparaison de différents élements, ici le nom et le prénom si égalité,  et retourne un nombre.
     * Ce nombre peut être soit négatif, soit positif, soit égale à 0.
     * Cette méthode a pour but d'être utiliser lorsqu'on applique la fonction de trie sort avec l'interface Comparable.
     * @param contact the object to be compared.
     * @return number
     */
    @Override
    public int compareTo(Contact contact)  {
        int compareName = this.lastname.toLowerCase().compareTo(contact.lastname.toLowerCase());
        return compareName !=0 ? compareName : this.firstname.toLowerCase().compareTo(contact.firstname.toLowerCase());
    }


}
