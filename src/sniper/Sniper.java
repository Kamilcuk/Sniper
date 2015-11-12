/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniper;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Sniper extends JFrame {
    
    public Sniper() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(640, 480);

        setTitle("Sniper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }    
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Sniper ex = new Sniper();
                ex.setVisible(true);
            }
        });
    }
}