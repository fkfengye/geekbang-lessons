package org.geekbang.study.file;

import java.io.*;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description 序列化反序列化
 * @create 2021-04-17 17:53
 **/
public class SerializableObjectDemo implements Serializable {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SerializableObjectDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializableObjectDemo serializableObjectDemo = new SerializableObjectDemo();
        serializableObjectDemo.setName("小明");
        serializableObjectDemo.setAge(100);

        String filePath = String.format("%s/%s", "D://", SerializableObjectDemo.class.getName());

        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(serializableObjectDemo);

        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SerializableObjectDemo rebackObject = (SerializableObjectDemo) ois.readObject();

        System.out.println(rebackObject);
    }
}
