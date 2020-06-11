package testService.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import common.entity.RestfulResult;
import common.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import testService.entity.ServiceInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
public class ServiceController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${server.port}")
    String port;


    @RequestMapping(value = "/ServiceRibbon")
    @HystrixCommand(fallbackMethod="serviceRibbonFallback")
    public String ServiceRibbon(@RequestBody ServiceInfo serviceInfo){
        //服务名+类的mapping+方法的mapping  这样写服务的时候不需要地址
        String result = this.restTemplate.postForObject("http://test1/testService/rest?token=1", serviceInfo, String.class);
        return result;
    }


    @RequestMapping(value = "rest")
    public String rest(@RequestBody ServiceInfo serviceInfo){

        return "Service1:Welcome " + serviceInfo.getName() + " !";
    }

    public String serviceRibbonFallback(@RequestBody ServiceInfo serviceInfo){
        return "consumerServiceRibbon异常，端口：" + port + "，Name=" + serviceInfo.getName();
    }
}
