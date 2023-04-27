package com.http.eventSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单控制器
 *
 * @author huan.fu 2021/10/14 - 上午9:34
 */
@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static volatile ConcurrentHashMap<String, DeferredResult<String>> DEFERRED_RESULT = new ConcurrentHashMap<>(20000);
    private static volatile AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    /**
     * 查询订单支付结果
     *
     * @param orderId 订单编号
     * @return DeferredResult
     */
    @RequestMapping(value="/queryOrderPayResult",produces = {"text/plain;charset=utf-8","text/html;charset=utf-8"})
    public DeferredResult<String> queryOrderPayResult(@RequestParam("orderId") String orderId, HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        log.info("订单orderId:[{}]发起了支付", orderId);
        ATOMIC_INTEGER.incrementAndGet();
        // // 60s 超时
        DeferredResult<String> result = new DeferredResult<>(3000L);

        // 超时操作
        result.onTimeout(() -> {
            DEFERRED_RESULT.get(orderId).setResult("超时了");
            log.info("订单orderId:[{}]发起支付,获取结果超时了.", orderId);
        });

        // 完成操作
        result.onCompletion(() -> {
            log.info("订单orderId:[{}]完成.", orderId);
            DEFERRED_RESULT.remove(orderId);
        });

        // 保存此 DeferredResult 的结果
        DEFERRED_RESULT.put(orderId, result);
        return result;
    }

    /**
     * 支付回调
     *
     * @param orderId 订单id
     * @return 支付回调结果
     */
    @RequestMapping(value="/payNotify",produces = {"text/plain;charset=utf-8","text/html;charset=utf-8"})
    public String payNotify(@RequestParam("orderId") String orderId) {
        log.info("订单orderId:[{}]支付完成回调", orderId);

        // 默认结果发生了异常
        if ("123".equals(orderId)) {
            DEFERRED_RESULT.get(orderId).setErrorResult(new RuntimeException("订单发生了异常"));
            return "回调处理失败";
        }

        if (DEFERRED_RESULT.containsKey(orderId)) {
            Optional.ofNullable(DEFERRED_RESULT.get(orderId)).ifPresent(result -> result.setResult("支付完成"));
            // 设置之前orderId toPay请求的结果
            return "回调处理成功";
        }
        return "回调处理失败";
    }
}