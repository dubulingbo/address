package addressbook.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统常量类
 * @author DubLBo
 */
public class Constant {
    public static String FILE_SPLITTER = "\t"; // 文本文件中每行的分隔符
    public static String BLANK_REPLACE = "#";  // 空字段的替代字符串
    public static String DEFAULT_AVATOR_PATH = "data/pdata/tx/xxx.png";

    public static String ALL_PERSON_INFO_FILEPATH = "data/pdata/all_person_info.txt";
    public static String GROUP_INFO_FILEPATH = "data/gdata/group_info.txt";

    public enum PERSON_INFO {
        PHOTO, NAME, PHONE, EMAIL, GROUP, BIRTHDAY, OFFICE, ADDRESS, POSTCODE, REMARK
    }


    /**
     * 将系统中的空字符串 转化成 文件中存储的占位符
     * @param s 传进来的对象的值的字符串
     * @return String
     */
    public static String nullTransFS(String s){
        if(s == null || s.equals("")){
            return Constant.BLANK_REPLACE;
        }
        return s;
    }

    /**
     * 将文件中存储的占位符 转化成 系统中的空字符串
     * @param s 文件中字段的值
     * @return String
     */
    public static String FSTransNull(String s){
        if(s.equals(Constant.BLANK_REPLACE)){
            return "";
        }
        return s;
    }

    // 加载图标
    public static Image loadImage(String path)
    {
        try {
            if(path == null || path.equals(""))
                path = Constant.DEFAULT_AVATOR_PATH;
            return ImageIO.read(new File(path));
        }catch(Exception e)
        {
            System.out.println("加载图片出错! 检查图片资源的路径是否有误！ " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Constant.loadImage("data/pdata/tx/张无忌_1590077752757.jpg");
    }

}
