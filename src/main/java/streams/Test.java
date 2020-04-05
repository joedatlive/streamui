package streams;
import org.apache.commons.io.FileUtils;
import java.io.File;


public class Test {
    public static void filterTest() throws Exception {

        String[] logArray = new String[] {
            "Jan 13 20:22:28 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:29 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:30 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:32 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:32 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:23:00 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:23:10 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2", 
            "Jan 17 22:43:54 ip-172-0.0.0 sshd[2632]: pam_unix(sshd:session): session opened for user ec2-user by (uid=0)"};
        //set some variables to output test results    
        

        Filter f = new Filter("Test title", "Test description", "Test stream", "Test seaarch", 99, 99);
        f.alertSink = "data\\results\\alerts\\results.txt";
        f.eventSink = "data\\results\\events\\results.txt";

        //run the stream filter
        SearchWindowFilter.ingest(f, logArray);
        
        //read the file and see of results are what we expect = we will test for the time stamps and that Events and Alerts are in the right files
        File alertFile = new File(f.alertSink);
        File eventFile = new File(f.eventSink);
        String alertFileContent = FileUtils.readFileToString(alertFile);
        String eventFileContent = FileUtils.readFileToString(eventFile);

        String expectedTime1 = "1,Jan 13 20:22:28";
        String expectedTime2 = "1,Jan 13 20:22:29";
        
        boolean alertTime1match = alertFileContent.contains(expectedTime1);
        assert alertTime1match;
        boolean alertTime2match = alertFileContent.contains(expectedTime2);
        assert alertTime2match;
        boolean alertStringMatch = alertFileContent.contains("ALERT");
        assert alertStringMatch;

        boolean eventTime1match = eventFileContent.contains(expectedTime1);
        assert eventTime1match;
        boolean eventTime2match = eventFileContent.contains(expectedTime2);
        assert eventTime2match;
        boolean eventStringMatch = eventFileContent.contains("EVENT");
        assert eventStringMatch;

        System.out.println("****TEST PASS****");
    

    }
}

