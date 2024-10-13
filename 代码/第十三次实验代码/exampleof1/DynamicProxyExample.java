package HW13.exampleof1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 动态代理
class SchoolDataInvocationHandler implements InvocationHandler {
    private Object target;

    public SchoolDataInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Permission check before updating data");
        Object result = method.invoke(target, args);
        System.out.println("Log after updating data");
        return result;
    }
}

// 示例代码
public class DynamicProxyExample {
    public static void main(String[] args) {
        SchoolData realSubject = new SchoolDataImpl();
        SchoolData proxyInstance = (SchoolData) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                new SchoolDataInvocationHandler(realSubject)
        );

        proxyInstance.updateData();
    }
}
