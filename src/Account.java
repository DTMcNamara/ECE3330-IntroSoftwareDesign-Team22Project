public class Account {

    private String userName;
    private String passWord;
    private Databases db = new Databases();

    public Account(String userName, String passWord)
    {
        this.userName = userName;
        this.passWord = passWord;
    }

    public boolean checkUser()
    {
       return db.validatePasswordByUsername(userName,passWord);
    }

    public Databases getDb()
    {
        return db;
    }
}
