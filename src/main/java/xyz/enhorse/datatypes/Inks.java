package xyz.enhorse.datatypes;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PAK on 01.04.2014.
 */
public enum Inks {
    BLACK("Black", Color.BLACK),
    GRAY("Gray", Color.GRAY),
    RED("Red", Color.RED),
    BLUE("Blue", Color.BLUE),
    GREEN("Green", Color.GREEN),
    ORANGE("Orange", Color.ORANGE),
    WHITE("White", Color.WHITE),
    UNDEFINED ("Undefined", null);

    private static final String AS_STRING_FORMAT = "%s";

    private String name;
    private Color color;

    public static final Inks[] RISO_COLORS = {Inks.BLACK, Inks.RED, Inks.GREEN, Inks.BLUE, Inks.ORANGE};

    private Inks(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public static Color getColor(Inks ink) {
        return ink.color;
    }

    public static Inks getInk(Color color) {
        for (Inks ink : Inks.values()) {
            if (ink.color == color) {
                return ink;
            }
        }
        return  Inks.UNDEFINED;
    };

    public static String getName(Inks ink){
        return ink.name;
    }

    public static boolean isBW(Inks ink) {
        return (ink == BLACK) || (ink == GRAY) || (ink == WHITE);
    }

    public static Color[] toColors(Inks[] inks) {
        if (inks != null) {
            Color[] result = new Color[inks.length];
            for (int i = 0; i < inks.length; i++) {
                result[i] = getColor(inks[i]);
            }
            return result;
        } else {
            return null;
        }
    }

    public static Set<Color> toColors(Set<Inks> inks) {
        if (inks != null) {
            Set<Color> result = new HashSet<>();
            for (Inks ink : inks) {
                result.add(getColor(ink));
            }
            return result;
        } else {
            return null;
        }
    }

    public static Inks[] fromColors(Color[] colors) {
        if (colors != null) {
            Inks[] result = new Inks[colors.length];
            for (int i = 0; i < colors.length; i++) {
                if  (getInk(colors[i]) != Inks.UNDEFINED) {
                    result[i] = getInk(colors[i]);
                };
            }
            return result;
        } else {
            return null;
        }
    }

    public static Set<Inks> fromColors(Set<Color> colors) {
        if (colors != null) {
            Set<Inks> result = new HashSet<>();
            for (Color color : colors) {
                if (getInk(color) != Inks.UNDEFINED) {
                    result.add(getInk(color));
                }
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(AS_STRING_FORMAT, getName(this), this.color.getRed(), this.color.getGreen(), this.color.getBlue());
    }
}
