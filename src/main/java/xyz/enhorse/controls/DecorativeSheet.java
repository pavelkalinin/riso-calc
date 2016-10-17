package xyz.enhorse.controls;

import xyz.enhorse.datatypes.Inks;
import xyz.enhorse.datatypes.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Set;

/**
 * Created by PAK on 23.04.2014.
 */
public class DecorativeSheet extends JPanel {
    private Color outline;
    private Color fill;
    private Color[] front;
    private Color[] back;
    private String[] page;

    public DecorativeSheet(Color outline, Color fill, Page page) {
        super();
        this.outline = outline;
        this.fill = fill;
        this.page = new String[3];
        this.page[0] = page.getPaper().getName();
        this.page[1] = String.valueOf(page.getPaper().getFormat());
        this.page[2] = String.valueOf(page.getQuantity());
        this.front = new Color[page.getFront().getInks().size()];
        this.back = new Color[page.getBack().getInks().size()];
        fillColors(this.front, page.getFront().getInks());
        fillColors(this.back, page.getBack().getInks());
    }

    public void paint(Graphics g) {
        Graphics2D rect = (Graphics2D) g;
        rect.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Polygon poly = new Polygon();

        int width = this.getWidth();
        int height = this.getHeight();

        poly.addPoint(0, 0);
        poly.addPoint(width - width / 5, 0);
        poly.addPoint(width, width / 5);
        poly.addPoint(width, height);
        poly.addPoint(0, height);

        rect.setPaint(fill);
        rect.fillPolygon(poly);
        rect.setColor(outline);
        rect.drawPolygon(poly);

        width = this.getWidth() / 2 / front.length;
        height = this.getHeight() / 10;
        for (int i = 0; i < front.length; i++)
        {
            rect.setPaint(front[i]);
            rect.fillRect(i * width, this.getHeight() - height*2, width, height);
            rect.setColor(outline);
            rect.drawRect(i * width, this.getHeight() - height*2, width, height);
        }

        width = this.getWidth() / 2 / back.length;
        height = this.getHeight() / 10;
        for (int i = 0; i < back.length; i++)
        {
            rect.setPaint(back[i]);
            rect.fillRect(i * width + this.getWidth() / 2, this.getHeight() - height, width, height);
            rect.setColor(outline);
            rect.drawRect(i * width + this.getWidth() / 2, this.getHeight() - height, width, height);
        }

        int x, y;
        for (int i = 0; i < page.length; i++) {
            Font font = new Font("Default", Font.PLAIN, this.getWidth()/10);
            AttributedString as = new AttributedString(page[i]);
            as.addAttribute(TextAttribute.FONT, font);
            FontMetrics fm = rect.getFontMetrics(font);
            x = (getWidth() - fm.stringWidth(page[i])) / 2;
            if (i < page.length / 2) {
                y = getHeight() / 2 - fm.getHeight() * (i + 1);
            } else {
                y = getHeight() / 2 + fm.getHeight() * (i - 1);
            }
            rect.drawString(as.getIterator(), x, y);
        }
    }

    private void fillColors(Color[] colors, Set<Inks> inks) {
        int i = 0;
        for (Inks ink : inks)
        {
            colors[i++] = Inks.getColor(ink);
        }
    }
}
