package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.util.Constant;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpEditText;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.image.JpImageView;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CPersonEditDialog extends JDialog {
    JpImageView avatarField = new JpImageView();
    JpEditText nameField = new JpEditText();
    JpEditText phoneField = new JpEditText();
    JpEditText emailField = new JpEditText();
    JComboBox<String> groupField = new JComboBox<>();
    JpEditText birthdayField = new JpEditText();
    JpEditText officeField = new JpEditText();
    JpEditText addressField = new JpEditText();
    JpEditText postcodeField = new JpEditText();
    JTextField remarkField = new JTextField();

    JpButton okBtn = new JpButton("提交改变");
    JpButton cancelBtn = new JpButton("关闭");

    // 表单封装实体
    private ContactPerson oldPerson;
    private int flag; // 标记 person 是否被修改过 0:未修改  1:修改过

    public CPersonEditDialog(JComponent owner, ContactPerson person) {
        super(SwingUtilities.windowForComponent(owner));
        this.oldPerson = person;
        this.flag = 0;
        this.setTitle("编辑联系人信息");
        this.setModal(true);
        this.setSize(400, 550);

        JpPanel root = new JpPanel();
        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.padding(5);

        root.add(initCenter(), BorderLayout.CENTER);
        root.add(initBottom(), BorderLayout.PAGE_END);

    }

    private JComponent initCenter() {
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(4));
        panel.padding(5);
        panel.preferredHeight(30);

        // 头像显示区域
        JpPanel p1 = new JpPanel();
        p1.setLayout(new JpRowLayout(4));
        p1.preferredHeight(120);
        p1.setPreferredSize(new Dimension(120, 120));
        avatarField.setScaleType(JpImageView.FIT_CENTER_INSIDE);
        p1.add(avatarField, "1w");
        panel.add(p1);

        JpPanel p2 = new JpPanel();
        p2.setLayout(new JpRowLayout(4));
        p2.add(new JLabel("姓名: "), "76px");
        p2.add(nameField, "1w");
        nameField.setFont(font);
        nameField.setEditable(false);
        nameField.setBorder(null);
        panel.add(p2, "30px");


        JpPanel p3 = new JpPanel();
        p3.setLayout(new JpRowLayout(4));
        p3.add(new JLabel("电话号码: "), "76px");
        p3.add(phoneField, "1w");
        phoneField.setFont(font);
        phoneField.setEditable(false);
        phoneField.setBorder(null);
        panel.add(p3, "30px");

        JpPanel p4 = new JpPanel();
        p4.setLayout(new JpRowLayout(4));
        p4.add(new JLabel("邮箱: "), "76px");
        p4.add(emailField, "1w");
        emailField.setFont(font);
        panel.add(p4, "30px");

        JpPanel p5 = new JpPanel();
        p5.setLayout(new JpRowLayout(4));
        p5.add(new JLabel("所在组: "), "76px");
        p5.add(groupField);
        panel.add(p5, "30px");
        groupField.setFont(font);
        // 加载分组数据
        List<String> groups = FileOperation.queryGroupTxtFile();
        groupField.addItem("--请选择--");
        if (groups.size() > 0) {
            for (String g : groups) {
                groupField.addItem(g);
            }
        }

        JpPanel p6 = new JpPanel();
        p6.setLayout(new JpRowLayout(4));
        p6.add(new JLabel("出生日期: "), "76px");
        p6.add(birthdayField, "1w");
        birthdayField.setFont(font);
        panel.add(p6, "30px");

        JpPanel p7 = new JpPanel();
        p7.setLayout(new JpRowLayout(4));
        p7.add(new JLabel("工作单位: "), "76px");
        p7.add(officeField, "1w");
        officeField.setFont(font);
        panel.add(p7, "30px");

        JpPanel p8 = new JpPanel();
        p8.setLayout(new JpRowLayout(4));
        p8.add(new JLabel("家庭住址: "), "76px");
        p8.add(addressField, "1w");
        addressField.setFont(font);
        panel.add(p8, "30px");

        JpPanel p9 = new JpPanel();
        p9.setLayout(new JpRowLayout(4));
        p9.add(new JLabel("所在地邮编: "), "76px");
        p9.add(postcodeField, "1w");
        postcodeField.setFont(font);
        panel.add(p9, "30px");

        JpPanel p10 = new JpPanel();
        p10.setLayout(new JpRowLayout(4));
        p10.add(new JLabel("备注: "), "76px");
        p10.add(remarkField, "1w");
        remarkField.setFont(font);
        panel.add(p10, "30px");

        if (this.oldPerson != null) {
            avatarField.setImage(Constant.loadImage(oldPerson.getPhoto()));
            nameField.setText(oldPerson.getName());
            phoneField.setText(oldPerson.getPhone());
            emailField.setText(oldPerson.getEmail());
            if (oldPerson.getGroup() == null) {
                groupField.setSelectedIndex(0);
            } else {
                groupField.setSelectedItem(oldPerson.getGroup());
            }
            birthdayField.setText(oldPerson.getBirthday());
            officeField.setText(oldPerson.getOffice());
            addressField.setText(oldPerson.getAddress());
            postcodeField.setText(oldPerson.getPostcode());
            remarkField.setText(oldPerson.getRemark());
        }
        return panel;
    }

    private JComponent initBottom() {
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        panel.add(new JLabel(), "1w");
        panel.add(cancelBtn);
        panel.add(okBtn);
        okBtn.setBackground(new Color(123, 104, 238));

        cancelBtn.addActionListener((e) -> this.setVisible(false));

        okBtn.addActionListener((e) -> {
            if (isModify()) { // 若用户什么也没修改就点了提交按钮，则此时应该不要执行之后的修改任务线程
                this.oldPerson = getValue(); //重新修改 person 中的值
                flag = 1;
                if (oldPerson != null) {
                    this.setVisible(false);  //隐藏对话框
                }
            } else {
                JpToaster.show(this, JpToaster.WARN, "请修改后提交！");
            }
        });
        return panel;
    }

    private boolean isModify() {
        boolean f = false; // 默认未修改过
        if (!(oldPerson.getName().equals(nameField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getPhone().equals(phoneField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getEmail().equals(emailField.getText().trim()))) {
            f = true;
        }
        String s = groupField.getSelectedIndex() == 0 ? "" : ((String) groupField.getSelectedItem()).trim();
        if (!(oldPerson.getGroup().equals(s))) {
            f = true;
        }
        if (!(oldPerson.getBirthday().equals(birthdayField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getOffice().equals(officeField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getAddress().equals(addressField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getPostcode().equals(postcodeField.getText().trim()))) {
            f = true;
        }
        if (!(oldPerson.getRemark().equals(remarkField.getText().trim()))) {
            f = true;
        }
        return f;
    }

    private ContactPerson getValue() {
        ContactPerson newPerson = new ContactPerson();
        newPerson.setPhoto(oldPerson.getPhoto());
        newPerson.setName(nameField.getText().trim());
        newPerson.setPhone(phoneField.getText().trim());
        newPerson.setEmail(emailField.getText().trim());
        String s = groupField.getSelectedIndex() == 0 ? "" : ((String) groupField.getSelectedItem()).trim();
        newPerson.setGroup(s);
        newPerson.setBirthday(birthdayField.getText().trim());
        newPerson.setOffice(officeField.getText().trim());
        newPerson.setAddress(addressField.getText().trim());
        newPerson.setPostcode(postcodeField.getText().trim());
        newPerson.setRemark(remarkField.getText().trim());
        if (oldPerson.getName().isEmpty() || oldPerson.getPhone().isEmpty() || oldPerson.getEmail().isEmpty()) {
            JpToaster.show(this, JpToaster.WARN, "请填写完整信息！");
            return null;
        }

        // 其实还可以做一些验证
        // .......

        return newPerson;
    }

    // 显示当前对话框，主要设置了 显示位置，窗口大小
    public ContactPerson exec() {
        // 设置当前对话框的显示位置：相对 owner 居中显示
        Rectangle formRect = this.getOwner().getBounds();
        int width = this.getWidth();
        int height = this.getHeight();
        int x = formRect.x + (formRect.width - width) / 2;
        int y = formRect.y + (formRect.height - height) / 2;
        this.setBounds(x, y, width, height);

        // 显示窗口 ( 阻塞 ，直接对话框窗口被关闭)
        this.setVisible(true);

        // 此处阻塞 ... 等待用户点 OK，或关闭窗口才会往下执行 ...

        // 若用户点了 提交改变 ，则需要将改变写入文件和更新信息列表
        if (flag == 1) {
            return oldPerson;
        } else { //点 其他按钮
            return null;
        }
    }
}
