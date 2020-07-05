package addressbook.frame;

import addressbook.entity.ContactPerson;
import addressbook.frame.person.*;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.*;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;


public class AllCPersonPanel extends JPanel {
    // 导航栏上的按钮
    JpButton addButton = new JpButton("新建联系人");
    JpButton copyToGroupButton = new JpButton("复制到组 ∨");
    JpButton delButton = new JpButton("删除");
    JpButton reloadButton = new JpButton("刷新");
    JpEditText searchEdit = new JpEditText();
    JpButton searchButton = new JpButton("查询");

    // 用于显示数据的表格
    public CPersonTableView tableView = new CPersonTableView();

    public AllCPersonPanel() {
        this.setLayout(new BorderLayout());
        this.add(initTop(), BorderLayout.PAGE_START);
        this.add(initCenter(), BorderLayout.CENTER);
        this.add(initBottom(), BorderLayout.PAGE_END);
    }

    private JComponent initTop() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(5));
        panel.padding(10);
        panel.preferredHeight(50);
        panel.setBgColor(Color.darkGray);

        panel.add(addButton);
        panel.add(delButton);
        panel.add(copyToGroupButton);
        panel.add(reloadButton);

        panel.add(new JLabel(), "20px");
        panel.add(searchEdit, "260px");
        panel.add(searchButton, "auto");

        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyToGroupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchEdit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        searchEdit.setPlaceHolder(" 按姓名查找");

        //给新建联系人按钮添加点击事件
        addButton.addActionListener((e) -> onAdd());
        // 给删除按钮添加点击事件
        delButton.addActionListener((e) -> onDelete());
        // 给复制到组按钮添加点击事件
        copyToGroupButton.addActionListener((e) -> {
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
        reloadButton.addActionListener((e) -> onReload());
        // 监听搜索按钮事件
        searchButton.addActionListener((e) -> {
            if (("").equals(searchEdit.getText().trim())) {
                JpToaster.show(this, JpToaster.WARN, "请输入内容后搜索！");
                return;
            }
            onQuery();
        });

        return panel;
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
            CPersonInfoPopupPanel infoPanel = new CPersonInfoPopupPanel(AllCPersonPanel.this, person, row, evt.getX(), evt.getY());
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
        task.execute(""); // 默认显示第 1 页
    }

    private void onDelete() {
        int[] rowIndexs = this.tableView.getSelectedRows();
        if (rowIndexs == null || rowIndexs.length <= 0) {
            JpToaster.show(this, JpToaster.WARN, "请选择要删除的联系人！");
            return;
        }

        String[] keys = new String[rowIndexs.length];
        for (int i = 0; i < rowIndexs.length; i++) {
            // 获取 姓名
            keys[i] = this.tableView.getValueAt(rowIndexs[i], 1).toString();
        }

        int result = JOptionPane.showConfirmDialog(  // 是：0 否：1 取消：-1
                this,
                "确认删除择联系人：\n『 " + String.join(" , ", keys) + " 』？",
                "提示",
                JOptionPane.YES_NO_OPTION
        );

        if (result == 0) { // 选择 “是”
            CPersonDeleteTask task = new CPersonDeleteTask(this);
            task.execute(rowIndexs, keys);
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
        String filter = searchEdit.getText().trim();  // 获取过滤条件
        System.out.println("filter=" + filter);
        this.tableView.clear(); // 清空联系人列表

        CPersonQueryTask task = new CPersonQueryTask(this);
        task.execute(filter);
    }
}
