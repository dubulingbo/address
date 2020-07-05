package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.util.Constant;
import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpEditText;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.image.JpImageView;
import com.dublbo.jpSwing.layout.JpColumnLayout;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;

public class CPersonViewDialog extends JDialog {
    JpImageView avatarField = new JpImageView();
    JpEditText nameField = new JpEditText();
    JpEditText phoneField = new JpEditText();
    JpEditText emailField = new JpEditText();
    JpEditText groupField = new JpEditText();
    JpEditText birthdayField = new JpEditText();
    JpEditText officeField = new JpEditText();
    JpEditText addressField = new JpEditText();
    JpEditText postcodeField = new JpEditText();
    JTextField remarkField = new JTextField();

    JpButton cancelBtn = new JpButton("关闭");

    ContactPerson person;

    public CPersonViewDialog(JComponent owner, ContactPerson person){
        super(SwingUtilities.windowForComponent(owner));
        this.person = person;

        this.setTitle("查看联系人信息");
        this.setModal(true);
        this.setSize(400,550);

        JpPanel root = new JpPanel();
        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.padding(5);

        root.add(initCenter(),BorderLayout.CENTER);
        root.add(initBottom(),BorderLayout.PAGE_END);

    }

    private JComponent initCenter(){
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpColumnLayout(4));
        panel.padding(5);
        panel.preferredHeight(30);

        // 头像显示区域
        JpPanel p1 = new JpPanel();
        p1.setLayout(new JpRowLayout(4));
        p1.preferredHeight(120);
        p1.setPreferredSize(new Dimension(120,120));
        avatarField.setScaleType(JpImageView.FIT_CENTER_INSIDE);
        p1.add(avatarField,"1w");
        panel.add(p1);

        JpPanel p2 = new JpPanel();
        p2.setLayout(new JpRowLayout(4));
        p2.add(new JLabel("姓名: "),"76px");
        p2.add(nameField, "1w");
        nameField.setFont(font);
        nameField.setEditable(false);
        nameField.setBorder(null);
        panel.add(p2,"30px");


        JpPanel p3 = new JpPanel();
        p3.setLayout(new JpRowLayout(4));
        p3.add(new JLabel("电话号码: "),"76px");
        p3.add(phoneField,"1w");
        phoneField.setFont(font);
        phoneField.setEditable(false);
        phoneField.setBorder(null);
        panel.add(p3,"30px");

        JpPanel p4 = new JpPanel();
        p4.setLayout(new JpRowLayout(4));
        p4.add(new JLabel("邮  箱: "),"76px");
        p4.add(emailField,"1w");
        emailField.setFont(font);
        emailField.setBorder(null);
        emailField.setEditable(false);
        panel.add(p4,"30px");

        JpPanel p5 = new JpPanel();
        p5.setLayout(new JpRowLayout(4));
        p5.add(new JLabel("所在组: "),"76px");
        p5.add(groupField);
        groupField.setFont(font);
        groupField.setBorder(null);
        groupField.setEditable(false);
        panel.add(p5,"30px");

        JpPanel p6 = new JpPanel();
        p6.setLayout(new JpRowLayout(4));
        p6.add(new JLabel("出生日期: "),"76px");
        p6.add(birthdayField,"1w");
        birthdayField.setFont(font);
        birthdayField.setBorder(null);
        birthdayField.setEditable(false);
        panel.add(p6,"30px");

        JpPanel p7 = new JpPanel();
        p7.setLayout(new JpRowLayout(4));
        p7.add(new JLabel("工作单位: "),"76px");
        p7.add(officeField,"1w");
        officeField.setFont(font);
        officeField.setBorder(null);
        officeField.setEditable(false);
        panel.add(p7,"30px");

        JpPanel p8 = new JpPanel();
        p8.setLayout(new JpRowLayout(4));
        p8.add(new JLabel("家庭住址: "),"76px");
        p8.add(addressField,"1w");
        addressField.setFont(font);
        addressField.setBorder(null);
        addressField.setEditable(false);
        panel.add(p8,"30px");

        JpPanel p9 = new JpPanel();
        p9.setLayout(new JpRowLayout(4));
        p9.add(new JLabel("所在地邮编: "),"76px");
        p9.add(postcodeField,"1w");
        postcodeField.setFont(font);
        postcodeField.setBorder(null);
        postcodeField.setEditable(false);
        panel.add(p9,"30px");

        JpPanel p10 = new JpPanel();
        p10.setLayout(new JpRowLayout(4));
        p10.add(new JLabel("备注: "),"76px");
        p10.add(remarkField,"1w");
        remarkField.setFont(font);
        remarkField.setBorder(null);
        remarkField.setEditable(false);
        panel.add(p10,"30px");

        if (this.person != null) {
            avatarField.setImage(Constant.loadImage(person.getPhoto()));
            nameField.setText(person.getName());
            phoneField.setText(person.getPhone());
            emailField.setText(person.getEmail());
            groupField.setText(person.getGroup());
            birthdayField.setText(person.getBirthday());
            officeField.setText(person.getOffice());
            addressField.setText(person.getAddress());
            postcodeField.setText(person.getPostcode());
            remarkField.setText(person.getRemark());
        }
        return panel;
    }

    private JComponent initBottom(){
        JpPanel panel = new JpPanel();
        panel.setLayout(new JpRowLayout(4));
        panel.padding(2);
        panel.preferredHeight(30);

        panel.add(new JLabel(),"1w");
        panel.add(cancelBtn);

        cancelBtn.addActionListener( (e) ->{
            this.setVisible(false);  //隐藏对话框
        });

        return panel;
    }



    // 提交
    public void exec(){
        // 相对owner居中显示
        Rectangle frmRect = this.getOwner().getBounds();
        int width = this.getWidth();
        int height = this.getHeight();
        int x = frmRect.x + (frmRect.width - width)/2;
        int y = frmRect.y + (frmRect.height - height)/2;
        this.setBounds(x,y, width, height);

        // 显示窗口 ( 阻塞 ，直接对话框窗口被关闭)
        this.setVisible(true);

        // 此处阻塞 ... 等待用户点 OK，或关闭窗口才会往下执行 ...
    }
}
