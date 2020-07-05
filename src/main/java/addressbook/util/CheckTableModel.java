package addressbook.util;

import javax.swing.table.DefaultTableModel;

public class CheckTableModel extends DefaultTableModel {

    // * 根据类型返回显示空间
    // * 布尔类型返回显示checkbox
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }



    public void selectAllOrNull(boolean value) {
        for (int i = 0; i < getRowCount(); i++) {
            this.setValueAt(value, i, 0);
        }
    }

}
