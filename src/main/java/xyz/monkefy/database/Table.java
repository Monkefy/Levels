package xyz.monkefy.database;

public class Table {

    private String name;
    private String use;

    public Table(String name, String use) {
        this.name = name;
        this.use = use;
    }

    public String getName() {
        return this.name;
    }

    public String getUsage() {
        return "(" + this.use + ")";
    }

    public String getValues() {
        String a = "";
        String[] b = this.use.split(",");
        int i = 0;
        byte c;
        int j;
        String[] arrayString;
        for(j = (arrayString = b).length, c = 0; c < j;) {
            String string = arrayString[c];
            i++;
            String[] d = string.split(" ");
            a = String.valueOf(a) + ((d[0] == null) ? "" : (String.valueOf(d[0]) + ((i <= b.length - 1) ? "," : "")));
            c++;
        }

        return "(" + a + ")";
    }
}
