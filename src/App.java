import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.Comparator;
import model.Contact ;

/**
 * App est la classe principale.
 * On y retrouve main et les différentes fonctionnalités principales
 */
public class App {
    /**
    Instance de la classe Scanner.
     Cette classe permet de détecter ce qu'entre l'utilisateur.
     */
    private static final Scanner _scanner = new Scanner(System.in);

    /**
     * This is the main class of the application.
     * @param args tableau de String
     */
    public static void main(String[] args)  {
        _AfficheMenu();
        _menu();
    }
    /**
     * Cette classe permet d'afficher le menu principal de notre gestionnaire.
     * Elle affiche chaque option possible.
     */
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

    /**
     * Permet à l'utilisateur d'entrée en ligne de commande  l'option du menu qu'il a choisi.
     * Si il ne choisit aucune option, on lui demandera à nouveau son choix.
     */
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

    /**
     * Modifie une information du contact.
     * On demande à l'utilisateur un email qui correspond à celui du contact qu'il souhaite modifier.
     * Si, il n'existe pas, on affiche un message et on lui propose à nouveau le choix.
     * On récupère le contact possédant ce mail.
     * On fait ensuite appel à la méthode modify de la classe Contact pour modifier la valeur du contact qu'on lui a envoyé en paramètre.
     * Il nous retourne le contact modifier et ensuite on imprime cette modification dans contacts.csv
     * Si, il y a une erreur, on affichera un message.
     */
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
        }
    }
    /**
     * Trie la liste de contact
     * @throws IOException - quand il y a une erreur en lien avec l'ouverture du fichier, etc.
     * @throws ParseException - causer par les méthodes setEmail, setTelephone et setBirthdate.
     * @return list - une liste de contact trier par nom.
     */
    private static ArrayList triNom() throws IOException, ParseException {
        ArrayList<Contact> list = Contact.lister();
        Collections.sort(list);
        return list;
    }

    /**
     * Utiliser dans l'appel de la fonction sort de Collections pour comparer selon la date de naissance.
     * Utilisation de l'interface Comparator et de la fonction comparing.
     */
    public static Comparator<Contact> ComparatorByBirth = Comparator.comparing(Contact::getBirthdate);
    /**
     * Utiliser dans l'appel de la fonction sort de Collections pour comparer selon la date de naissance.
     * Utilisation de l'interface Comparator et d'une classe anonyme.
     */
    public static Comparator<Contact> ComparatorByMail = new Comparator<Contact>() {
        public int compare(Contact contact1, Contact contact2) {
            return contact1.getMail().compareTo(contact2.getMail());
        }
    };

    /**
     * Affiche la liste des contacts.
     * Si il ne choisit aucune option, on lui demandera à nouveau son choix.
     */
    private static void listContact()  {
        try {
        ArrayList<Contact> list = Contact.lister();
        //List<Contact> listContact = list;
        System.out.println("1 - Tri par nom");
        System.out.println("2 - Tri par date de naissance");
        System.out.println("3 - Tri par mail");
        System.out.println("4 - Sans tri");

        System.out.println("Choisissez une des options :");
        String response = _scanner.nextLine();
        switch (response){
            case "1":
                ArrayList contactList = triNom();
                for (Object contact : contactList) {
                    System.out.println(contact);
                }
                break;
            case "2":
                Collections.sort(list, ComparatorByBirth);
                for (Contact contact : list) {
                    System.out.println(contact.getLastname() + " " + contact.getFirstname()+ " " + contact.getMail()+ " " + contact.getTelephone()+ " " + contact.getBirthdate());
                }
                break;
            case "3":
                Collections.sort(list, ComparatorByMail);
                for (Contact contact : list) {System.out.println(contact.getLastname() + " " + contact.getFirstname()+ " " + contact.getMail()+ " " + contact.getTelephone()+ " " + contact.getBirthdate());
                }
                break;
            case "4":
                for (Contact contact : list) {
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
    /**
     * Supprimer un contact de la liste
     */
    private static void removeContact()  {

        try {
            Boolean delete = false;
            ArrayList<Contact> list = Contact.lister();
            System.out.println("Entrer le numéro : ");
            String numero = _scanner.nextLine();
            BufferedReader br = new BufferedReader(new FileReader("contacts.csv"));
            String line;
            int numberline = -1;
            while ( (line = br.readLine()) != null ) {
                /**
                 * On récupère les valeurs de la ligne courante
                 * On les stocke dans un tableau
                 */
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[3].equals(numero)) {
                    /*
                     * On vérifie que la valeur de la colonne 3 correspond au numéro entré par l'utilisateur
                     * Si c'est le cas, on supprime la ligne de la liste temporaire
                     */
                    list.remove(numberline);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(list);
                    try(FileWriter writer = new FileWriter("contacts.csv")){
                        /*
                         * On supprime les caractères inutiles dans la liste temporaire
                         * On rajoute un retour à la ligne à la fin de la liste
                         * On réécrit le fichier avec les nouvelles valeurs de la liste temporaire
                         */
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

    /**
     * Recherche les contacts selon le nom.
     * L'utilisateur entre un nom et on affiche le contact qui y correspond.
     */
    private static void rechercheContactparnom()  {
        try {
            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrer le Nom : ");
            String name = _scanner.nextLine();
            BufferedReader br = new BufferedReader(new FileReader("contacts.csv"));
            String line;
            int numberline = -1;
            while ( (line = br.readLine()) != null ) {
                /**
                 * On récupère les valeurs de la ligne courante
                 * On les stocke dans un tableau
                 */
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[1].toUpperCase().equals(name.toUpperCase())) {
                    /*
                     * On vérifie que la valeur de la colonne 1 correspond au nom entré par l'utilisateur sans tenir compte des majuscules
                     * Si c'est le cas, on affiche les valeurs de la ligne courante en les séparant par un retour à la ligne
                     */
                    System.out.println("Prénom : " + values[0] + "\nNom : " + values[1] + "\nMail : " + values[2] + "\nNuméro : " + values[3] + "\nDate de naissance : " + values[4]);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Désolé, ce n'est pas possible actuellement");
        }
    }
    /**
     * Recherche les contacts selon le prénom.
     * L'utilisateur entre un prénom et on affiche le contact qui y correspond.
     */
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
                /**
                 * On récupère les valeurs de la ligne courante
                 * On les stocke dans un tableau
                 */
                String[] values = line.split(";");
                numberline = numberline + 1;
                if(values[0].toUpperCase().equals(name.toUpperCase())) {
                    /*
                     * On vérifie que la valeur de la colonne 1 correspond au prénom entré par l'utilisateur sans tenir compte des majuscules
                     * Si c'est le cas, on affiche les valeurs de la ligne courante en les séparant par un retour à la ligne
                     */
                    System.out.println("Prénom : " + values[0] + "\nNom : " + values[1] + "\nMail : " + values[2] + "\nNuméro : " + values[3] + "\nDate de naissance : " + values[4]);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Désolé, ce n'est pas possible actuellement");
        }
    }
    /**
     * Ajoute un contact dans le fichier contacts.csv.
     * On propose à l'utilisateur d'entrer le nom, le prénom, le numéro de téléphone, le mail et la date de naissance.
     * On modifie l'objet Contact avec ses valeurs.
     * On finit par enregistrer le contact dans un fichier contact.csv
     * S'il y a un problème, on affiche un message.
     */
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