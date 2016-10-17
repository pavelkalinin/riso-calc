package xyz.enhorse.forms;

import xyz.enhorse.controls.PageTable;
import xyz.enhorse.datatypes.*;

import javax.swing.*;

/**
 * Created by PAK on 17.04.2014.
 */
public class Test {
    public static  void sop(Object obj) {
        System.out.println(obj);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        Paper paper = new Paper("Snegurochka", Formats.A4);
        Side sideFront = new Side(Inks.RISO_COLORS);
        Side sideBack = new Side(Inks.RISO_COLORS);
        Page page = new Page(paper, sideFront, sideBack);
        page.setQuantity(100);

        PageTable pt = new PageTable();
        pt.addPage(page);
//        ColorsPlate cp = new ColorsPlate(page, 10);
        frame.add(pt);
        frame.setVisible(true);
    }
}
