public class ContactList {

    private Array<string> listOfContacts;

    //Implementer InitContactList, a contructor for ContactList, to initialize listOfContacts

    //Verify that the nickname is Unique
    //add context
    public boolean isUnique(string nickname){
        for (contact:listOfContacts) {
            if ( nickname == contact) {
                return false
            }
        }
        return true;
    }

    public ContactList(){
        //Recoie les messages des autres utilisateurs
        //

    }

    public void tryConnection(string nickname){
        //Dès réception d'une demande de connexion du User
        //Renvoie erreur ?
        //Call isUnique()
        //
    }
}