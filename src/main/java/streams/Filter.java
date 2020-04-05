package streams;

public class Filter {
    String title;
    String description;
    String search;
    String stream;
    Integer count;
    Integer window;
    String alertSink;
    String eventSink;
    Integer parallelism;

    //constructs a new filter
    public Filter(String titleIn, String descriptionIn, String streamIn, String searchIn, int countIn, int windowIn) {
        title = titleIn;
        description = descriptionIn;
        stream = streamIn;
        search = searchIn;
        count = countIn;
        window = windowIn;
        alertSink = null;
        eventSink = null;
        parallelism = 1;
    }

    public String toString() {
        return this.title + this.description + this.stream + this.search + this.count + this.window + this.alertSink + this.eventSink + this. parallelism;
    }
}