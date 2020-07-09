package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.frame.CPersonMainPanel;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

public class CPersonInsertTask extends JpShortTask {

    CPersonMainPanel ui;
    ContactPerson person;

    public CPersonInsertTask(CPersonMainPanel ui){ this.ui = ui; }

    @Override
    protected void doInBackground() throws Exception {
        this.person = (ContactPerson) this.args[0]; // 在execute方法中传入的参数

        FileOperation.insertCPersonTxtFileRow(person);
        System.out.println("已添加一条记录！");

    }

    @Override
    protected void done() {
        // 检查是否出错
        if(this.err != null){
            JpToaster.show(ui,JpToaster.WARN, err.getMessage());
            return ;
        }
        System.out.println("回显==========" + this.person);
        // 刷新页面
        this.ui.onReload();
    }
}
