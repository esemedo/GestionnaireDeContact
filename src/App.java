import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


import model.Contact ;


public class App {
    private static final Scanner _scanner = new Scanner(System.in);
    //private static  Contact contact = new Contact();
    public static void main(String[] args) throws Exception {
        _AfficheMenu();
        _menu();
    }
    private  static void _AfficheMenu(){
        String add = "A - Ajouter contact\n";
        String list = "L - Liste des contacts\n";
        String remove = "R - remove\n";
        String quitter = "Q - Quitter l'appli";
        System.out.println(add + list + remove + quitter);
    }
    private static void _menu(){
        System.out.println("Choix de l'onglet : ");
        String choiceMenu = _scanner.nextLine();
        switch (choiceMenu) {
            case "A" -> addContact();
            case "L" -> listContact();
            case "R" -> removeContact();
            case "Q" -> System.out.println("Quitter");
            default -> _menu();
        }


    }

    private static void listContact()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            ArrayList<Contact> list = Contact.lister();

            for (Contact contact : list) {
                System.out.println(contact.getLastname() + " " + contact.getFirstname());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeContact()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrer le numéro : ");
            String numero = _scanner.nextLine();
            BufferedReader br = new BufferedReader(new FileReader("contacts.csv"));
            String line;
            int numberline = -1;
            while ( (line = br.readLine()) != null ) {
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[3].equals(numero)) {
                    list.remove(numberline);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(list);
                  
                    // stringBuilder.append("list");
                    try(FileWriter writer = new FileWriter("contacts.csv")){
                        writer.write(stringBuilder.toString().replace("[", "").replace("]", ",").replace(",", "\n"));
                    }
                    break;
                }
            }
            // for (Contact contact : list) {
            //     System.out.println(contact.getLastname() + " " + contact.getFirstname());
            // }
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
        System.out.println(contact.toString());
        try {
            contact.enregistrer();
            System.out.println("Contact enregistré");
        } catch (IOException e) {
            System.out.println("Le contact n'a pas été enregistrer");
        }

    }
}