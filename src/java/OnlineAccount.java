/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.context.FacesContext;

/**
 *
 * @author Victor
 */

public class OnlineAccount {

    private String id;
    private String email;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //constructor
    public OnlineAccount(String Id, String Email) {
        id = Id;
        email = Email;
    }

    //log out, kill the session and return to the main page
    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }

}
