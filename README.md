# Streamui
## A POC of a web interface on flink streaming analysis, focused on Security Analytics

This is a POC built in java using flink to analyze log streams for patterns and alert. 

There is a web site at dash.html.  The most progress is in filters.  If you go to Filters.html, you can see live filters.  You can click on "New fitlers" and create a new one. Output shows up in data/results.  Add a "-test" arg to run tests.

### Running and output
You will need a web server to be able to post new fitlers and server the user web site.  I use a python web server for development.  Plans are to move it to S3 or other cloud storage.  Lots changing here, so I won't include too many details.

Check the repo issues to get a sense of what is working.

### References
This POC is based on a simple streaming example: https://github.com/joedatlive/flink-failed-password.  If you just want to see some flink filtering out security logs (without UI), this might be a good place to start.

