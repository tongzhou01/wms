import com.mz.common.component.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redisTest
 *
 * @author tongzhou
 * @date 2018-03-12 15:31
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-*.xml"})
public class RedisTest {
    @Autowired
    RedisCache redisCache;

    @Test
    public void test() {
        //redisCache.setValue("1","张三");
        System.out.println(redisCache.getValue("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiIxIiwic2V4IjoxLCJyZWFsX25hbWUiOiLlmI7lmI4iLCJpZCI6MX0.JkvOZRmdhcmDvte2S_1W6FYnliTE_HBcUhwO8YKSHvc"));
    }
}
