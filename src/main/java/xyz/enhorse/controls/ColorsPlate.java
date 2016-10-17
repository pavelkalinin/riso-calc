package xyz.enhorse.controls;

import xyz.enhorse.datatypes.Inks;
import xyz.enhorse.datatypes.Side;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created by PAK on 28.04.2014.
 */
public class ColorsPlate extends JTextField {
    private Color[] colors;
    private int height;

    public ColorsPlate(Side side, int height) {
        super();
        this.height = height;

        this.colors = new Color[side.getInks().size()];
        fillColors(this.colors, side.getInks());
    }

    private void fillColors(Color[] colors, Set<Inks> inks) {
        int i = 0;
        for (Inks ink : inks)
        {
            colors[i++] = Inks.getColor(ink);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D rect = (Graphics2D) g;
        for (int i = 0; i < colors.length; i++)
        {
            rect.setPaint(colors[i]);
            rect.fillRect(i * height, 0, height, height);
        }
    }

}
