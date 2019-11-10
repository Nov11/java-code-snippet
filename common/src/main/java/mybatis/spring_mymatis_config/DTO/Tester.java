package mybatis.spring_mymatis_config.DTO;

public class Tester {
    public static void main(String[] args) {
        System.out.println(Gender.M.name());
        Gender v = Enum.valueOf(Gender.class, "M");
        System.out.println(v);
    }
}
