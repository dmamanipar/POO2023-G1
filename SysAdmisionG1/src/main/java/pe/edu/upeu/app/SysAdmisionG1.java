/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pe.edu.upeu.app;

import java.util.prefs.Preferences;
import pe.edu.upeu.app.gui.Login;
import pe.edu.upeu.app.util.UtilsX;

/**
 *
 * @author Data_Science
 */
public class SysAdmisionG1 {

    static Preferences userPrefs = Preferences.userRoot();

    public static void main(String[] args) {
        userPrefs.put("IDIOMAX", new UtilsX().readLanguageFile());
        userPrefs.put("PERFIL", "Admin");
        new Login().setVisible(true);
    }
}
