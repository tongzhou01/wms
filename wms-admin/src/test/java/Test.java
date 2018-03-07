public class Test {
    public String str = "6";

    public static void main(String[] args) {
        Test test = new Test();
        test.change(test.str);
        System.out.println(Math.random());
    }
    public void change(String str) {
        str = "10";
    }
}
