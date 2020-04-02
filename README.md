# Streamui
## A POC of a web interface on flink streaming analysis, focused on Security Analytics

This is a POC built in java using flink to analyze log streams for patterns and alert.  It it based on a POC of a failed password log stream - https://github.com/joedatlive/flink-failed-password

### Running and output
You will need to be able to post and get from the web site.  I use a python web server for development.  Plans are to move it to S3 or other cloud storage.  Lots changing here, so I won't include too many details.

The core streaming analysis info is in main.java, and can be run from test.java. Then check the data/results folder.
The web site is a work in progress.  dash.html is the home page and any pages that work have dummy data.  

Check the repo issues to get a sense of what is working.

