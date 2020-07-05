package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpEditText;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CPersonAddDialog extends JDialog {
    JpEditText nameField = new JpEditText();
    JpEditText phoneField = new JpEditText();
    JpEditText emailField = new JpEditText();
    JpEditText groupField = new JpEditText();

    JpButton groupBtn = new JpButton("选择组");
    JpButton okBtn = new JpButton("确认添加");

    // 表单封装实体
    private ContactPerson formObj;
    // 当前页面被选中的分组名称
    List<String> sgList;

    public CPersonAddDialog(JComponent owner) {
        super(SwingUtilities.windowForComponent(owner));
        this.setTitle("添加新联系人");
        this.setModal(true);
        this.setSize(350, 300);

        JpPanel root = new JpPanel();
        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.padding(10);

        // 中间面板
        root.add(initCenter(), BorderLayout.CENTER);
        root.add(initBottom(), BorderLayout.PAGE_END);

    }

    private JComponent initCenter() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        JLabel prompt1 = new JLabel("*");
        prompt1.setFont(new Font("微软雅黑", Font.BOLD, 16));
        prompt1.setForeground(Color.RED);

        JLabel prompt2 = new JLabel("*");
        prompt2.setFont(new Font("微软雅黑", Font.BOLD, 16));
        prompt2.setForeground(Color.RED);

        JLabel prompt3 = new JLabel("*");
        prompt3.setFont(new Font("微软雅黑", Font.BOLD, 16));
        prompt3.setForeground(Color.RED);

        JpPanel p1 = new JpPanel();
        p1.setLayout(new JpRowLayout(4));
        p1.add(prompt1, "10px");
        p1.add(nameField, "1w");
        panel.add(p1, "30px");

        JpPanel p2 = new JpPanel();
        p2.setLayout(new JpRowLayout(4));
        p2.add(prompt2, "10px");
        p2.add(phoneField, "1w");
        panel.add(p2, "30px");

        JpPanel p3 = new JpPanel();
        p3.setLayout(new JpRowLayout(4));
        p3.add(prompt3, "10px");
        p3.add(emailField, "1w");
        panel.add(p3, "30px");

        JpPanel p4 = new JpPanel();
        p4.setLayout(new JpRowLayout(2));
        p4.padding(2);
        p4.add(groupBtn, "1w");
        panel.add(p4, "30px");

        nameField.setPlaceHolder("姓名");
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        phoneField.setPlaceHolder("电话号码");
        phoneField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        emailField.setPlaceHolder("邮箱地址");
        emailField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        groupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        groupBtn.addActionListener((e) -> {
            SelectGroupDialog ctgp = new SelectGroupDialog(this, sgList);
            this.sgList = ctgp.exec();
            if (sgList != null && sgList.size() != 0) { // 有选 联系分组
                // 重绘分组选择区域
                p4.removeAll();
                this.groupField.setText(String.join(", ", sgList));
                groupField.setFont(new Font("微软雅黑", Font.BOLD, 15));
                groupField.setEditable(false);
                groupField.setBorder(null);
                p4.add(groupField, "1w");
                groupBtn.setText("修改");
                p4.add(groupBtn, "50px");
                p4.validate();
                p4.repaint();
            }
        });

        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        panel.add(new JLabel(), "1w");
        panel.add(okBtn);

        okBtn.addActionListener((e) -> {
            formObj = getValue();
            if (formObj != null) {
                this.setVisible(false);  //隐藏对话框
            }
        });

        return panel;
    }

    private ContactPerson getValue() {
        ContactPerson person = new ContactPerson();
        person.setName(nameField.getText().trim());
        person.setPhone(phoneField.getText().trim());
        person.setEmail(emailField.getText().trim());
        person.setGroup(groupField.getText().trim());

        if (person.getName().isEmpty() || person.getPhone().isEmpty() || person.getEmail().isEmpty()) {
            JpToaster.show(this, JpToaster.WARN, "请填写完整信息！");
            return null;
        } else { // 判断该用户是否已存在
            if (FileOperation.cPersonIsExist(person.getName())) {
                JpToaster.show(this, JpToaster.ERROR, "该联系人姓名已存在，请重新添加！");
                return null;
            }
        }
        // 其实还可以做一些验证
        // .......
        return person;
    }

    // 提交
    public ContactPerson exec() {
        // 相对owner居中显示
        Rectangle frmRect = this.getOwner().getBounds();
        int width = this.getWidth();
        int height = this.getHeight();
        int x = frmRect.x + (frmRect.width - width) / 2;
        int y = frmRect.y + (frmRect.height - height) / 2;
        this.setBounds(x, y, width, height);
        // 显示窗口 ( 阻塞 ，直到对话框窗口被关闭)
        this.setVisible(true);

        // 此处阻塞 ... 等待用户点 OK，或关闭窗口才会往下执行 ...

        // 返回值
        return formObj;
    }
}
