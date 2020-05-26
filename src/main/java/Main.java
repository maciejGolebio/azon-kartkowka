import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Main {
    public final static String API_KEY_VALUE = "nxr1HOr5ZgMhT4epa43OxoPfv8HskX8TCv1cASS2";
    public final static String API_KEY = "X-Api-Key";
    public final static String ADDRESS = "https://api.e-science.pl/api/azon/";
    @SneakyThrows
    public static void print_title_id_submitter_by_id(String entity, long id){
        URL myURL = new URL(ADDRESS+entity+"/"+id+"/");
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
        System.out.println();
        System.out.println("title = " + jsonObject.getString("title") + "\n");
        System.out.println("id = " + jsonObject.getInt("pk") + "\n");
        System.out.println("deponujacy = " + jsonObject.getString("submitter"));

    }

    public static void main(String[] args) {
        String entity = "entry";
        long id = 16138L;
        Main.print_title_id_submitter_by_id(entity,id);
    }
}
