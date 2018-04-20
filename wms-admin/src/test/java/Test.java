import com.mz.common.util.WXPayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        long currentTimestampMs = WXPayUtil.getCurrentTimestampMs();
        int i = random.nextInt(99);
        System.out.println(currentTimestampMs);
        System.out.println(i);
        System.out.println(currentTimestampMs + i);
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
