import java.util.ArrayList;

public class ContactList {

    private ArrayList<String> listOfContacts;

    //Implementer InitContactList, a contructor for ContactList, to initialize listOfContacts

    //Verify that the nickname is Unique
    //add context
    public boolean isUnique(String nickname){
        return !listOfContacts.contains(nickname);
    }

    public ContactList(){
        listOfContacts = new ArrayList<String> ();
        //Recoie les messages des autres utilisateurs
        //Ajoute les pseudo recu dans listOfContacts
    }

    public void tryConnection(String nickname){
        //Dès réception d'une demande de connexion du User
        //Renvoie erreur ?
        //Call isUnique()
    }
}