package test;

import java.io.*;

public class ReadFile2 {
    //输出本行内容及字符数
    public static void readLineVarFile(String fileName, int lineNumber) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))); //使用缓冲区的方法将数据读入到缓冲区中
        String line = reader.readLine(); //定义行数
//        File file = new File(fileName);

        if (lineNumber <= 0 || lineNumber > getTotalLines(fileName)) //确定输入的行数是否有内容
        {
            System.out.println("不在文件的行数范围之内。");
        }
        int num = 0;
        while (line != null)    //当行数不为空时，输出该行内容及字符数
        {
            if (lineNumber == ++num) {
                System.out.println("第" + lineNumber + "行: " + line + "     字符数为：" + line.length());
            }
            line = reader.readLine();
        }
        reader.close();
    }



    // 文件内容的总行数
    public static int getTotalLines(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))); ////使用缓冲区的方法将数据读入到缓冲区中
        LineNumberReader reader = new LineNumberReader(br);
        String s = reader.readLine(); //定义行数
        int lines = 0;
        while (s != null) //确定行数
        {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        br.close();
        return lines; //返回行数
    }

    public static void readTxtFile3(String fileName) throws IOException {
        int past_pos = 1; // 文件指针上一次所在位置
        int cur_pos = 1; // 文件指针当前所在位置（就是下一个要读取的字符的位置）
        int cur_lineNumber = 1;
        InputStreamReader fr = new InputStreamReader(new FileInputStream(fileName), "UTF-8"); // 指定以UTF-8编码读入
        BufferedReader br = new BufferedReader(fr);

        String line = "";

        while ((line = br.readLine()) != null) {  // 文件未到文件尾
            past_pos = cur_pos;
            cur_pos += line.length();
            br.mark(past_pos);
            br.reset();
            System.out.println("第 " + cur_lineNumber + " 行: " + past_pos + "," + cur_pos);
            System.out.println(line);
            cur_lineNumber++;
        }

    }

    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按可读可写方式
            randomFile = new RandomAccessFile(fileName, "rw");

            long cur_pos = randomFile.getFilePointer();
            long prev_pos = 0;

            long fileLength = randomFile.length();  // 文件长度，字节数
            System.out.println(fileLength);

            System.out.println("初始位置："+cur_pos);

            // 将读文件的开始位置移到beginIndex位置。
//            randomFile.seek(beginIndex);

            String line = null;
            long lineNumber = 1;
            while ((line = randomFile.readLine()) != null) {
                prev_pos = cur_pos;
                cur_pos = randomFile.getFilePointer();
                randomFile.seek(prev_pos);
                line = randomFile.readLine();
                System.out.println("第 "+lineNumber+" 行: "+prev_pos+","+cur_pos);
                System.out.println(new String(line.getBytes("ISO-8859-1"),"utf-8"));
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String fileName = "D:\\data\\pdata\\all_person_info.txt"; // 读取文件
        int totalNo = getTotalLines(fileName);  // 获取文件的内容的总行数
        System.out.println("本文总共有：" + totalNo + "行");
//        while(true)
//        {
//            Scanner sc=new Scanner(System.in);
//            int lineNumber =sc.nextInt();  // 指定读取的行号
//            readLineVarFile(fileName, lineNumber); //读取指定行的内容
//        }
//        ReadFile2.readTxtFile3(fileName);
        ReadFile2.readFileByRandomAccess(fileName);

    }
}

