package xyz.enhorse.controls;

import java.awt.*;

/**
 * Created by PAK on 14.04.2014.
 */
public enum Alignment {
    TOP(BorderLayout.NORTH) {
        @Override
        public Alignment getOpposite() {
            return this.BOTTOM;
        }
    },
    BOTTOM(BorderLayout.SOUTH) {
        @Override
        public Alignment getOpposite() {
            return this.TOP;
        }
    },
    LEFT(BorderLayout.WEST) {
        @Override
        public Alignment getOpposite() {
            return this.RIGHT;
        }
    },
    RIGHT(BorderLayout.EAST) {
        @Override
        public Alignment getOpposite() {
            return this.LEFT;
        }
    };
    String align;

    private Alignment(String align) {
        this.align = align;
    }

    public abstract Alignment getOpposite();
}
