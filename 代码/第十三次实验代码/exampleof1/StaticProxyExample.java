package HW13.exampleof1;

// 静态代理类
class SchoolDataProxy implements SchoolData {
    private SchoolData schoolData;

    public SchoolDataProxy(SchoolData schoolData) {
        this.schoolData = schoolData;
    }

    public void updateData() {
        // 执行一些额外操作，比如权限验证、日志记录等
        System.out.println("Permission check before updating data");
        // 调用实际的数据更新操作
        schoolData.updateData();
        // 执行一些额外操作，比如日志记录等
        System.out.println("Log after updating data");
    }
}
public class StaticProxyExample {
    public static void main(String[] args) {
        // 创建实际的学校数据实现类
        SchoolData realSubject = new SchoolDataImpl();

        // 创建静态代理对象
        SchoolData proxy = new SchoolDataProxy(realSubject);

        // 调用代理对象的方法
        proxy.updateData();
    }
}