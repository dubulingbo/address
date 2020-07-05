package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.frame.AllCPersonPanel;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

public class CPersonUpdateTask extends JpShortTask {
    AllCPersonPanel ui;
    ContactPerson person;
    int row; //需要修改列表的行号

    public CPersonUpdateTask(AllCPersonPanel ui) {
        this.ui = ui;
    }

    @Override
    protected void doInBackground() throws Exception {
        this.person = (ContactPerson) this.args[0];
        this.row = (Integer) this.args[1];

        FileOperation.modifyCPersonTxtFileRow(this.person);
        System.out.println("修改一条记录，主键为：" + this.person.getPhone());

    }

    @Override
    protected void done() {
        if (this.err != null) {
            JpToaster.show(this.ui, JpToaster.ERROR, this.err.getMessage());
            return;
        }

        // 显示修改成功，并更新列表
        JpToaster.show(this.ui, JpToaster.INFO, "修改成功！");
        this.ui.tableView.setRow(this.row, this.person);
    }
}
