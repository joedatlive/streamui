package streams;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import com.google.gson.*;

public class FilterManager {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("****Filter Manager Launching");
            System.out.println("****Reading filters");
            //Read filters.json and instantiate a filter object
            Gson filterjson = new Gson();
            String filterfile = "src/web/data/config/filters.json";
            BufferedReader fr = new BufferedReader(
					new FileReader(filterfile));
            Filter f = filterjson.fromJson(fr, Filter.class);

            //Default other filter params.  These should be defaults that the user could override.
            f.alertSink = "data/results/alerts/results.txt";
            f.eventSink = "data/results/events/results.txt";
            f.parallelism = 1;
            
            //And for now, until we build some streams, we need to provide the data
            System.out.println("****Using samplestream.txt");
            BufferedReader sr = new BufferedReader(new FileReader("src/web/data/sample_sources/pwdstream.txt"));
            String str;
            List<String> list = new ArrayList<String>();
            while((str = sr.readLine()) != null){
                list.add(str);
            }
            String[] logArray = list.toArray(new String[0]);
            sr.close();
            SearchWindowFilter.ingest(f, logArray);

        } else {
            String arg1 = args[0];
            if (arg1.equals("-test")) {
                System.out.println("****Filter Manager Launching in test mode****");
                Test.filterTest();
            } else {
                System.out.println("Bad arguement(s), ending program.");
            }
        }
    }
}