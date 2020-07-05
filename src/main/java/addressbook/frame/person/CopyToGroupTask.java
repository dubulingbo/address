package addressbook.frame.person;

import addressbook.frame.AllCPersonPanel;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

import java.util.List;

public class CopyToGroupTask extends JpShortTask {
    AllCPersonPanel ui;


    public CopyToGroupTask(AllCPersonPanel ui) {
        this.ui = ui;
    }

    @Override
    protected void doInBackground() throws Exception {
        List<String> personNameList = (List<String>) this.args[0];
        String destGroup = (String) this.args[1];

        for (String personName : personNameList) {
            FileOperation.copyToGroup(personName, destGroup);
        }
        System.out.println("已将选择的联系人复制到联系组：" + destGroup);
    }

    @Override
    protected void done() {
        if (this.err != null) {
            JpToaster.show(this.ui, JpToaster.ERROR, err.getMessage());
            return;
        }
        // 更新显示列表
        this.ui.onReload();
    }
}
