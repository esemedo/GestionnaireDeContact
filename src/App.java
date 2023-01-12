import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.Comparator;

import model.Contact ;


public class App {
    private static final Scanner _scanner = new Scanner(System.in);
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
        System.out.println(add + list +  modify + remove +chercherparnom + chercherparprenom +   quitter);
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
        try {
            Boolean contactTrouve = false;
            System.out.println("Entrez un email :");
            String mail = _scanner.nextLine();
            ArrayList<Contact> list = Contact.lister();
            Contact Boncontact = null;
            for (Contact contact : list) {
                if (contact.getMail().equals(mail)) {
                    Boncontact = contact;
                    System.out.println(Boncontact);
                    contactTrouve = true;
                    break;
                }
            }
            if (!contactTrouve) {
                System.out.println("Contact inexitant\n");
                modifyContact();
            }
            Contact contactModifier = Contact.modify(Boncontact, mail);
            try(FileWriter writer = new FileWriter("contacts.csv")) {
            for (Contact contact : list){
                    if(! contact.equals(Boncontact)){
                        writer.write(contact.toString() + "\n");
                    }
                    else {
                        writer.write(contactModifier.toString()+ "\n");
                    }
            }
            }
         }catch (ParseException | IOException e) {
            System.out.println("Désolé, ce n'est pas possible actuellement");
            System.out.println(e.getMessage());
        }
    }

    private static List triNom(ArrayList<Contact> list){
        List listNomPrenom = new ArrayList<>();
        for (Contact contact : list) {
            listNomPrenom.add(contact.getLastname() +" "+ contact.getFirstname() +" "+ contact.getMail() +" "+ contact.getTelephone() +" "+ contact.getBirthdate());

        }
        Collections.sort(listNomPrenom);
        return listNomPrenom;
    }
    public static Comparator<Contact> ComparatorByBirth = Comparator.comparing(Contact::getBirthdate);
    public static Comparator<Contact> ComparatorByMail = new Comparator<Contact>() {
        public int compare(Contact contact1, Contact contact2) {
            return contact1.getMail().compareTo(contact2.getMail());
        }
    };


    private static void listContact()  {
        try {
        ArrayList<Contact> list = Contact.lister();
        List<Contact> listContact = list;
        System.out.println("1 - Tri par nom");
        System.out.println("2 - Tri par date de naissance");
        System.out.println("3 - Tri par mail");
        System.out.println("4 - Sans tri");

        System.out.println("Choisissez une des options :");
        String response = _scanner.nextLine();
        switch (response){
            case "1":
                List contactList = triNom(list);
                for (Object contact : contactList) {
                    System.out.println(contact);
                }
                break;
            case "2":
                Collections.sort(listContact, ComparatorByBirth);
                for (Contact contact : listContact) {
                    System.out.println(contact.getLastname() + " " + contact.getFirstname()+ " " + contact.getMail()+ " " + contact.getTelephone()+ " " + contact.getBirthdate());
                }
                break;
            case "3":
                Collections.sort(listContact, ComparatorByMail);
                for (Contact contact : listContact) {System.out.println(contact.getLastname() + " " + contact.getFirstname()+ " " + contact.getMail()+ " " + contact.getTelephone()+ " " + contact.getBirthdate());
                }
                break;
            case "4":
                for (Contact contact : listContact) {
                    System.out.println(contact.getLastname() + " " + contact.getFirstname()+ " " + contact.getMail()+ " " + contact.getTelephone()+ " " + contact.getBirthdate());
                }
                break;
            default:
                listContact();
                break;
        }
        } catch (Exception e) {
            System.out.println("Désolé, ce n'est pas possible actuellement");
        }
    }

    private static void removeContact()  {
        // récupérer les contacts avec la méthode lister de la class contact
        try {
            Boolean delete = false;
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
                        delete= true;
                    }
                    break;
                }
            }
            if(!delete){
                System.out.println("Le contact n'est pas dans la liste");
                removeContact();
            }

        } catch (Exception e) {
            System.out.println("Désolé, ce n'est pas possible actuellement");
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
            System.out.println("Désolé, ce n'est pas possible actuellement");
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
            System.out.println("Désolé, ce n'est pas possible actuellement");
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
                System.out.println("Entrer la date de naissance (format: yyyy-MM-dd) : ");
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