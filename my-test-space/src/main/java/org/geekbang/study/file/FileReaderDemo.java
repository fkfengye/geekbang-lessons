package org.geekbang.study.file;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-03-28 12:23
 **/
public class FileReaderDemo {

    public static void main(String[] args) {
        test02();
    }

    public static void test02(){
        // 获得资源文件(.class文件)所在根路径
        String path1 = ClassLoader.getSystemResource("").getPath();
        String path2 = FileReaderDemo.class.getResource("/").getPath();

        System.out.println("------------ class文件所在的根路径 -----------");
        System.out.println(path1);
        System.out.println(path2);

        // 获取当前 FileReaderDemo.class 文件所在的路径
        String path3 = FileReaderDemo.class.getResource("").getPath();
        System.out.println("\n------------ 当前class文件所在的路径 -----------");
        System.out.println(path3);

        InputStream is = ClassLoader.getSystemResourceAsStream("META-INF/dependency-lookup-context.xml");
        System.out.println(is);

    }

    public static void test01() {
        // 使用System获取当前项目路径
        String projectPath = System.getProperty("user.dir");
        System.out.println("当前项目路径：" + projectPath + "\n\r");

        // Properties对象获取系统属性
        Properties properties = System.getProperties();
        Enumeration pnames = properties.propertyNames();
        while (pnames.hasMoreElements()) {
            String pk = (String) pnames.nextElement();
            String pv = properties.getProperty(pk);
            System.out.println(String.format("【%s】: %s", pk, pv));
        }
    }
}
