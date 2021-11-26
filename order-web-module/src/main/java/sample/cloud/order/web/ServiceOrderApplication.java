package sample.cloud.order.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sample.cloud.base.api.service.BaseDataClient;
import sample.cloud.base.domain.vo.DateVO;

import java.util.Map;

@EnableFeignClients({"sample.cloud"})
@ComponentScan({"sample.cloud"})
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceOrderApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private BaseDataClient baseDataClient;

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }

    @RestController
    public class TestController {

        private final RestTemplate restTemplate;

        @Autowired
        public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

        @RequestMapping(value = "/get/{info}", method = RequestMethod.GET)
        public Map<String, Object> get(@PathVariable String info) {
//            restTemplate.getForObject("http://sample-cloud-service-base/service-base/get/" + str, String.class);
            DateVO dateVO = baseDataClient.get(info);
            Map<String, Object> map = Maps.newHashMap();
            map.put("baseData", dateVO);
            map.put("orderId", 1L);
            return map;
        }
    }
}
