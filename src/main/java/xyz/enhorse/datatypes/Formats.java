package xyz.enhorse.datatypes;

/**
 * Created by PAK on 01.04.2014.
 */
public enum Formats {
    A0("A0", 1189, 841) {
        public double asA4() {
            return 16.0;
        };
        public int getWidth() {
            return getWidth(A0);
        };
        public int getHeight() {
            return getHeight(A0);
        };
        public String getName() {
            return getName(A0);
        };
    },
    A1("A1", 841, 594){
        public double asA4() {
            return 8.0;
        };
        public int getWidth() {
            return getWidth(A1);
        };
        public int getHeight() {
            return getHeight(A1);
        };
        public String getName() {
            return getName(A1);
        }
    },
    A2("A2", 594, 420){
        public double asA4() {
            return 4.0;
        };
        public int getWidth() {
            return getWidth(A2);
        };
        public int getHeight() {
            return getHeight(A2);
        };
        public String getName() {
            return getName(A2);
        }
    },
    A3("A3", 420, 297){
        public double asA4() {
            return 2.0;
        };
        public int getWidth() {
            return getWidth(A3);
        };
        public int getHeight() {
            return getHeight(A3);
        };
        public String getName() {
            return getName(A3);
        }
    },
    A4("A4", 297, 210){
        public double asA4() {
            return 1.0;
        };
        public int getWidth() {
            return getWidth(A4);
        };
        public int getHeight() {
            return getHeight(A4);
        };
        public String getName() {
            return getName(A4);
        }
    },
    A5("A5", 210, 148){
        public double asA4() {
            return 0.5;
        };
        public int getWidth() {
            return getWidth(A5);
        };
        public int getHeight() {
            return getHeight(A5);
        };
        public String getName() {
            return getName(A5);
        }
    },
    A6("A6", 148, 105){
        public double asA4() {
            return 0.25;
        };
        public int getWidth() {
            return getWidth(A6);
        };
        public int getHeight() {
            return getHeight(A6);
        };
        public String getName() {
            return getName(A6);
        }
    };

    private static final String AS_STRING_FORMAT = "%s(%dx%dmm)";

    private String format;
    private int width;
    private int height;

    private Formats(String format, int width, int height){
        this.format = format;
        this.width = width;
        this.height = height;
    }

    public static int getWidth(Formats format) {
        return format.width;
    }
    public abstract int getWidth();

    public static int getHeight(Formats format) {
        return format.height;
    }
    public abstract int getHeight();

    public static String getName(Formats format) {
        return format.format;
    }
    public abstract String getName();

    public abstract double asA4();

    public static Formats parseFormat(String name) {
        switch (name) {
            case "A0": return A0;
            case "A1": return A1;
            case "A2": return A2;
            case "A3": return A3;
            case "A4": return A4;
            case "A5": return A5;
            case "A6": return A6;
            default: throw new NumberFormatException("No such format");
        }
    }

    public static Formats[] available(){
        return new Formats[] {A6, A5, A4, A3, A2, A1, A0};
    }

    @Override
    public String toString() {
        return String.format(AS_STRING_FORMAT, getName(this), getWidth(this), getHeight(this));
    }
}
