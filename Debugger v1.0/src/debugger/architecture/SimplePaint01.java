package debugger.architecture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SimplePaint01 {

    public static void main(String[] args) {
        new SimplePaint01();
    }

    public SimplePaint01() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                    try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setContentPane(new PaintPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class PaintPane extends JPanel {

        private int angle = 0;
        private Rectangle shape = new Rectangle(0, 0, 100, 100);
        private int i = 0;

        public PaintPane() {
            setBackground(Color.RED);
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    angle += 5;
                    repaint();
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int x = ((getWidth() - shape.width) / 2);
            int y = ((getHeight() - shape.height) / 2);

            shape.x = x;
            shape.y = y;


            if (i==3)
            	i=0;
            switch (i){
            case 0: g2d.setColor(Color.GREEN);
            break;
            case 1: g2d.setColor(Color.ORANGE);
            break;
            case 2: g2d.setColor(Color.MAGENTA);
            break;
            default: break;
            }
            g2d.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angle), x + (shape.width / 2), y + (shape.height / 2)));
            g2d.fill(shape);
            i++;

            g2d.dispose();
        }

    }

}