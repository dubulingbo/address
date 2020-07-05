package addressbook.frame;

import addressbook.entity.ContactPerson;
import addressbook.util.CheckHeaderCellRenderer;
import addressbook.util.CheckTableModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * 联系人列表
 * @author DubLBo
 */
public class CPersonTableView extends JTable {
    public DefaultTableModel model = new CheckTableModel();

    public CPersonTableView() {
        // 列标题
        model.addColumn("全选");
        model.addColumn("姓名");
        model.addColumn("电话号码");
        model.addColumn("邮箱地址");
        model.addColumn("所属组");
        this.setModel(model);

        this.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(this));
        this.getTableHeader().setReorderingAllowed(false);
        this.setRowHeight(30);
        this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        this.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 16));

        // 列宽
        this.setColumnWidth(0, 70);

    }

    public void addRow(ContactPerson row, int index) {
        Vector<Object> rowData = new Vector<>();
        rowData.add(false);
        // 此处显示的数据不能为 null
        rowData.add(row.getName() == null ? "" : row.getName());
        rowData.add(row.getPhone() == null ? "" : row.getPhone());
        rowData.add(row.getEmail() == null ? "" : row.getEmail());
        rowData.add(row.getGroup() == null ? "" : row.getGroup());

        if (index < 0)
            model.addRow(rowData); //在列表最后一行追加一条数据
        else
            model.insertRow(index, rowData); // 插入某行数据
    }

    public void addRow(ContactPerson row) {
        this.addRow(row, -1);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column != 0) return false;
        return true;
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        if (column != 0) return false;
        return true;
    }

    public void clear() {
        model.setNumRows(0);
    }

    // 设置某列的宽度
    private void setColumnWidth(int column, int width) {
        getColumnModel().getColumn(column).setMaxWidth(width);
        getColumnModel().getColumn(column).setMinWidth(width);
    }

    @Override
    public int[] getSelectedRows() {
        List<Integer> arr = new ArrayList<>();
        int rowCount = this.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if ((boolean) this.getValueAt(i, 0)) {
                arr.add(i);
            }
        }
        int[] res = arr.stream().mapToInt(Integer::valueOf).toArray();
        System.out.println(arr.toString());
        return res;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_RELEASED) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                // 获取点中的位置
                int row = this.rowAtPoint(e.getPoint());
                int col = this.columnAtPoint(e.getPoint());

                // 选中该行
                if (row >= 0 && row < this.getRowCount()) {
                    this.clearSelection();
                    this.addRowSelectionInterval(row, row);
                }

//				CPersonInfoPanel panel = new CPersonInfoPanel();
//				panel.showPopup(this, e.getX(), e.getY());
                if (rightClickedListener != null) {
                    rightClickedListener.clicked(this, row, col, e);
                }
            }
        }
        super.processMouseEvent(e);
    }

    public void setRow(int row, ContactPerson person) {
        this.setValueAt(person.getName() == null ? "" : person.getName(), row, 1);
        this.setValueAt(person.getPhone() == null ? "" : person.getPhone(), row, 2);
        this.setValueAt(person.getEmail() == null ? "" : person.getEmail(), row, 3);
        this.setValueAt(person.getGroup() == null ? "" : person.getGroup(), row, 4);
    }

    // 右键处理，自定义监听器
    public interface ClickListener {
        void clicked(JTable view, int row, int col, MouseEvent evt);
    }

    public ClickListener rightClickedListener;

    public void setRightClickedListener(ClickListener l) {
        this.rightClickedListener = l;
    }

}
