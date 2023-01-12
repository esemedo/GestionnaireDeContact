package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Comparable{
    private String lastname;
    private String firstname;//prenom
    private String mail;
    private String telephone;
    private Date birthdate;

    private static final String SEPARATEUR = ";";

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMail() {
        return mail;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws ParseException {
        Pattern p = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher m = p.matcher(telephone);
        if (m.matches()) {
            this.telephone = telephone;
        } else {
            throw new ParseException("Le format du numéro est incorrect.", 0);
        }
    }

    public String getBirthdate() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(birthdate);
    }

    public void setBirthdate(String birthdate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.birthdate = format.parse(birthdate);
    }

    public void enregistrer() throws IOException {
        /*PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));//demander au PO pk
        try {
            pw.println(this.toString());
        } finally {
            pw.close();

        }*/
        try (PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)))) {
            pw2.println(this.toString());
        }
    }

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
                            System.out.println("Nouvelle email : ");
                            String email = _scanner.nextLine();
                            contact.setMail(email);

                            break;
                        case "T":
                            System.out.println("Nouveau numéro : ");
                            String telephone = _scanner.nextLine();
                            contact.setTelephone(telephone);

                            break;
                        case "D":
                            System.out.println("Nouvelle date de naissance : ");
                            String DateBirth = _scanner.nextLine();
                            contact.setBirthdate(DateBirth);

                            break;
                        default:
                            modify(contact,mail);
                    }
                }
                return contact;

    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder(); //demander au PO pk
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

    @Override
    public int compareTo(Contact contact) throws Exception {
        int compareName = this.lastname.compareTo(contact.getLastname());
        return compareName !=0 ? compareName : this.firstname.compareTo(contact.getFirstname());
    }
}
