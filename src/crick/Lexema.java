package crick;

public class Lexema {
    String type;
    String value;
    int line;

    public Lexema() {
    }

    public Lexema(String type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    @Override
    public String toString() {
        return "[" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                '}';
    }
}
