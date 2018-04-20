import com.mz.common.component.RedisCache;
import com.mz.mq.component.Producer;
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
    @Autowired
    Producer producer;

    @Test
    public void test() {
        //redisCache.setValue("1","张三");
        for (int i = 0; i < 10000; i++) {
            producer.sendQueueMsg(i);
        }
    }
}
