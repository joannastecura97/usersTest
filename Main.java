import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        while (true){
            TimeUnit.SECONDS.sleep(15);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://Full2020_94878:3306/users", "JStecura", "root");
                 Statement statement = conn.createStatement())

            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID int NOT NULL AUTO_INCREMENT, login varchar(255), haslo varchar(255), email varchar(255), PRIMARY KEY (ID) );");
                String value = "0";
                do {
                    System.out.println("Wybierz nr polecenia, które chcesz wykonać:\n" +
                            "Pokaż wszystkie dane - 1\n" +
                            "Dodaj użytkownika - 2\n" +
                            "Usuń użytkownika - 3\n" +
                            "Edytuj użytkownika - 4\n" +
                            "Wyjdź - 5");

                    value = scanner.nextLine();
                    switch (value) {
                        case "1":
                            getUsersList(statement);
                            break;
                        case "2":
                            insertUser(statement);
                            break;
                        case "3":
                            deleteUser(statement);
                            break;
                        case "4":
                            updateUser(statement);
                            break;
                    }
                } while (!value.equals("5"));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }}
    }

    private static void deleteUser(Statement s) throws SQLException {
        System.out.println("Podaj ID użytkownika");
        final String id = scanner.nextLine();
        final String delete = " DELETE FROM Users WHERE ID= '" + id + "';";
        s.executeUpdate(delete);
    }

    private static void updateUser(Statement stmt) throws SQLException {

        System.out.println("Podaj ID użytkownika");
        String id = scanner.nextLine();

        System.out.println("Login: ");
        String login = scanner.nextLine();

        System.out.println("Hasło: ");
        String haslo = scanner.nextLine();

        System.out.println("E-mail:");
        String email = scanner.nextLine();

        String query = " UPDATE Users SET login = '" + login + "' , haslo = '" + haslo + "', email = '" + email + "' WHERE ID= '" + id + "';";
        stmt.executeUpdate(query);
    }

    private static void insertUser(Statement stmt) throws SQLException {

        System.out.println("Login: ");
        String login = scanner.nextLine();

        System.out.println("Hasło: ");
        String haslo = scanner.nextLine();

        System.out.println("E-mail:");
        String email = scanner.nextLine();

        String query = " INSERT INTO Users (login, haslo, email) VALUES ('" + login + "', '" + haslo + "', '" + email + "')";
        stmt.executeUpdate(query);
    }

    private static void getUsersList(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT ID, login, haslo, email FROM Users");
        System.out.println("ID      login       haslo       email");
        String id;
        String login;
        String haslo;
        String email;
        while (resultSet.next()) {
            id = String.valueOf(resultSet.getInt("ID"));
            login = resultSet.getString("login");
            haslo = resultSet.getString("haslo");
            email = resultSet.getString("email");

            System.out.println(id + "    " + login + "    " + haslo + "    " + email);
        }
        resultSet.close();
    }
}