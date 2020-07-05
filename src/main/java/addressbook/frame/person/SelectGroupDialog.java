package addressbook.frame.person;

import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectGroupDialog extends JDialog {
    JDialog ui;

    List<String> selectedGroupNames = new ArrayList<>(); // 存取新的选择结果
    List<String> sgList; // 已经选了的分组
    List<String> groupList; // 所有分组 列表
    List<JCheckBox> groupFieldList = new ArrayList<>(); // 分组复选框列表
    JpButton saveBtn = new JpButton("保存");
    JpButton cancelBtn = new JpButton("取消并返回");

    public SelectGroupDialog(JDialog ui, List<String> sgList) {
        this.ui = ui;
        this.sgList = sgList;
        this.groupList = FileOperation.queryGroupTxtFile();
        this.setModal(true);
        this.setBackground(new Color(0x9400D3));
        this.setUndecorated(true);

        JpPanel root = new JpPanel();
        this.setContentPane(root);
        root.setLayout(new BorderLayout());

        root.add(initCenter(sgList), BorderLayout.CENTER);
        root.add(initBottom(), BorderLayout.PAGE_END);
    }

    private JComponent initCenter(List<String> sgList) {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(4));
        panel.padding(2);
        panel.preferredHeight(25);

        for (String g : groupList) {
            JCheckBox jCheckBox = new JCheckBox(g);
            jCheckBox.setOpaque(false);
            jCheckBox.setFocusPainted(false);
            jCheckBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            jCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.add(jCheckBox, "25px");
            groupFieldList.add(jCheckBox);
        }


        // 显示已勾选的分组
        if (sgList != null) {
            for (String s : sgList) {
                for (JCheckBox g : groupFieldList) {
                    if (s.equals(g.getText())) {
                        g.setSelected(true);
                        break;
                    }
                }
            }
        }

        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        panel.add(new JLabel(), "1w");
        panel.add(saveBtn);
        panel.add(cancelBtn);

        // 点保存按钮
        saveBtn.addActionListener((e) -> {
            // 查找选择了哪些组
            for (JCheckBox j : groupFieldList) {
                if (j.isSelected()) {
                    this.selectedGroupNames.add(j.getText());
                }
            }
            this.setVisible(false);
        });

        // 点 取消并返回 按钮
        cancelBtn.addActionListener((e) -> {
            selectedGroupNames = sgList;
            this.setVisible(false);
        });
        return panel;
    }

    public List<String> exec() {
        // 覆盖 owner 显示
        Rectangle frmRect = this.ui.getBounds();
        this.setBounds(frmRect.x, frmRect.y, frmRect.width, 30 * (groupList.size() + 3));

        // 显示窗口 ( 阻塞 ，直接对话框窗口被关闭)
        this.setVisible(true);

        // 此处阻塞 ... 等待用户点 OK，或关闭窗口才会往下执行 ...

        // 返回值
        return selectedGroupNames;
    }
}
