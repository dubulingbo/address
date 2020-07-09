package addressbook.frame.person;

import addressbook.frame.CPersonMainPanel;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

public class CPersonDeleteTask extends JpShortTask {
    CPersonMainPanel ui;
    int[] rowIndexs;

    public CPersonDeleteTask(CPersonMainPanel ui) {
        this.ui = ui;
    }


    @Override
    protected void doInBackground() throws Exception {
        this.rowIndexs = (int[]) this.args[0];
        String[] keys = (String[]) this.args[1];

        // 执行文件内容删除
        for (String key : keys) {
            FileOperation.deleteCPersonTxtFileRow(key);
        }

        System.out.println("已删除 " + rowIndexs.length + "条记录！");
    }

    @Override
    protected void done() {
        if (this.err != null) {
            JpToaster.show(ui, JpToaster.ERROR, err.getMessage());
            return;
        }

        // 从表格中删除 这些行, 从后往前删除 （便于展示效果）
        for (int i = rowIndexs.length - 1; i >= 0; i--) {
            ui.tableView.model.removeRow(rowIndexs[i]);
        }
    }
}
