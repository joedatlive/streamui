package streams;

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
            String filterfile = "C:\\Users\\joed\\Documents\\Source\\secdur\\streamui\\src\\web\\filters.json";
            BufferedReader fr = new BufferedReader(
					new FileReader(filterfile));
            Filter f = filterjson.fromJson(fr, Filter.class);

            //Default other filter params.  These should be defaults that the user could override.
            f.alertSink = "data\\results\\alerts\\results.txt";
            f.eventSink = "data\\results\\events\\results.txt";
            f.parallelism = 1;
            //And for now, until we build some streams, we need to provide the data
            String[] logArray = new String[] {
                "Jan 13 20:22:28 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:22:29 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:22:30 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:22:32 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:22:32 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:23:00 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
                "Jan 13 20:23:10 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2", 
                "Jan 17 22:43:54 ip-172-0.0.0 sshd[2632]: pam_unix(sshd:session): session opened for user ec2-user by (uid=0)"};


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