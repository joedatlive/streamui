package streams;

public class FilterManager {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("****Filter Manager Launching****");
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