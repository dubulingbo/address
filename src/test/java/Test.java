import addressbook.util.Constant;

import java.io.File;

public class Test {
    public static void main(String[] args) {
//        System.out.println(Test.class.getResource("").getPath());
//        System.out.println(System.getProperty("user.dir"));
//        Integer i = new Integer(1000);
//        System.out.println(String.join("\t","123","456","789",i.toString()));
//        String tmp = "12131\t\t13412\t";
//        String[] arr = tmp.split("\t");
//        System.out.println(arr.length);
//        for(String a : arr)
//            System.out.println(a);
//        String[] ts = {"123","456","789"};
//        System.out.println(ts);
//        System.out.println(ts.toString());
//        System.out.println(String.join(" , ",ts));
//        tmp = "";
//        for(String s : ts){
//            tmp = String.join(" , ", s, tmp);
//        }
//        System.out.println(tmp);

//        File file = new File("HelloWorld.java");
//        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
//        System.out.println(suffix);
        String[] tmp = {"213abc","bcjdhz213","%%%%ab###","abc343","asgdabcbcabc9834"};
        for(String t : tmp){
            if(t.contains("")){
                System.out.println(t);
            }
        }


    }
}
