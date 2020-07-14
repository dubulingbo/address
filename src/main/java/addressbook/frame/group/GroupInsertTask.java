package addressbook.frame.group;

import addressbook.frame.CPersonMainPanel;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

public class GroupInsertTask extends JpShortTask {
    private CPersonMainPanel ui;
    private String groupName;
    public GroupInsertTask(CPersonMainPanel ui){
        this.ui = ui;
    }

    @Override
    protected void doInBackground() throws Exception {
        this.groupName = (String) this.args[0];
        FileOperation.insertGroupTxtFileRow(groupName);
        System.out.println("插入分组文件：1 条记录");
    }

    @Override
    protected void done() {
        if(this.err != null){
            JpToaster.show(this.ui,JpToaster.ERROR,err.getMessage());
            return;
        }
        // 更新左边菜单栏
        this.ui.left_menu.addTab(this.groupName, null);
    }
}
