package addressbook.frame.person;

import addressbook.entity.ContactPerson;
import addressbook.util.Constant;
import addressbook.util.FileOperation;
import com.dublbo.jpSwing.JpToaster;
import com.dublbo.jpSwing.thread.JpShortTask;

public class CPersonUpAvatarTask extends JpShortTask {
    CPersonInfoPopupPanel root;
    String path;  // 新文件路径
    public CPersonUpAvatarTask(CPersonInfoPopupPanel ui){
        this.root = ui;
    }
    @Override
    protected void doInBackground() throws Exception {
        ContactPerson person = (ContactPerson) this.args[0];
        String oldPath = (String) this.args[1];
        this.path = (String) this.args[2];
        FileOperation.modifyCPersonAvatar(person, oldPath, path);
        System.out.println("已修改头像，联系人：" + person.getName());
    }

    @Override
    protected void done() {
        if(this.err != null){
            JpToaster.show(root.ui,JpToaster.ERROR,err.getMessage());
            return;
        }
        JpToaster.show(root.ui,JpToaster.INFO,"修改成功！");
        // 让照片回显，并修改当前存储对象中的 photo值
        root.oldPerson.setPhoto(path);
        root.imageView.setImage(Constant.loadImage(path));
    }
}
