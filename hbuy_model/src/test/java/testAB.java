import java.lang.reflect.Field;

/**
 * author:快乐风男
 * time:8:26
 */
public class testAB {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        A a = new A();//父类
        B b = new B();//子类
        a.setNum(10);
        int num = a.getNum();
        System.out.println(num);

        b.setNum(20);
        int num1 = b.getNum();
        System.out.println(num1);

        System.out.println("=========================");
        Field declaredField = a.getClass().getDeclaredField("num");

        declaredField.setAccessible(true);
        System.out.println("暴力反射:"+declaredField.get(a));

        System.out.println(a);
        System.out.println(b);
        A c = new A();//父类
        System.out.println(c.getNum());


    }
}
