package com.blixmark.utilites;

import com.blixmark.Config;
import com.blixmark.MainUIWindow;
import com.blixmark.controller.LoginController;
import javax.swing.*;
import java.util.List;

public class OnAppCreate {
    public OnAppCreate() {
        JFrame frame = new JFrame();
        JPanel jPanel = new JPanel();
        jPanel.setBorder(null);
        frame.setUndecorated(true);
        ImageIcon imageIcon = new ImageIcon(Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "img" + Config.separator + "hero.jpg");
        jPanel.add(new JLabel(imageIcon));
        frame.setContentPane(jPanel);

        frame.setAlwaysOnTop(true);
        frame.setSize(400,162);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new OnCreate(frame).execute();
    }

    static class OnCreate extends SwingWorker<Void, Integer> {
        JFrame frame;
        public OnCreate(JFrame frame) {
            this.frame = frame;
        }
        @Override
        protected void process(List<Integer> chunks) {
            int i = chunks.get(chunks.size() - 1);
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(10);
                if(i == 20)
                   try {
                       new ErrorScanner();
                   } catch (Exception exception) {
                       frame.setVisible(false);
                       UserDialog.showError("Doslo je do greske! Developer mail: hrvanovic.dev@gmail.com");
                       System.out.println(Config.get("sqlhost") + Config.get("sqluser") + Config.get("sqlpass"));
                       exception.printStackTrace();
                       System.exit(0);
                   }
                publish(i);
            }
            frame.setVisible(false);
            frame.dispose();
            new LoginController(new MainUIWindow());
            return null;
        }

        /**
         * Metod koji se poziva pri zavrsetku pozadiniskih procesa.
         */
        @Override
        public void done() {
            try {
                get();
            } catch (Exception e) {
                new SystemLog().newLog(e);
            }
        }
    }
}