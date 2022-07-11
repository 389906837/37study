package com.cloud.cang.vis.ws;
//普通类
public class PuTong {
    public PuTong(){
            System.out.print("默认构造方法！-->");
            }

        //非静态代码块
            {
            System.out.print("非静态代码块！-->");
            }

    //静态代码块
    static{
            System.out.print("静态代码块！-->");
            }

    public static  void test(){
            {
            System.out.println("普通方法中的代码块！");
            }
            }




    /**
     * 区别两次new静态与非静态代码块执行情况
     */
    public static void main(String[] args) {
        PuTong c1 = new PuTong();
        c1.test();

        PuTong c2 = new PuTong();
        c2.test();
    }
}

//测试类

