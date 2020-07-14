package addressbook.frame;

import addressbook.entity.ContactPerson;
import addressbook.frame.person.*;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.*;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * 所有联系人主面板类：主要显示导航栏和联系人列表
 * @author Administrator
 * create_time 2020/07/09
 */
public class CPersonMainPanel extends JPanel {

    public CPersonNavPanel navPanel = new CPersonNavPanel();  // 导航栏
    public CPersonTableView tableView = new CPersonTableView();  // 用于显示数据的表格
    String groupFilter;  // 所属组过滤
    public JTabbedPane left_menu; // 左边菜单栏，有时候可能会需要修改

    public CPersonMainPanel(String groupFilter, JTabbedPane left_menu) {
        this.groupFilter = groupFilter;
        this.left_menu = left_menu;

        this.setLayout(new BorderLayout());

        addNavPanelAction(); // 给导航栏中的组件添加监听事件
        this.add(navPanel, BorderLayout.PAGE_START);

        this.add(initCenter(), BorderLayout.CENTER);
        this.add(initBottom(), BorderLayout.PAGE_END);
    }

    private void addNavPanelAction() {

        //给新建联系人按钮添加点击事件
        this.navPanel.addButton.addActionListener((e) -> onAdd());
        // 给删除按钮添加点击事件
        this.navPanel.delButton.addActionListener((e) -> onDelete());
        // 给复制到组按钮添加点击事件
        this.navPanel.copyToGroupButton.addActionListener((e) -> {
            int[] selectedRows = this.tableView.getSelectedRows();
            if (selectedRows != null && selectedRows.length > 0) {
                JpButton btn = (JpButton) e.getSource();
                CopyToGroupPopupPanel ctgp = new CopyToGroupPopupPanel(this, selectedRows);
                ctgp.showPopup(this, btn.getX(), btn.getY() + btn.getHeight());
            } else {
                JpToaster.show(this, JpToaster.WARN, "请先选择联系人！");
            }
        });
        // 给刷新按钮添加点击事件
        this.navPanel.reloadButton.addActionListener((e) -> onReload());
        // 监听搜索按钮事件
        this.navPanel.searchButton.addActionListener((e) -> {
            if (("").equals(this.navPanel.searchEdit.getText().trim())) {
                JpToaster.show(this, JpToaster.WARN, "请输入内容后搜索！");
                return;
            }
            onQuery();
        });

    }

    private JComponent initCenter() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(4));
        panel.padding(0);

        JScrollPane scroll = new JScrollPane(tableView);
        panel.add(scroll, "1w");

        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(new Color(0xFCFCFC));

        // 右键点击表格
        tableView.setRightClickedListener((view, row, col, evt) -> {
            // 保存点击的联系人对象（查询文件）
            ContactPerson person = FileOperation.getCPersonByKey(tableView.model.getValueAt(row, 1).toString());
            if (person == null) {
                JpToaster.show(view, JpToaster.ERROR, "系统内部错误，请刷新后重试！");
                return;
            }
            // 弹出右键菜单界面
            CPersonInfoPopupPanel infoPanel = new CPersonInfoPopupPanel(CPersonMainPanel.this, person, row, evt.getX(), evt.getY());
            infoPanel.showPopup(view, evt.getX(), evt.getY());
        });

        onReload();
        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);
        return panel;
    }

    public void onReload() {
        this.tableView.clear();
        CPersonQueryTask task = new CPersonQueryTask(this);
        task.execute("", this.groupFilter);
    }

    private void onDelete() {
        int[] rowIndexes = this.tableView.getSelectedRows();
        if (rowIndexes == null || rowIndexes.length <= 0) {
            JpToaster.show(this, JpToaster.WARN, "请选择要删除的联系人！");
            return;
        }

        String[] keys = new String[rowIndexes.length];
        for (int i = 0; i < rowIndexes.length; i++) {
            // 获取 姓名
            keys[i] = this.tableView.getValueAt(rowIndexes[i], 1).toString();
        }

        int result = JOptionPane.showConfirmDialog(  // 是：0 否：1 取消：-1
                this,
                "确认删除择联系人：\n『 " + String.join(" , ", keys) + " 』？",
                "提示",
                JOptionPane.YES_NO_OPTION
        );

        if (result == 0) { // 选择 “是”
            CPersonDeleteTask task = new CPersonDeleteTask(this);
            task.execute(rowIndexes, keys);
        }
    }

    private void onAdd() {
        CPersonAddDialog dlg = new CPersonAddDialog(tableView);
        ContactPerson person = dlg.exec();
        if (person != null) {
            CPersonInsertTask task = new CPersonInsertTask(this);
            task.execute(person);
        }
    }

    private void onQuery() {
        String filter = this.navPanel.searchEdit.getText().trim();  // 获取过滤条件
        System.out.println("filter=" + filter);
        this.tableView.clear();  // 清空联系人列表

        CPersonQueryTask task = new CPersonQueryTask(this);
        task.execute(filter, this.groupFilter);
    }
}
