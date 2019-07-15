// IBookManager.aidl
package com.chen.fy.test2;
import com.chen.fy.test2.MyBook;
import com.chen.fy.test2.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
       List<MyBook> getBooks();
       //对于非基本数据类型，参数必须标明方向，in表示输入型参数,out输出型参数,inout输入输出型参数
       void addBook(in MyBook book);

       //注册新书到达监听器
       void registerListener(IOnNewBookArrivedListener listener);
       //取消新书到达监听器
       void unregisterListener(IOnNewBookArrivedListener listener);
}
