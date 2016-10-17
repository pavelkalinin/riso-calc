package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by PAK on 03.04.2014.
 */

public class ColoredToggleButtonPanel extends JPanel {
    private Map<Color, ColoredToggleButton> colorButtons = new HashMap<Color, ColoredToggleButton>();

    public ColoredToggleButtonPanel(LayoutManager layout, Color... colors)
    {
        super(layout);
        for (int i = 0; i < colors.length; i++) {
            this.addColor(colors[i]);
        }
        setFocusable(false);
    }

    public ColoredToggleButtonPanel(Color... colors) {
        this(new GridLayout(1, colors.length), colors);
    }

    public ColoredToggleButtonPanel(Set<Color> colors)
    {
        this(colors.toArray(new Color[0]));
    }


    public void addColor(Color color) {
        ColoredToggleButton btn = new ColoredToggleButton(color);
        btn.setFocusable(false);
        this.colorButtons.put(color, btn);
        add(btn);
    }

    public void removeColor(Color color) {
        if (this.colorButtons.containsKey(color)) {
            remove(colorButtons.get(color));
        }
    }

    public void setColorsState(boolean state){
        for (Map.Entry<Color, ColoredToggleButton> pair : colorButtons.entrySet()) {
            this.setColorState(pair.getKey(), state);
        }
    }

    public boolean isColorSelected(Color color) {
        return this.colorButtons.containsKey(color) ? (this.colorButtons.get(color)).isSelected() : null;
    }

    public void setColorState(Color color, boolean selected) {
        if (this.colorButtons.containsKey(color)) {
            this.colorButtons.get(color).setSelected(selected);
        }
    }

    public Set<Color> getColors() {
        Set<Color> result = new HashSet<Color>();

        for (Map.Entry<Color, ColoredToggleButton> pair : this.colorButtons.entrySet()) {
            result.add(pair.getKey());
        }

        return result;
    }

    public Set<Color> getSelectedColors() {
        Set<Color> result = new HashSet<Color>();

        for (Map.Entry<Color, ColoredToggleButton> pair : this.colorButtons.entrySet()) {
            if (pair.getValue().isSelected()) {
                result.add(pair.getKey());
            }
        }
        return result;
    }

}


