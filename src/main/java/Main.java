import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * @author Maciej Gołebiowski 241496
 */
public class Main {
    public final static String API_KEY_VALUE = "nxr1HOr5ZgMhT4epa43OxoPfv8HskX8TCv1cASS2";
    public final static String API_KEY = "X-Api-Key";
    public final static String ADDRESS = "https://api.e-science.pl/api/azon/";
    private static final String ENTITY = "entry";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void addToDb(long id, String title, String submitter) {
        Connection connection = DriverManager
                .getConnection("jdbc:sqlite:D:\\Programming\\JAVA\\azon\\src\\main\\resources\\sample.db");
        Statement statement = connection.createStatement();
        statement.
                executeUpdate("create table if not exists azon(id integer , title TEXT, submitter varchar)");
        String query = "insert into azon values(" + id + ",'" + title.replaceAll(",", "") + "','" + submitter + "');";

        statement.executeUpdate(query);
        connection.close();
    }

    @SneakyThrows
    public static void printTitleIdSubmitterById(String entity, int id) {
        URL myURL = new URL(ADDRESS + entity + "/" + id + "/");
        URLConnection connection = myURL.openConnection();
        connection.setRequestProperty(API_KEY, API_KEY_VALUE);
        connection.setRequestProperty("accept", "application/json");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder json = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }
        in.close();
        JSONObject jsonObject = new JSONObject(json.toString());
        String title = jsonObject.getString("title");
        String submitter = jsonObject.getString("submitter");
        int uploaded_id = jsonObject.getInt("pk");

        System.out.println();
        System.out.println("title = " + title + "\n");
        System.out.println("id = " + uploaded_id + "\n");
        System.out.println("deponujacy = " + submitter);

        Main.addToDb(uploaded_id, title, submitter);

    }

    public static void main(String[] args) {
        int id = 1618;
        Main.printTitleIdSubmitterById(ENTITY, id);
    }
}
