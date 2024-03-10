package me.imflowow.tritium.utils.account.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.imflowow.tritium.core.Tritium;

public class AltSaving {
    private static File altFile;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public AltSaving(File dir) {
        altFile = new File(dir + File.separator + "alts.json");
    }

    public void setup() {
        // Tries to create the module's file.
        try {
            // Creates the module file if it doesn't exist.
            if (!altFile.exists()) {

                // Creates the module's file.
                altFile.createNewFile();
                // Returns because there is no need to load.
                return;
            }

            // Loads the file.
            loadFile();
        } catch (IOException exception) {
        }
    }


    public void loadFile() {
        if (!altFile.exists()) {
            return;
        }
        try (FileReader inFile = new FileReader(altFile)) {
            Tritium.instance.getAccountManager().setAccounts(GSON.fromJson(inFile, new TypeToken<ArrayList<Account>>() {
            }.getType()));

            if (Tritium.instance.getAccountManager().getAccounts() == null)
                Tritium.instance.getAccountManager().setAccounts(new ArrayList<Account>());

        } catch (Exception e) {
        }
    }

    public void saveFile() {
        if (altFile.exists()) {
            try (PrintWriter writer = new PrintWriter(altFile)) {
                writer.print(GSON.toJson(Tritium.instance.getAccountManager().getAccounts()));
            } catch (Exception e) {
            }
        }
    }
}
