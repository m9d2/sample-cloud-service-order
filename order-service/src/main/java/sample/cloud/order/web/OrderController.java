package sample.cloud.order.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sample.cloud.base.api.client.UserServiceClient;
import sample.cloud.base.domain.User;
import sample.cloud.framework.lock.ILock;

@Slf4j
@RestController
@AllArgsConstructor
public class OrderController {
    private static int num = 10;

    private UserServiceClient userServiceClient;

    private final ILock ILock;

    @GetMapping("/order")
    public User get(@RequestParam("id") Long id) {
        try {
            if (ILock.tryLock()) {
                if (num < 1) {
                    log.info("票已售完, 余票：{}", num);
                    return userServiceClient.getById(id);
                }
                num--;
                log.info("卖了一张，余票：{}", num);
                return userServiceClient.getById(id);
            }
        } catch (Exception e) {
            // 捕获异常，防止程序崩溃
            log.error("获取订单失败", e);
            throw new RuntimeException(e);
        } finally {
            ILock.unlock();
        }
        return null;
    }

}