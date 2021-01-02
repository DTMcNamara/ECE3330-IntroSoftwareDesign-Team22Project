import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Databases implements Serializable
{
    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class022";
    private static final String DATABASE_USERNAME = "engr_class022";
    private static final String DATABASE_PASSWORD = "killerclown";
    private static final String VALIDATE_LOGIN_QUERY = "SELECT username, password FROM userRecord";
    private static final String GET_UID_BY_USERNAME_QUERY = "SELECT userID, username FROM userRecord";
    private static final String GET_USERNAMES_QUERY = "SELECT username FROM userRecord";
    private static final String GET_EMAIL_QUERY = "SELECT email FROM userRecord;";
    private static final String GET_BALANCE_QUERY = "SELECT username, balance FROM userRecord";
    private static final String GET_FIRSTLAST_QUERY = "SELECT username,LName,FName FROM userRecord";
    private static final String GET_MASTERRECORD_QUERY = "SELECT * FROM ServerMasterRecord";

    public static void main(String[] args)
    {
        Databases db = new Databases();
        db.validatePasswordByUsername("dtmcnamara", "fml1234");
        db.userExists("tversluis");
        db.getBalance("jrathjen");
        db.getName("dtmcnamara");
        db.updateBalance("jrathjen", -200);
        db.checkUniqueEA("dtmcnamara13@gmail.com");
        db.checkUniqueEA("SkippyDingus@pornhub.com");
        db.checkUniqueUN("jrathjen");
        db.checkUniqueUN("SkippyDingus");
        db.userTransaction("jrathjen","dtmcnamara",1000, 1,"test");
        db.createUser("testing3","testing@uiowa.edu","test","test",
                "test");
        db.deleteUser("testing3");
        db.getMasterRecord("jrathjen");
    }

    public boolean userExists(String username)
    {
        boolean found=false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(GET_UID_BY_USERNAME_QUERY);
            while (rs.next())
            {
                String usernameTest = rs.getString("username");
                if (usernameTest.equals(username))
                {
                    found=true;
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (found)
        {
            System.out.println(username + " found. ");
        }
        return found;
    }

    public void getName(String username)
    {
        String name = null;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(GET_FIRSTLAST_QUERY);
            while (rs.next())
            {
                String usernameTest = rs.getString("username");
                if (usernameTest.equals(username))
                {
                    name = rs.getString("FName");
                    name += " " + rs.getString("LName");
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (name != null)
        {
            System.out.println(username + " Account Owner: " + name);
        }
    }

    public int getBalance(String username)
    {
        int currentBalance = 0;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(GET_BALANCE_QUERY);
            while (rs.next())
            {
                String usernameTest = rs.getString("username");
                if (usernameTest.equals(username))
                {
                    currentBalance = rs.getInt("balance");
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (currentBalance != 0)
        {
            System.out.println(username + " Current Balance: " + currentBalance);
        }
        return currentBalance;
    }

    public boolean validatePasswordByUsername(String usernameLogin, String passwordLogin)
    {
        boolean correctLogin = false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet results;
            results = s.executeQuery(VALIDATE_LOGIN_QUERY);
            while (results.next())
            {
                String password = results.getString("password");
                String username = results.getString("username");
                if (password.equals(passwordLogin) && username.equals(usernameLogin))
                {
                    correctLogin = true;
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        System.out.println(correctLogin);
        return correctLogin;
    }

    public void updateBalance(String username, int value)
    {
        boolean complete = false;
        int balance = 0;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            balance = getBalance(username);
            balance += value;
            String updateStatement = "UPDATE userRecord SET balance='" + balance + "'WHERE username='" + username + "'";
            int u = s.executeUpdate(updateStatement);
            System.out.println("Number of rows updated: " + u);
            if (u != 0)
            {
                complete = true;
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (complete)
        {
            System.out.println("Successfully transferred balance");
        }
    }

    public boolean checkUniqueUN(String username)
    {
        boolean unique = true;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(GET_USERNAMES_QUERY);
            while (rs.next())
            {
                String usernameTest = rs.getString("username");
                if (usernameTest.equals(username))
                {
                    unique = false;
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (unique)
        {
            System.out.println(username + " is Unique!");
        }
        else
        {
            System.out.println(username + " is not Unique!");
        }
        return unique;
    }

    public boolean checkUniqueEA(String emailAddress)
    {
        boolean unique = true;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(GET_EMAIL_QUERY);
            while (rs.next())
            {
                String emailTest = rs.getString("email");
                if (emailTest.equals(emailAddress))
                {
                    unique = false;
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (unique)
        {
            System.out.println(emailAddress + " is Unique!");
        }
        else
        {
            System.out.println(emailAddress + " is not Unique!");
        }
        return unique;
    }

    public void createUser(String username, String emailAddress, String lastName, String firstName, String password)
    {
        boolean complete = false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            if (checkUniqueUN(username) && checkUniqueEA(emailAddress))
            {
                String createStatement = "INSERT INTO userRecord(username,LName,FName,email,password,balance)" +
                        "VALUES('" + username + "','" + lastName + "','" + firstName + "','" + emailAddress +
                        "','" + password + "','" + 0 + "')";
                int u = s.executeUpdate(createStatement);
                if (u != 0)
                {
                    complete = true;
                    createUserRecord(username);
                }
            }
            conn.close();
        } catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (complete)
        {
            System.out.println("Successfully created account");
        }
        else
        {
            System.out.println("Failed to created account");
        }
    }

    private void createUserRecord(String username) {
        String makeTableStatement = "CREATE TABLE " + username + "Record (TransactionID int(10) NOT NULL AUTO_INCREMENT" +
                " , Sender VARCHAR(50) NOT NULL, receiver VARCHAR(50) NOT NULL, Amount int(11) NOT NULL, NewBalance int(11)" +
                " NOT NULL, private BOOLEAN NOT NULL DEFAULT 0, memo VARCHAR(100) NOT NULL, PRIMARY KEY(TransactionID))";
        boolean complete = false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            int u = s.executeUpdate(makeTableStatement);
            if (u == 0)
            {
                complete = true;
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (complete)
        {
            System.out.println("Successfully created account transaction history");
        }
        else
        {
            System.out.println("Failed to created account transaction history");
        }
    }

    private void addToTransactionHistory(String usernameSend, String usernameReceive, int amount, int privacy, String memo){
        String addToSenderStatement = "INSERT INTO "+usernameSend+"Record(Sender, receiver, Amount, NewBalance,private,memo) " +
                "VALUES('you','"+usernameReceive+"','"+amount+"','"+getBalance(usernameSend)+"','"+privacy+"','"+memo+"')";
        String addToReceiverStatement= "INSERT INTO "+usernameReceive+"Record(Sender, receiver, Amount, NewBalance,private,memo) " +
                "VALUES('"+usernameSend+"','you','"+amount+"','"+getBalance(usernameReceive)+"','"+privacy+"','"+memo+"')";
        String addToMasterStatement= "INSERT INTO ServerMasterRecord(Sender, receiver, Amount, NewBalance,private,memo) " +
                "VALUES('"+usernameSend+"','"+usernameReceive+"','"+amount+"','"+getBalance(usernameReceive)+"','"+privacy+"','"+memo+"')";
        boolean complete = false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            updateBalance(usernameSend,amount*-1);
            updateBalance(usernameReceive,amount);
            int ss = s.executeUpdate(addToSenderStatement);
            int rs = s.executeUpdate(addToReceiverStatement);
            int ms = s.executeUpdate(addToMasterStatement);
            if (ss!=0 && rs!=0 && ms!=0)
            {
                complete = true;
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (complete)
        {
            System.out.println("Successfully sent and recorded Transaction");
        }
        else
        {
            System.out.println("Failed to send and record Transaction");
        }
    }

    public void deleteUser(String username)
    {
        String dropTableStatement="DROP TABLE "+username+"Record";
        String deleteUserFromList="DELETE FROM userRecord WHERE username='"+username+"'";
        boolean complete = false;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            int dt = s.executeUpdate(dropTableStatement);
            int du = s.executeUpdate(deleteUserFromList);
            if (du != 0&&dt==0)
            {
                complete = true;
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        if (complete)
        {
            System.out.println("Successfully deleted account and transaction History");
        }
        else
        {
            System.out.println("Failed to delete account and transaction History");
        }
    }

    public void userTransaction(String usernameSend, String usernameReceive, int amount, int privacy,String memo)
    {
        int senderBalance = getBalance(usernameSend);
        if(amount<senderBalance)
        {
            addToTransactionHistory(usernameSend,usernameReceive,amount,privacy,memo);
        }
    }



    public ArrayList<String> getMasterRecord(String username)
    {
        ArrayList<String> record=new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(GET_MASTERRECORD_QUERY);
            while (rs.next())
            {
                String sender = rs.getString("Sender");
                String receiver=rs.getString("receiver");
                String amount = rs.getString("amount");
                String memo = rs.getString("memo");
                int priv = rs.getInt("private");
                String concat = sender+" paid "+receiver+" $"+amount+" for "+memo ;
                System.out.println(concat);
                if(priv==0)
                {
                    record.add(concat);
                }
                else if(sender.matches(username)||receiver.matches(username))
                {
                    record.add(concat);
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.err.println("issue in finding Database");
            System.err.println(e.getMessage());
        }
        return record;
    }


}
