package gcg.testproject.activity.xuliehua;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * discription:
 * Created by 宫成浩 on 2018/8/8.
 */

public class PersonBean2 implements Parcelable {
    protected PersonBean2(Parcel in) {
        this.age = in.readInt();
        this.sex = in.readInt();
        this.name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeInt(this.sex);
        dest.writeString(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonBean2> CREATOR = new Creator<PersonBean2>() {
        @Override
        public PersonBean2 createFromParcel(Parcel in) {
            return new PersonBean2(in);
        }

        @Override
        public PersonBean2[] newArray(int size) {
            return new PersonBean2[size];
        }
    };

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
        return "PersonBean2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
