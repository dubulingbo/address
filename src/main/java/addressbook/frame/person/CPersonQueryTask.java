package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.frame.AllCPersonPanel;
import addressbook.util.FileOperation;

import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

import java.util.List;

public class CPersonQueryTask extends JpShortTask {
    AllCPersonPanel ui;   // 主界面
    List<ContactPerson> personList;  // 存放查询到的联系人列表
    String filter;

    public CPersonQueryTask(AllCPersonPanel ui) {
        this.ui = ui;
    }

    @Override
    protected void doInBackground() throws Exception {
        this.filter = (String) this.args[0];
        // 获取本次查询的总记录数
        personList = FileOperation.queryCPersonTxtFile(filter);
        System.out.println("查询得到 " + personList.size() + " 条记录!");
    }

    @Override
    protected void done() {
        // 检查是否出错
        if (this.err != null) {
            JpToaster.show(ui, JpToaster.WARN, err.getMessage());
            return;
        }

        // 更新界面 显示查询结果
        for (ContactPerson person : personList) {
            this.ui.tableView.addRow(person);
        }
    }
}
