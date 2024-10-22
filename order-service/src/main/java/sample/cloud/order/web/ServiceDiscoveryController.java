package sample.cloud.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceDiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    // 查看所有注册的服务
    @GetMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    // 查看某个服务的实例信息
    @GetMapping("/services/{serviceName}")
    public List<ServiceInstance> getServiceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }
}