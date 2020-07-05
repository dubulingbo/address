package addressbook;


import addressbook.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class App {
    private static void createGUI()
    {
        // 启动登录界面
//		JFrame frame = new LoginFrame();
        JFrame frame = new MainFrame();
        frame.setVisible(true);

        // 居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ( screenSize.width - frame.getWidth())/2;
        int y = ( screenSize.height - frame.getHeight())/2;
        frame.setLocation(x,  y);
//		frame.setBounds(x,y,x*2/3,y*2/3);
    }

    public static void main(String[] args)
    {
        // 此段代码间接地调用了 createGUI()，具体原理在 Swing高级篇 里讲解
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                createGUI();
            }
        });

    }
}
