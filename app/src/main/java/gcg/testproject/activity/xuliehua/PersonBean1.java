package gcg.testproject.activity.xuliehua;

import java.io.Serializable;

/**
 * discription:
 * Created by 宫成浩 on 2018/8/8.
 */

public class PersonBean1 implements Serializable {
    private int age;
    private String name;
    private int sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PersonBean1{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
