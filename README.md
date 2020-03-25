# flink-failed-password
A POC using flink to analyze logs for volume of failed passwords in a time window.

The is a POC built in java using flink to analyze log streams for patterns and alert.

The core streaming analysis info is in main.java, including sample log data, the pattern to match, and new streams of Alerts and Info messages.

Info
The program outputs to terminal.  If you run Main.java, you will see a bunch of flink status in about 2/3rds down you shoud see Alerts.
Sample Alert: {ALERT- 4 failed passwords in 1 minute=[(1,Jan 13 20:22:30,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:28,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:23:00,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:32,host1,sshd[21487]:,root,192.168.20.185)]}

I created this in a Eclipse or IDEA last year, and have ported it to VScode with no problems.
