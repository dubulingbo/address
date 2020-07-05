package addressbook.frame.person;


import addressbook.entity.ContactPerson;
import addressbook.frame.AllCPersonPanel;
import addressbook.util.Constant;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.image.JpImageView;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;
import com.dublbo.jpSwing.popup.JpPopupPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Date;

/**
 * 右键菜单显示面板
 *
 * @author DubLBo
 */
public class CPersonInfoPopupPanel extends JpPopupPanel {
    JLabel titleLabel = new JLabel();
    public JpButton editBtn = new JpButton("修改");
    public JpButton viewBtn = new JpButton("查看详情");

    public JpButton upAvatorBtn = new JpButton("修改头像");
    public JpImageView imageView = new JpImageView();  // 头像显示区

    public AllCPersonPanel ui; // 主窗口
    public ContactPerson oldPerson;    // 未修改之前的联系人对象
    public int selectedRow; // 选中的行的行号

    public int x;  // 窗口的位置
    public int y;

    // owner: 主窗口
    // initValue: 关联的 Book对象
    public CPersonInfoPopupPanel(AllCPersonPanel ui, ContactPerson initValue, int row, int x, int y) {
        this.ui = ui;
        this.oldPerson = initValue;
        this.selectedRow = row;
        this.x = x;
        this.y = y;

        this.setBorder(BorderFactory.createLineBorder(new Color(0x9AC0CD)));
        this.setLayout(new BorderLayout());
        this.setOpaque(true);
        this.setBackground(new Color(0xF8F8FF));

        this.add(initTop(), BorderLayout.PAGE_START);
        this.add(initCenter(), BorderLayout.CENTER);
        this.add(initBottom(), BorderLayout.PAGE_END);
    }

    // 计算窗口所需的大小
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(280, 240);
    }

    // 设置标题显示
    private JComponent initTop() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(6));
        panel.padding(5);
        panel.preferredHeight(30);

        panel.add(titleLabel, "1w");
        titleLabel.setText(oldPerson.getName());
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        return panel;
    }

    // 设置头像显示区
    private JComponent initCenter() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(6));
        panel.padding(5);
        panel.preferredHeight(120);

        panel.add(imageView, "1w");
        imageView.setBgColor(new Color(0xFFFFFF));
        imageView.setScaleType(JpImageView.FIT_CENTER_INSIDE);
        imageView.setImage(Constant.loadImage(oldPerson.getPhoto()));

        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(6));
        panel.padding(5);
        panel.preferredHeight(40);

        panel.add(editBtn, "20%");
        panel.add(viewBtn, "30%");
        panel.add(upAvatorBtn, "30%");

        // 把按钮字体改小一点
        editBtn.font = editBtn.font.deriveFont(12f);
        viewBtn.font = viewBtn.font.deriveFont(12f);
        upAvatorBtn.font = upAvatorBtn.font.deriveFont(12f);

        // 为 按钮 添加点击事件
        editBtn.addActionListener((e) -> onEdit());
        viewBtn.addActionListener((e) -> onView());
        upAvatorBtn.addActionListener((e) -> onUpAvatar());

        return panel;
    }

    // 修改头像
    private void onUpAvatar() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        fc.setMultiSelectionEnabled(false);
        int val = fc.showOpenDialog(null);    //文件打开对话框
        if (val == JFileChooser.APPROVE_OPTION) {
            //正常选择文件，设置文件回显，并存档

            String filePath = fc.getSelectedFile().getPath();
            System.out.println("选中的文件是：" + filePath);
            File file = new File(filePath);
            String newFilePath = "data/pdata/tx/" + oldPerson.getName() + "_" + new Date().getTime()
                    + file.getName().substring(file.getName().lastIndexOf("."));
            CPersonUpAvatarTask task = new CPersonUpAvatarTask(this);
            task.execute(oldPerson, filePath, newFilePath);
        }
        // 重现 右键菜单
        this.showPopup(this.ui.tableView, x, y);
    }

    private void onView() {
        this.hidePopup(); // 关闭 右键菜单弹出式窗口

        // 弹出 查看详情 信息框
        CPersonViewDialog dlg = new CPersonViewDialog(this.ui, this.oldPerson);
        dlg.exec();
    }

    // 点击'修改'按钮
    public void onEdit() {
        this.hidePopup(); // 关闭 右键菜单弹出式窗口

        // 弹出 修改 对话框
        CPersonEditDialog dlg = new CPersonEditDialog(this.ui, this.oldPerson);
        ContactPerson newPerson = dlg.exec();
        if (newPerson != null) {
            CPersonUpdateTask task = new CPersonUpdateTask(this.ui);
            task.execute(newPerson, this.selectedRow);
        }
    }
}
