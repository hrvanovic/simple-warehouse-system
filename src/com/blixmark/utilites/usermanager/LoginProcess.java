package com.blixmark.utilites.usermanager;

import com.blixmark.utilites.AuthSession;
import com.blixmark.utilites.SystemLog;
import com.blixmark.utilites.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginProcess {
    private int userID;
    /**
     * Metod za provjeru podataka pri loginu koju korisnik unese putem Login forme.
     *
     * @param email Email koji korisnik unese putem Login forme.
     * @param password Lozinka koju korisnik unese putem Login forme.
     * @return         Vraca se boolean vrijednost, ukoliko je true korisnik ce se uspjesno loginovat, ukoliko ne izbacuje se greska.
     */
    public boolean checkLoginCreditials(String email, String password) {
        int usersFound = 0;
        Database database = new Database();
        try {
            String query = "SELECT kor_id FROM korisnici WHERE kor_email = ?";
            PreparedStatement preparedStatement = database.getPreparedStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                this.userID = resultSet.getInt("kor_id");
                AuthSession.setUser(UserUtility.getUser(this.userID));
                usersFound++;
            }

            return (usersFound == 1);

        } catch (Exception exception) {
            new SystemLog().newLog(exception);
            return false;
        } finally {
            database.closeConn();
        }
    }

    public int getUserID() {
        return this.userID;
    }
}
