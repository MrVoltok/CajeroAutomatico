package cajero_automatico;

public class DataBase {

    User user[] = new User[20];

    public DataBase() {
        user[0] = new User("123456789012", "1234", 100);
        user[1] = new User("012345678901", "4321", 100);
        user[2] = new User("111222333444", "4567", 100);
        user[3] = new User("444333222111", "7894", 100);
        user[4] = new User("333222111444", "9512", 100);
        user[5] = new User("111444333222", "6521", 100);
        user[6] = new User("000111222333", "2365", 100);
        user[7] = new User("121212121212", "7845", 100);
        user[8] = new User("424242424242", "9632", 100);
        user[9] = new User("101010101010", "1258", 100);
    }

    public User[] getUsers() {
        return user;
    }
}
