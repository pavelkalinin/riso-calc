package xyz.enhorse.datatypes;

import java.util.*;

/**
 * Created by PAK on 02.04.2014.
 */
public class Page {
    private static final String AS_STRING_FORMAT = "%s (%s:%s) - %d";

    private Paper paper;
    private long quantity;
    private Side front;
    private Side back;

    public Page(Paper paper, Side front, Side back) {
        this.setPaper(paper);
        this.front = front;
        this.back = back;
    }

    public Page(Paper paper, Side side) {
        this(paper, side, new Side());
    }

    public Page(Paper paper) {
        this(paper, new Side(), new Side());
    }

    public Paper getPaper() {
        return this.paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper != null ? paper : Paper.DEFAULT;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity >= 0 ? quantity : 0;
    }

    public Side getFront() {
        return this.front;
    }

    public Side getBack() {
        return this.back;
    }

    public void setFront(Side side) {
        this.front = side;
    }

    public void setBack(Side side) {
        this.back = side;
    }

    public void setSides(Side front, Side back) {
        this.setFront(front);
        this.setBack(back);
    }

    public List<Inks> getInks(){
        List<Inks> result = new ArrayList<Inks>();

        result.addAll(front.getInks());
        result.addAll(back.getInks());

        return result;
    }

    public String toString() {
        return String.format(AS_STRING_FORMAT, this.getPaper(), this.getFront(), this.getBack(), getQuantity());
    }

}
