package xyz.enhorse.datatypes;

/**
 * Created by PAK on 01.04.2014.
 */
public class Paper {
    private static final String UNKNOWN = "Unknown";
    private static final double DEFAULT_PRICE = 0.0D;
    private static final String AS_STRING_FORMAT = "\"%s\" (%s)";
    public static final  Paper DEFAULT = new Paper(UNKNOWN, Formats.A4);

    private String name;
    private Formats format;
    private double price;

    public Paper(String name, Formats format, double price) {
        this.name = name != null ? name : UNKNOWN;
        this.format = format;
        this.price = price;
    }

    public Paper(String name, Formats format) {
        this(name, format, DEFAULT_PRICE);
    }

    public String getName() {
        return this.name;
    }

    public Formats getFormat() {
        return this.format;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return String.format(AS_STRING_FORMAT, getName(), Formats.getName(this.format));
    }

}
