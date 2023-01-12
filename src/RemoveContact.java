// public class RemoveContact {
    private static void removeContact()  {
    // récupérer les contacts avec la méthode lister de la class contact
    try {
        ArrayList<Contact> list = Contact.lister();

        System.out.println("Entrer le mail : ");
        String mail = _scanner.nextLine();
        contact.setMail(mail);
        break;
        for (Contact contact : list) {
            System.out.println(contact.getLastname() + " " + contact.getFirstname());
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}
// }
