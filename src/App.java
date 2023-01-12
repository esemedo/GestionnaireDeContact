import java.io.*;
import java.text.ParseException;
import java.util.*;


import model.Contact ;


public class App {
    private static final Scanner _scanner = new Scanner(System.in);
    //private static  Contact contact = new Contact();
    public static void main(String[] args)  {
        _AfficheMenu();
        _menu();
    }
    private  static void _AfficheMenu(){
        String add = "1 - Ajouter contact\n";
        String list = "2 - Liste des contacts\n";
        String modify = "3 - Modifier un contact\n";
        String delete = "4 - Supprimer un contact\n";
        String quitter = "Q - Quitter l'appli";
        System.out.println(add + list + modify + delete + quitter);
    }
    private static void _menu(){
        System.out.println("Choix de l'onglet : ");
        String choiceMenu = _scanner.nextLine();
        switch (choiceMenu) {
            case "1" -> addContact();
            case "2" -> listContact();
            case "3" -> modifyContact();
            case "4" -> System.out.println("Delete");
            case "Q" -> System.out.println("Quitter");
            default -> _menu();
        }
    }
    private static void modifyContact()  {
        Boolean modify = false;
        System.out.println("Entrez un email :");
        String mail = _scanner.nextLine();
        try {
            ArrayList<Contact> list = Contact.lister();
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts2.csv", true)))) {
                for (Contact contact : list) {
                    if (contact.getMail().equals(mail)) {
                        Contact contactModifier = Contact.modify(contact, mail);
                        modify = true;
                        pw.println(contactModifier);
                    } else {
                        pw.println(contact);
                    }

                }
                File realFile = new File("contacts.csv");
                realFile.delete();
                new File("contacts2.csv").renameTo(realFile);

            }if (!modify){
                System.out.println("Contact inexitant\n");
                _AfficheMenu();
                _menu();
            }
         }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static List triNom(ArrayList<Contact> list){
        List listNomPrenom = new ArrayList<>();
        for (Contact contact : list) {
            listNomPrenom.add(contact.getLastname() +" "+ contact.getFirstname());

        }
        return listNomPrenom;
    }

    private static void listContact()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            ArrayList<Contact> list = Contact.lister();
            List listNomPrenom = triNom(list);
            Collections.sort(listNomPrenom);

            for (Object ma : listNomPrenom) {
                System.out.println(ma);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addContact()  {
        Contact contact = new Contact();
        System.out.println("Entrer le nom : ");
        String lastname = _scanner.nextLine();
        contact.setLastname(lastname);

        System.out.println("Entrer le prénom: ");
        String firstname = _scanner.nextLine();
        contact.setFirstname(firstname);
        while (true){
            try{
                System.out.println("Entrer le numéro de téléphone : ");
                String telephone = _scanner.nextLine();
                contact.setTelephone(telephone);
                break;
            }catch (Exception e) {
                System.out.println("Numéro de téléphone invalide");
            }
        }


        while (true) {
            try {
                System.out.println("Entrer le mail : ");
                String mail = _scanner.nextLine();
                contact.setMail(mail);
                break;
            } catch (Exception e) {
                System.out.println("Email invalide");

            }
        }
        while(true){
            try {
                System.out.println("Entrer la date de naissance : ");
                String birthdate = _scanner.nextLine();
                contact.setBirthdate(birthdate);
                break;
            } catch (ParseException e) {
                System.out.println("Date de naissance invalide");
            }
        }
        try {
            contact.enregistrer();
            System.out.println("Contact enregistré");
        } catch (IOException e) {
            System.out.println("Le contact n'a pas été enregistrer");
        }

    }
}