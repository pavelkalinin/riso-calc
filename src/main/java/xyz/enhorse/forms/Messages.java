package xyz.enhorse.forms;

import javax.swing.*;

/**
 * Created by PAK on 11.04.2014.
 */
public class Messages {
    public final static void consoleMsg(String message){
        System.out.println(message);
    }

    public final static void frameMsg(String message){
        JOptionPane.showMessageDialog(null, message);
    }
}
