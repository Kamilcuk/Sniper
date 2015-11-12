/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class Board extends JPanel {
    
    private Image tlo;

    public Board() {
        initBoard();
    }
    
     private void initBoard() {
        
        loadImage();
        
        int w = tlo.getWidth(this);
        int h = tlo.getHeight(this);
        setPreferredSize(new Dimension(w, h));        
    }

    private void loadImage() {
        
        ImageIcon ii = new ImageIcon("images/terrain/base_0.png");
        tlo = ii.getImage();        
    }    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

         g.drawImage(tlo, 0, 0, null);
        drawDonut(g);
    }

    private void drawTlo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        
    }
    private void drawDonut(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        Dimension size = getSize();
        double w = size.getWidth();
        double h = size.getHeight();

        Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 130);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.gray);

        for (double deg = 0; deg < 360; deg += 5) {
            AffineTransform at
                    = AffineTransform.getTranslateInstance(w/2, h/2);
            at.rotate(Math.toRadians(deg));
            g2d.draw(at.createTransformedShape(e));
        }
    }
}
