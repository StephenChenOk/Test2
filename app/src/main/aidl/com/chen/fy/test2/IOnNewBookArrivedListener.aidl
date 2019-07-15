package com.chen.fy.test2;
import com.chen.fy.test2.MyBook;
//aidl类型接口，在aidl中只能用此接口
interface IOnNewBookArrivedListener{

    void onNewBookArrived(in MyBook newBook);

}