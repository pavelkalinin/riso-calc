package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PAK on 14.04.2014.
 */
public class Labeled {
    public static JPanel with(String text, JComponent component, Alignment captionAlignment) {
        JLabel caption = new JLabel(text);
        JPanel result = new JPanel();

        switch (captionAlignment) {
            case TOP: {
                result.setLayout(new GridLayout(1,1));
                result.setBorder(BorderFactory.createTitledBorder(text));
                result.add(component);
                break;
            }
            case BOTTOM: {
                result.setLayout(new GridLayout(2,1));
                result.add(component);
                result.add(caption);
                break;
            }
            case LEFT: {
                caption.setHorizontalAlignment(SwingConstants.RIGHT);
                result.setLayout(new GridLayout(1,2));
                result.add(caption);
                result.add(component);
                break;
            }
            case RIGHT: {
                caption.setHorizontalAlignment(SwingConstants.LEFT);
                result.setLayout(new GridLayout(1,2));
                result.add(component);
                result.add(caption);
            }
        }
        result.setFocusable(false);
        return result;
    }
}
