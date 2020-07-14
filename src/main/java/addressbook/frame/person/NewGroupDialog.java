package addressbook.frame.person;

import addressbook.frame.CPersonMainPanel;
import addressbook.frame.group.GroupInsertTask;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpEditText;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;

public class NewGroupDialog extends JDialog {
    private CPersonMainPanel ui;
    String groupName;
    JpEditText groupField = new JpEditText();

    JpButton okBtn = new JpButton("确定");
    JpButton cancelBtn = new JpButton("取消");

    public NewGroupDialog(CPersonMainPanel ui) {
        this.ui = ui;
        this.setModal(true);
        this.setSize(400, 80);
        this.setUndecorated(true);

        JpPanel root = new JpPanel();
        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.padding(5);
        this.setBackground(new Color(0xFFE4E1));

        // 中间面板
        root.add(initCenter(), BorderLayout.CENTER);
        root.add(initBottom(), BorderLayout.PAGE_END);
    }


    private JComponent initCenter() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);


        JLabel label = new JLabel("输入联系组名称");
        label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel.add(label, "120px");

        groupField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        groupField.setOpaque(false);
        panel.add(groupField, "1w");
        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        panel.add(new JLabel(), "1w");
        panel.add(okBtn);
        panel.add(cancelBtn);

        okBtn.addActionListener((e) -> {
            String t = groupField.getText().trim();
            if (!t.equals("")) {
                // 验证分组是否已存在
                if (FileOperation.groupIsExist(t)) {
                    JpToaster.show(this, JpToaster.WARN, "该分组已存在，请输入其他名称！");
                }else{
                    GroupInsertTask task = new GroupInsertTask(this.ui);
                    task.execute(t);
                    this.groupName = t;  // 写入文件
                    this.setVisible(false);
                }
            }else{
                JpToaster.show(this, JpToaster.WARN, "请填写联系组名称！");
            }
        });

        cancelBtn.addActionListener((e) -> {
            this.setVisible(false);
        });
        return panel;
    }

    public String exec() {
        // 相对owner居中显示
        Rectangle frmRect = ui.getBounds();
        int width = this.getWidth();
        int height = this.getHeight();
        int x = frmRect.x + (frmRect.width - width) / 2;
        int y = frmRect.y + (frmRect.height - height) / 2;
        System.out.println(x + " " + y + " " + width + " " + height);
        this.setBounds(x, y, width, height);
        // 显示窗口 ( 阻塞 ，直到对话框窗口被关闭)
        this.setVisible(true);

        // 此处阻塞 ... 等待用户点 OK，或关闭窗口才会往下执行 ...

        // 返回值
        return groupName;
    }

}
