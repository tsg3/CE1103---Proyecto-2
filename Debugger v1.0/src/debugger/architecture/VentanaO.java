package debugger.architecture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaO implements ActionListener {

	static SimpleTrace s;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaO().createGui();
            }
        });
        s = new SimpleTrace();
    }

    public void createGui() {
        JFrame frame = new JFrame("Ejemplo Boton");
        frame.setSize(700, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        frame.getContentPane().add(panel, BorderLayout.WEST);
        GridBagConstraints c = new GridBagConstraints();

        JButton button1 = new JButton("Step Into");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(40, 40, 40, 40);
        panel.add(button1, c);
        button1.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
    	s.watcher.value=true;
    }
}