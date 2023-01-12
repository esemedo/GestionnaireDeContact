import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        String remove = "4 - Supprimer un contact\n";
        String chercherparnom = "5 - Chercher par nom\n";
        String chercherparprenom = "6 - Chercher par prenom\n";
        String quitter = "Q - Quitter l'appli";
        System.out.println(add + list + chercherparnom + modify + chercherparprenom + remove + quitter);
    }
    private static void _menu(){
        System.out.println("Choix de l'onglet : ");
        String choiceMenu = _scanner.nextLine();
        switch (choiceMenu) {
            case "1" -> addContact();
            case "2" -> listContact();
            case "3" -> modifyContact();
            case "4" -> removeContact();
            case "5" -> rechercheContactparnom();
            case "6" -> rechercheContactparprenom();
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
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv")))) {
                for (Contact contact : list) {
                    if (contact.getMail().equals(mail)) {
                        Contact contactModifier = Contact.modify(contact, mail);
                        modify = true;
                        pw.println(contactModifier);
                    } else {
                        pw.println(contact);
                    }

                }
                // File realFile = new File("contacts.csv");
                // realFile.delete();
                // new File("contacts2.csv").renameTo(realFile);

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
            listNomPrenom.add(contact.getLastname() +" "+ contact.getFirstname() +" "+ contact.getMail() +" "+ contact.getTelephone() +" "+ contact.getBirthdate());

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

    private static void rechercheContactparnom()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrer le Nom : ");
            String name = _scanner.nextLine();
            BufferedReader br = new BufferedReader(new FileReader("contacts.csv"));
            String line;
            int numberline = -1;
            while ( (line = br.readLine()) != null ) {
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[1].toUpperCase().equals(name.toUpperCase())) {
                    System.out.println("Prénom : " + values[0] + "\nNom : " + values[1] + "\nMail : " + values[2] + "\nNuméro : " + values[3] + "\nDate de naissance : " + values[4]);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rechercheContactparprenom()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrer le Prénom : ");
            String name = _scanner.nextLine();
            BufferedReader br = new BufferedReader(new FileReader("contacts.csv"));
            String line;
            int numberline = -1;
            while ( (line = br.readLine()) != null ) {
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[0].toUpperCase().equals(name.toUpperCase())) {
                    System.out.println("Prénom : " + values[0] + "\nNom : " + values[1] + "\nMail : " + values[2] + "\nNuméro : " + values[3] + "\nDate de naissance : " + values[4]);
                    break;
                }
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