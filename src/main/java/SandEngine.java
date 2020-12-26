import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class SandEngine {

    static JSONArray jsonArray = null;
    public static JSONArray getEngine() throws IOException {
        if(jsonArray==null){
            File file = new File("/home/phani_jsp/SAnD_SE/Engine.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String result = sb.toString();

            JSONObject jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("descriptors");
        }
        return jsonArray;

    }
}
