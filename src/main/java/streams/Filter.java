package streams;

public class Filter {
    String title;
    String description;
    String search;
    Integer count;
    Integer window;

    //constructs a new filter
    public Filter(String titleIn, String descriptionIn, String searchIn, int countIn, int windowIn) {
        title = titleIn;
        description = descriptionIn;
        search = searchIn;
        count = countIn;
        window = windowIn;
    }

    public String toString() {
        return this.title + this.description + this.search + this.count + this.window;
    }
}