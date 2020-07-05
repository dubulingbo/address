package addressbook.util;

import com.dublbo.jpSwing.JpButton;
import com.dublbo.jpSwing.JpPanel;
import com.dublbo.jpSwing.layout.JpRowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* 分页显示控件, 支持任意页数, 支持自动省略
 *
 * 比如，有15页时会显示 1 2 ... 5 6 (7) 8 9 ... 14 15
 * 其中，(7)表示当前页面码为7，显示前后5页，当页面过多时以省略号显示
 */
public class PageIndexBar extends JpPanel implements ActionListener {
    JLabel infoLabel = new JLabel();

    public PageIndexBar() {
        this.setLayout(new JpRowLayout(4));
        this.padding(2);
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.PLAIN));
    }

    /**
     * 设置分页控件上的显示页码
     * @param totalPageCount 总页数
     * @param pageIndex 当前页码（从 1 开始）
     * @param totalRow 总记录数
     */
    public void setPage(int totalRow, int totalPageCount, int pageIndex) {
        this.removeAll();  // 先清空分页控件上的组件

        this.add(new JLabel(), "1w"); // 左边添加弹簧, 使内容右对齐
        this.add(infoLabel, "auto");

        boolean skipped = false;  // 是否需要跳页显示

        for (int i = 1; i <= totalPageCount; i++) {
            // 如果页面数过多，则添加一个省略号
            if (i < pageIndex - 2 && i > 2) {  // 左边是否需要添加省略号 , 当前页码大于 2 则需要
                if (!skipped) this.add(new JpButton("..."));
                skipped = true;
                continue;
            }
            if (i > pageIndex + 2 && i <= totalPageCount - 2) {
                if (!skipped) this.add(new JpButton("..."));
                skipped = true;
                continue;
            }
            skipped = false;

            JpButton btn = new JpButton();
            this.add(btn);
            btn.setText(String.valueOf(i));
            btn.addActionListener(this);

            // 当前页码： 以高亮显示
            if (i == pageIndex) {
                btn.normal.bgColor = new Color(0x333333);
                btn.normal.textColor = new Color(0xFFFfff);
                btn.hover.bgColor = new Color(0x333333);
                btn.hover.textColor = new Color(0xFFFfff);
            }
        }
        infoLabel.setText("共 " + totalRow + " 条数据\t");
        this.validate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JpButton btn = (JpButton) e.getSource();
        int page = Integer.parseInt(btn.getText());
        if (pageClickListener != null) {
            pageClickListener.pageClicked(page);
        }
    }


    // 自定义事件支持
    public interface PageClickListener {
        void pageClicked(int page);
    }

    public PageClickListener pageClickListener;

    public void setPageClickListener(PageClickListener l) {
        this.pageClickListener = l;
    }
}
