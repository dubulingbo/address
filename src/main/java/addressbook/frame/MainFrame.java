package addressbook.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * 主界面类
 * 用于显示菜单栏和信息展示
 *
 * @author DubLBo
 */
public class MainFrame extends JFrame {
    /**
     * 左边菜单栏里的联系人和联系组选项卡
     * index 1：所有联系人选项
     * index 2：未分组联系人
     * index 4~n：每个联系组对应的一个选项卡
     */
    private JTabbedPane left_menu; // 左侧菜单栏

    public MainFrame() {
        super("My Address Book - Beta");
        // 获取电脑屏幕的尺寸，便于固定主界面的大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = screenWidth * 3 / 4;  // 主页面的宽
        int frameHeight = screenHeight * 3 / 4;  // 主页面的高

        // 居中显示
        this.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // 主页面的最底层容器
        this.getContentPane().add(initLeftMenu());
    }

    private JComponent initLeftMenu() {
        left_menu = new JTabbedPane(JTabbedPane.LEFT);
        left_menu.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
        left_menu.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 20;
            }

            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 60;
            }
        });

        left_menu.addTab("联系人管理", null);
        left_menu.add("所有联系人", null);
        left_menu.add("未分组联系人", null);
        left_menu.addTab("联系组管理", null);
        left_menu.setEnabledAt(0, false);
        left_menu.setEnabledAt(3, false);

        // 添加 联系组 分组
        // 这里只需要将内存中groupInfoData加载到面板上即可,因为这里的数据始终与文件中的数据保持同步
//        for (String groupInfo : groupInfoData) {
//            JPanel jPanel = new JPanel();
//            jPanel.setLayout(new BorderLayout());
//            jPanel.setBackground(Color.WHITE);
//            String tmpString = groupInfo.split(Constant.FILE_SPLITTER)[1];
//            if (!tmpString.isEmpty()) {
//                left_menu.add(tmpString, jPanel);
//                leftMenu_list.addElement(jPanel);
//            }
//        }

        left_menu.addChangeListener(e -> {
            int index = left_menu.getSelectedIndex();
            String title = left_menu.getTitleAt(left_menu.getSelectedIndex());
            System.out.println(index + "  " + title);
            if (index == 1 || index == 2) {
                left_menu.setComponentAt(index, new CPersonMainPanel(index));
            }
//                else if(index >= 4)
////					leftMenu_list.get(index-2).removeAll();
//                    showGroup(index - 2, title);
        });
        return left_menu;
    }
}
