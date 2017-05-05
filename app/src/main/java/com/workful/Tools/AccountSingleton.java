package com.workful.Tools;

/**
 * Created by Cristian on 4/30/2017.
 */

public class AccountSingleton {

    private String email;
    private int id;
    private String full_name;

    private static AccountSingleton currentAccount = null;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFull_name() {
        return full_name;
    }

    private AccountSingleton(String email, int id) {
        this.email = email;
        this.id = id;
    }

    public void accountLogout(){
        currentAccount = null;
    }


    public static AccountSingleton getCurrent(){
        return currentAccount;
    }

    public static void createAccount(String email, int id){
        if (currentAccount == null)
            currentAccount = new AccountSingleton(email, id);
    }

}
