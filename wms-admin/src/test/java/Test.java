import java.util.ArrayList;
import java.util.List;

/**
 * @author tongzhou
 * @description
 * @date 2018/3/8 11:30
 */
public class Test {
    public String str = "6";

    public static void main(String[] args) {
        Test test = new Test();
        test.change(test.str);
        System.out.println(Math.random());

    }

    /**
     * @param
     * @description
     * @author tongzhou
     * @date 2018/3/8
     */
    public void change(String str) {
        /**
         * @description
         * @author tongzhou
         * @param [str]
         * @date 2018/3/8 11:15
         */
        str = "10";
        List list = new ArrayList();
        System.out.println(list);
    }

}
