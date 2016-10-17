package xyz.enhorse.datatypes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PAK on 11.04.2014.
 */
public class Side {
    private static final String AS_STRING_FORMAT = "%s";

    private Set<Inks> inks = new HashSet<Inks>();

    public Side(Inks... inks) {
        this.setInks(inks);
    }

    public Side(Set<Inks> inks) {
        this.setInks(inks);
    }

    public Set<Inks> getInks() {
        return this.inks;
    }

    public void setInks(Inks... inks) {
        Collections.addAll(this.inks, inks);
    }

    public void setInks(Set<Inks> inks) {
        this.inks = inks;
    }

    public void addInk(Inks ink) {
        if (ink != null) {
            this.inks.add(ink);
        }
    }

    public String toString() {
        return String.format(AS_STRING_FORMAT, getInks());
    }

}
