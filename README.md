# flink-failed-password
## A POC using flink to analyze logs for volume of failed passwords in a time window.

The is a POC built in java using flink to analyze log streams for patterns and alert.

The core streaming analysis info is in main.java, including sample log data, the pattern to match, and new streams of Alerts and Info messages.

### Running and output
The program outputs to terminal and file.  
You can run from the Test.java file and you will see a bunch of flink status messages.  About 2/3rds down you shoud see ALERTS and EVENTS.

Sample Alert: {ALERT- 4 failed passwords in 1 minute=[(1,Jan 13 20:22:30,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:28,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:23:00,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:32,host1,sshd[21487]:,root,192.168.20.185)]}

The test file writes to "C:\\tmp\\flink\\test\\results.txt"

### Random notes
log4j and gson libraries are included for future (likely) use.
I created this in a Eclipse or IDEA last year, and have ported it to VScode with no problems.
