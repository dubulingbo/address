package addressbook.util;

import addressbook.entity.ContactPerson;

import java.io.*;
import java.util.*;

public class FileOperation {
    private final static String ENCODING = "UTF-8";
    private final static String TXT_FILE_SUFFIX = ".txt";
//    private static String CSV_FILE_SUFFIX = ".csv";

    /**
     * 从文本文件中一行一行读取数据
     *
     * @param filePath 读取的文件路径
     * @return 将文件的每一行内容存入 String，每一行放入 Vector 容器中
     * @throws IOException 路径是目录时抛出异常
     */
    private static Vector<String> readTxtFile(String filePath) throws IOException {
        File file = new File(filePath);
        Vector<String> res = new Vector<>();
        if (FileOperation.isTxtFile(file)) {
            InputStreamReader fr = new InputStreamReader(new FileInputStream(file), ENCODING); // 指定以UTF-8编码读入
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    res.add(line);
                }
            }
            br.close();
            fr.close();
        }
        return res;
    }

    /**
     * 将String数据一行一行写入文本文件
     *
     * @param filePath 要写入文件的路径，不存在的文件能自动创建，文件原有的内容会覆盖掉
     * @param content  写入的内容
     * @throws IOException 当路径是目录时会抛异常
     */
    private static void writeTxtFile(String filePath, Vector<String> content) throws IOException {
        File file = new File(filePath);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), ENCODING);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String line : content) {   // 会覆盖原有的数据
            bw.write(line);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    private static boolean isTxtFile(File file) {
        return file.isFile() && file.getName().endsWith(TXT_FILE_SUFFIX);
    }


    // 在文本文件的头部添加新增的内容
    public static void insertCPersonTxtFileRow(ContactPerson insertPerson) throws IOException {
        // 先读取文件，再将改变后的内容写入文件
        Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
        // 在第一行插入数据
        strVec.add(0, insertPerson.decode());
        FileOperation.writeTxtFile(Constant.ALL_PERSON_INFO_FILEPATH, strVec);
    }

    // 删除文本文件的某行内容
    public static void deleteCPersonTxtFileRow(String key) throws IOException {
        // 删除容器中的记录
        Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
        int totalRow = strVec.size();

        assert totalRow > 0;

        for (int i = 0; i < totalRow; i++) {
            if (key.equals(strVec.get(i).split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()])) {
                strVec.removeElementAt(i);
                break;
            }
        }
        // 将删除后的数据写入文件
        if (strVec.size() == 0) {
            File file = new File(Constant.ALL_PERSON_INFO_FILEPATH);
            file.delete();
            file.createNewFile();
        } else {
            FileOperation.writeTxtFile(Constant.ALL_PERSON_INFO_FILEPATH, strVec);
        }
    }

    // 修改文本文件中某一行内容
    public static void modifyCPersonTxtFileRow(ContactPerson modifyPerson) throws IOException {
        Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
        int totalRow = strVec.size();

        assert (totalRow > 0);

        for (int i = 0; i < totalRow; i++) {
            if (modifyPerson.getName().equals(strVec.get(i).split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()])) {
                strVec.setElementAt(modifyPerson.decode(), i);
                break;
            }
        }
        // 将改变写入文件
        FileOperation.writeTxtFile(Constant.ALL_PERSON_INFO_FILEPATH, strVec);
    }

    /**
     * 查询文本文件中的某些行（过滤条件：按名称模糊查询）
     *
     * @param name 使用名称作为模糊查询
     * @return 联系人列表 List<ContactPerson>
     * @throws IOException 读取文件异常
     */
    public static List<ContactPerson> queryCPersonTxtFile(String name, int dataFlag) throws IOException {
        Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
        List<ContactPerson> personList = new ArrayList<>();

        if(dataFlag == 1){  // 搜索全部联系人
            for (String s : strVec) {
                String tmp = s.split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()];
                if (tmp.contains(name)) {
                    ContactPerson person = new ContactPerson(s);
                    personList.add(person);
                }
            }
        }
        else if(dataFlag == 2){  // 搜索未分组的联系人
            for (String s : strVec) {
                String tmp1 = s.split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()];
                String tmp2 = s.split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.GROUP.ordinal()];
                if (tmp2.equals(Constant.BLANK_REPLACE) && tmp1.contains(name)) {
                    ContactPerson person = new ContactPerson(s);
                    personList.add(person);
                }
            }
        }
        else{
            System.out.println("=======queryCPersonTxtFile : dataFlag parameter error!");
        }
        return personList;
    }

    // 查询联系组文件里的所有内容（不分页）
    public static List<String> queryGroupTxtFile() {
        List<String> res = new ArrayList<>();
        try {
            Vector<String> strVec = FileOperation.readTxtFile(Constant.GROUP_INFO_FILEPATH);
            for (String s : strVec) {
                res.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // 通过主键查询某个联系人
    public static ContactPerson getCPersonByKey(String key) {
        Vector<String> strVec;
        try {
            strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);

            assert strVec.size() > 0;

            for (String s : strVec) {
                if (key.equals(s.split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()])) {
                    return new ContactPerson(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改联系人的头像
    public static void modifyCPersonAvatar(ContactPerson person, String sourceImgPath, String newImagePath) throws IOException {
        // 1.将文件存入系统指定文件夹
        File file = new File(sourceImgPath);
        FileInputStream fis = new FileInputStream(file);
        byte[] bit = new byte[fis.available()];
        fis.read(bit);
        File newFile = new File(newImagePath);
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(bit);
        fis.close();
        fos.close();
        // 删除原有头像
        if (person.getPhoto() != null && person.getPhoto().equals(""))
            new File(person.getPhoto()).delete();
        // 2.修改联系人文件
        person.setPhoto(newImagePath);
        modifyCPersonTxtFileRow(person);
    }

    // 判断联系人是否已存在
    public static boolean cPersonIsExist(String personName) {
        try {
            Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
            for (String s : strVec) {
                if (personName.equals(s.split(Constant.FILE_SPLITTER)[Constant.PERSON_INFO.NAME.ordinal()])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 将某个联系人复制到某个联系组
    public static void copyToGroup(String personName, String destGroup) throws IOException {
        Vector<String> strVec = FileOperation.readTxtFile(Constant.ALL_PERSON_INFO_FILEPATH);
        int totalRow = strVec.size();

        for (int i = 0; i < totalRow; i++) {
            ContactPerson person = new ContactPerson(strVec.get(i));
            if (personName.equals(person.getName())) {
                String g = person.getGroup();
                if (g.equals("")) { // 直接加入分组
                    person.setGroup(destGroup);
                } else { // 已经有分组了
                    // 判断是否已经加入过该分组，没有加入就添加该分组，否则直接退出
                    if (!g.contains(destGroup)) {
                        person.setGroup(String.join(", ", g, destGroup));
                    }
                }
                strVec.setElementAt(person.decode(), i);
                break;
            }
        }
        // 将改变写回文件
        FileOperation.writeTxtFile(Constant.ALL_PERSON_INFO_FILEPATH, strVec);
    }

    // 判断联系组是否存在
    public static boolean groupIsExist(String groupName) {
        try {
            Vector<String> strVec = FileOperation.readTxtFile(Constant.GROUP_INFO_FILEPATH);
            for(String s : strVec){
                if(groupName.equals(s)){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
