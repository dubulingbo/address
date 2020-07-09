package addressbook.frame;

import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpEditText;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * 导航栏类
 * @author Administrator
 * create_time 2020/07/09
 */
public class CPersonNavPanel extends JpPanel {
    // 导航栏上的按钮
    public JpButton addButton = new JpButton("新建联系人");
    public JpButton copyToGroupButton = new JpButton("复制到组 ∨");
    public JpButton delButton = new JpButton("删除");
    public JpButton reloadButton = new JpButton("刷新");
    public JpEditText searchEdit = new JpEditText();
    public JpButton searchButton = new JpButton("查询");

    public CPersonNavPanel(){
        this.setLayout(new JpRowLayout(5));
        this.padding(10);
        this.preferredHeight(50);
        this.setBgColor(Color.darkGray);

        this.add(addButton);
        this.add(delButton);
        this.add(copyToGroupButton);
        this.add(reloadButton);

        this.add(new JLabel(), "20px");
        this.add(searchEdit, "260px");
        this.add(searchButton, "auto");

        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyToGroupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchEdit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        searchEdit.setPlaceHolder(" 按姓名查找");
    }
}
