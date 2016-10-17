package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PAK on 03.04.2014.
 */
public class ColoredToggleButton extends JToggleButton {
    private ToggleColor colors = new ToggleColor();

    public ColoredToggleButton() {
        this.setColors(colors);
    }

    public ColoredToggleButton(Color selectedColor) {
        this.setColors(new ToggleColor(selectedColor));
    }

    public ColoredToggleButton(Color selectedColor, Color unselectedColor) {
        this.setColors(selectedColor, unselectedColor);
    }

    public ColoredToggleButton(ToggleColor colors) {
        this.setColors(colors);
    }

    public ColoredToggleButton(String text) {
        super(text);
        this.setColors(colors);
    }

    public ColoredToggleButton(String text, Color selectedColor) {
        super(text);
        this.setColors(new ToggleColor(selectedColor));
    }

    public ColoredToggleButton(String text, Color selectedColor, Color unselectedColor) {
        super(text);
        this.setColors(selectedColor, unselectedColor);
    }

    public ColoredToggleButton(String text, ToggleColor colors) {
        super(text);
        this.setColors(colors);
    }

    public ColoredToggleButton(Icon icon) {
        super(icon);
        this.setColors(colors);
    }

    public ColoredToggleButton(Icon icon, Color selectedColor) {
        super(icon);
        this.setColors(new ToggleColor (selectedColor));
    }

    public ColoredToggleButton(Icon icon, Color selectedColor, Color unselectedColor) {
        super(icon);
        this.setColors(selectedColor, unselectedColor);
    }

    public ColoredToggleButton(Icon icon, ToggleColor colors) {
        super(icon);
        this.setColors(colors);
    }

    public ColoredToggleButton(String text, Icon icon) {
        super(text, icon);
        this.setColors(colors);
    }

    public ColoredToggleButton(String text, Icon icon, Color selectedColor) {
        super(text, icon);
        this.setColors(new ToggleColor(selectedColor));
    }

    public ColoredToggleButton(String text, Icon icon, Color selectedColor, Color unselectedColor) {
        super(text, icon);
        this.setColors(selectedColor, unselectedColor);
    }

    public ColoredToggleButton(String text, Icon icon, ToggleColor colors) {
        super(text, icon);
        this.setColors(colors);
    }

    public void setSelectedColor(Color color){
        this.colors.setSelectedColor(color);
    }

    public Color getSelectedColor(){
        return this.colors.getSelectedColor();
    }

    public void setUnselectedColor(Color color){
        this.colors.setUnselectedColor(color);
    }

    public Color getUnselectedColor(){
        return this.colors.getUnselectedColor();
    }

    public void setColors(Color selectedColor, Color unselectedColor){
        this.setSelectedColor(selectedColor);
        this.setUnselectedColor(unselectedColor);
    }

    public void setColors(ToggleColor colors){
        this.setColors(colors.getSelectedColor(), colors.getUnselectedColor());
    }

    public ToggleColor getColors(){
        return this.colors;
    }

    private void paint(Graphics graphics, Color color){
        if (color != null){
            int w = getWidth();
            int h = getHeight();
            graphics.setColor(color);
            graphics.fillRect(0, 0, w, h);

            String s = this.getText();
            graphics.setColor(UIManager.getColor("controlText"));
            graphics.drawString(
                    s,
                    (w - graphics.getFontMetrics().stringWidth(s)) / 2 + 1,
                    (h + graphics.getFontMetrics().getAscent()) / 2 - 1);
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        if (this.isSelected()){
            this.paint(graphics, getSelectedColor());
        }
        else{
            this.paint(graphics, getUnselectedColor());
        }
    }

    @Override
    public String toString() {
        return this.colors.getSelectedColor().toString();
    }
}
