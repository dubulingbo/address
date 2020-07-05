package addressbook.frame.person;

import addressbook.frame.AllCPersonPanel;

import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.popup.JpPopupPanel;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 复制到组 弹出面板
 * @author DubLBo
 */
public class CopyToGroupPopupPanel extends JpPopupPanel implements ActionListener {
    public JpButton newGroupBtn = new JpButton("新建分组并复制");
    public AllCPersonPanel ui; // 主窗口
    public int[] selectedRows; // 选中行的行号
    List<String> groupList;

    public CopyToGroupPopupPanel(AllCPersonPanel ui, int[] selectedRows) {
        this.ui = ui;
        this.selectedRows = selectedRows;
        this.groupList = FileOperation.queryGroupTxtFile();

        // 动态设置窗口的高度
        this.setPreferredSize(new Dimension(150, 35 * (groupList.size() + 1)));
        this.setBorder(BorderFactory.createLineBorder(new Color(0x9AC0CD)));
        this.setLayout(new JpColumnLayout(4));
        this.setOpaque(true);
        this.setBackground(new Color(0xF8F8FF));

        initCenter();
    }

    // 设置头像显示区
    private void initCenter() {
        for (String g : groupList) {
            JpButton btn = new JpButton(g);
            this.add(btn, "30px");
            btn.addActionListener(this);
        }

        this.add(newGroupBtn, "30px");
        newGroupBtn.setBackground(Color.green);
        newGroupBtn.setOpaque(true);

        newGroupBtn.addActionListener((e) -> {
            // 新建分组
            NewGroupDialog dlg = new NewGroupDialog();
            String groupName = dlg.exec(ui);

            // 复制联系人到改组
            List<String> personNameList = new ArrayList<>();
            // 获取选择的联系人的姓名
            for (int i : selectedRows) {
                String tmp = (String) this.ui.tableView.getValueAt(i, 1);
                personNameList.add(tmp);
            }
            CopyToGroupTask task = new CopyToGroupTask(this.ui);
            task.execute(personNameList, groupName);

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JpButton btn = (JpButton) e.getSource();
        System.out.println("你点击了：" + btn.getText());
        List<String> personNameList = new ArrayList<>();
        // 获取选择的联系人的姓名
        for (int i : selectedRows) {
            String tmp = (String) this.ui.tableView.getValueAt(i, 1);
            personNameList.add(tmp);
        }
        CopyToGroupTask task = new CopyToGroupTask(this.ui);
        task.execute(personNameList, btn.getText());

    }
}
