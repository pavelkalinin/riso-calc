package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PAK on 03.04.2014.
 */
public class ToggleColor {
    private static final Color DEFAULT_BACKGROUND = UIManager.getColor("ToggleButton.background");
    private static final Color DEFAULT_FOREGROUND = UIManager.getColor("ToggleButton.foreground");

    private Color selectedColor = DEFAULT_BACKGROUND;
    private Color unselectedColor = DEFAULT_FOREGROUND;

    public ToggleColor() {
        this.setColors(DEFAULT_FOREGROUND, DEFAULT_BACKGROUND);
    }

    public ToggleColor(Color selectedColor, Color unselectedColor) {
        this.setColors(selectedColor, unselectedColor);
    }

    public ToggleColor(Color selectedColor) {
        this.setColors(selectedColor, this.modifyColor(selectedColor));
    }

    public void setColors(Color selectedColor, Color unselectedColor) {
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getUnselectedColor() {
        return this.unselectedColor;
    }

    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
    }

    private Color modifyColor(Color color) {
        return color != Color.BLACK ? color.darker() : Color.GRAY;
    }

    @Override
    public String toString() {
        return "Toggle Colors (" +
                "active color=" + selectedColor +
                ", inactive color=" + unselectedColor +
                ')';
    }
}
