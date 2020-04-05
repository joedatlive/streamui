# Streamui
## A POC of a web interface on flink streaming analysis, focused on Security Analytics

This is a POC built in java using flink to analyze log streams for patterns and alert.  

### Running and output
You will need to be able to post and get from the user web site.  I use a python web server for development.  Plans are to move it to S3 or other cloud storage.  Lots changing here, so I won't include too many details.

We have filters working from the website.  FilterManager reads the config filter and executes a stream.  Running FilterManager from an IDE works. Then check the data/results folder.  Add a "-test" arg to run tests. .
The web site is a work in progress. Filters.html is live. dash.html is the home page and any pages that work have dummy data.  

Check the repo issues to get a sense of what is working.

### References
This POC is based on a simple streaming example: https://github.com/joedatlive/flink-failed-password.  If you just want to see some flink filtering out security logs (without UI), this might be a good place to start.

