package failedpwd;

public class Test {
    public static void main(String[] args) throws Exception {

        String[] logArray = new String[] {
            "Jan 13 20:22:28 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:29 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:30 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:32 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:22:32 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:23:00 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
            "Jan 13 20:23:10 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2", 
            "Jan 17 22:43:54 ip-172-0.0.0 sshd[2632]: pam_unix(sshd:session): session opened for user ec2-user by (uid=0)"};

        String alertSink = "C:\\tmp\\flink\\test\\alerts\\results.txt";
        String eventSink = "C:\\tmp\\flink\\test\\events\\results.txt";
        FailedPwd.ingest(logArray, alertSink, eventSink);
    

    }
}

