package addressbook;


import addressbook.frame.MainFrame;

import javax.swing.*;

public class App {
    private static void createGUI()
    {
//		JFrame frame = new LoginFrame(); // 启动登录界面
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        // 此段代码间接地调用了 createGUI()
        javax.swing.SwingUtilities.invokeLater(App::createGUI);

    }
}
