package ContactDiscovery;

/** A user's identifiers: their pseudo and IP address
 *
 */
public class Contact {
    public String ip;
    public String pseudo;

    /** PseudoIP Constructor
     *
     * @param pseudo The chosen pseudo
     * @param ip The associated IP address of our machine
     */
    public Contact(String pseudo, String ip){
        this.pseudo=pseudo;
        this.ip=ip;
    }
}
