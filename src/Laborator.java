import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Laborator {

    private static final String userName = "gjergj";

    private static final String password = "gjergj";

    private static final String serverName = "localhost";

    private static final int portNumber = 3306;

    private static final String dbName = "student";

    private static final String tableName = "Klasa";

    private static Connection conn;

    public static void main(String[] args) throws SQLException {
        conn = initConnection();
        createTable();
        insertRecords("Gjergj", "Plepi", "Informatike", 6);
        insertRecords("Joan", "Plepi", "Informatike", 6);
        shfaqEmerDheId();
        separator();
        shfaqEmerDheProgramiStudimit();
        separator();
        shfaqStudentetQeFillojneMe("J");

        //TODO: scanner apo buffer?
        int id = 2;
        int nrKrediteve = 12;
        updateKreditetEStudentitMeId(id, nrKrediteve);

        id = 2;
        fshiStudentinMeId(id);
        separator();
        shfaqStudentetQeFillojneMe("");
    }

    private static void fshiStudentinMeId(int id) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE id=" + id;
        executeUpdate(query);
    }

    private static void updateKreditetEStudentitMeId(int id, int nrKrediteve) throws SQLException {
        String query = "UPDATE " + tableName + " SET nr_krediteve = " + nrKrediteve +  " WHERE id = " + id;
        System.out.println(query);
        executeUpdate(query);
    }

    private static void separator() {
        System.out.println("**************************");
        System.out.println("**************************");
        System.out.println("**************************");
    }

    private static void shfaqStudentetQeFillojneMe(String fillimi) {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE emri LIKE '" + fillimi + "%'";

        try {
            ResultSet rs = executeQuery(selectQuery);
            System.out.println("ID | Emri | Mbiemer | Programi i Studimit | Nr Krediteve");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("emri") +
                        " | " +  rs.getString("mbiemer") +
                        " | " + rs.getString("programi_studimit") +
                        " | " + rs.getInt("nr_krediteve"));
            }


            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void shfaqEmerDheProgramiStudimit() {
        String selectQuery = "SELECT emri, programi_studimit FROM " + tableName;
        try {
            ResultSet rs = executeQuery(selectQuery);
            System.out.println("Emri | Programi i Studimit");
            System.out.println("-------------------------");

            while (rs.next()) {
                System.out.println(rs.getString("emri") + " | " + rs.getString("programi_studimit"));
            }


            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void shfaqEmerDheId() {
        String selectQuery = "SELECT id, emri FROM " + tableName;
        try {
            ResultSet rs = executeQuery(selectQuery);
            System.out.println("ID | Emri");
            System.out.println("------------------");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("emri"));
            }


            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ResultSet executeQuery(String command) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(command);
    }

    private static void insertRecords(String emer, String mbiemer, String programiStudimit, int nrKrediteve) throws SQLException {
        String query = "INSERT INTO " + tableName +
                 " (emri, mbiemer, programi_studimit, nr_krediteve) VALUES ('" +
                emer + "', '" + mbiemer + "', '" + programiStudimit + "', " + nrKrediteve + ")";


        executeUpdate(query);
    }

    private static boolean executeUpdate(String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command);
            return true;
        } finally {

            if (stmt != null) { stmt.close(); }
        }
    }

    private static void createTable() throws SQLException {
        String createString =
                "CREATE TABLE IF NOT EXISTS " + tableName + "( " +
                        "ID INTEGER NOT NULL AUTO_INCREMENT, " +
                        "EMRI varchar(20) NOT NULL UNIQUE, " +
                        "MBIEMER varchar(20) NOT NULL, " +
                        "PROGRAMI_STUDIMIT varchar(40) NOT NULL, " +
                        "NR_KREDITEVE INT, " +
                        "PRIMARY KEY (ID))";

        executeUpdate(createString);
    }

    private static Connection initConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        return DriverManager.getConnection("jdbc:mysql://"
                        + serverName + ":" + portNumber + "/" + dbName,
                connectionProps);

    }

}
