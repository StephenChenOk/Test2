package com.chen.fy.test2;

import android.os.Parcel;
import android.os.Parcelable;

public class MyBook implements Parcelable {

    private String name;
    private int price;

    public MyBook(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * 反序列化构造方法
     */
    private MyBook(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    /**
     * 进行序列化
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;  //当对象中存在文件描述符时，返回1
    }

    public static final Creator<MyBook> CREATOR = new Creator<MyBook>() {
        @Override
        public MyBook createFromParcel(Parcel in) {
            return new MyBook(in);  //得到反序列化对象
        }

        @Override
        public MyBook[] newArray(int size) {
            return new MyBook[size];
        }
    };
}
