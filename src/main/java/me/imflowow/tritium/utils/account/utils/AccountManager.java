package me.imflowow.tritium.utils.account.utils;

import java.io.File;
import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> accounts = new ArrayList<>();
    private AltSaving altSaving;

    public AccountManager(File dir) {
        altSaving = new AltSaving(dir);
        altSaving.setup();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public AltSaving getAltSaving() {
        return altSaving;
    }
}
