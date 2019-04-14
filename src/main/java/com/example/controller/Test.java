package com.example.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class Test {
	@Autowired
	private LoadBalancerClient loadBalancerClient;//ribbon负载均衡器，客户端
	
	@RequestMapping("test3")
	@ResponseBody
	public Map<String, String> test3() {
		System.out.println("消费者");
		Map<String,String> map = new HashMap<>();
		map.put("p", "requestBody");
		//根据应用名称，获取路径
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");//serviceId应用程序名称
		String host = serviceInstance.getHost();
		int port = serviceInstance.getPort();
		URI uri = serviceInstance.getUri();
		System.out.println("uri:"+uri);// http://Lenovo-PC:9090
		String uriStr = uri.toString();
		System.out.println("uriStr:"+uriStr); //http://Lenovo-PC:9090
		StringBuilder url = new StringBuilder();
		url.append(uri.toString()).append("/eureka-client-provider/test/var?a=aaa&b=bbbb");
//		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/test/var?a=aaa&b=bbbb");
//		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/");
		/*
		 * 发送请求
		 * 近几年来,以信息为中心的表述性状态转移(Representational State Transfer，REST)已经称为替代传统SOAP Web 服务的流行方案. 
			SOAP关注的一般是行为和处理,而REST关注的是要处理的数据.
			//Object... uriVariables 可变长度参数，参数个数不限，但类型必须一致
			uri:统一资源标识符，只是给出资源的一个唯一标识
			url：统一资源定位符，含有资源的地址。
		 */
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url.toString(), Map.class);
		
		Map body = responseEntity.getBody();
		return body;
	}
	
	@RequestMapping("test2")
	@ResponseBody
	public Map<String, String> test2() {
		System.out.println("消费者");
		Map<String,String> map = new HashMap<>();
		map.put("p", "requestBody");
		//根据应用名称，获取路径
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");//serviceId应用程序名称
		String host = serviceInstance.getHost();
		int port = serviceInstance.getPort();
		StringBuilder url = new StringBuilder();
		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/test/var?a=aaa&b=bbbb");
//		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/");
		/*
		 * 发送请求
		 * 近几年来,以信息为中心的表述性状态转移(Representational State Transfer，REST)已经称为替代传统SOAP Web 服务的流行方案. 
			SOAP关注的一般是行为和处理,而REST关注的是要处理的数据.
		 */
		RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Map> responseEntity = 
						restTemplate.postForEntity(url.toString(), map, Map.class);//Map.class给出返回类型 //map不是传参，不懂
		
		Map body = responseEntity.getBody();
		return body;
	}
	
	@RequestMapping("test")
	@ResponseBody
	public Map<String, String> test() {
		System.out.println("消费者");
		Map<String,String> map = new HashMap<>();
		map.put("p", "requestBody");
		//根据应用名称，获取路径
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");//serviceId应用程序名称
		String host = serviceInstance.getHost();
		int port = serviceInstance.getPort();
		StringBuilder url = new StringBuilder();
		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/test/var");
//		url.append("http://").append(host).append(":"+port).append("/eureka-client-provider/");
		/*
		 * 发送请求
		 * 近几年来,以信息为中心的表述性状态转移(Representational State Transfer，REST)已经称为替代传统SOAP Web 服务的流行方案. 
			SOAP关注的一般是行为和处理,而REST关注的是要处理的数据.
		 */
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<Map<String,String>> ptr = new ParameterizedTypeReference<Map<String,String>>() {
		};
//		ResponseEntity<Map<String, String>> exchange = restTemplate.exchange(sb.toString(), HttpMethod.POST, 
//				null, ptr,"variable1","variable2");//Object... uriVariables 可变长度参数，参数个数不限，但类型必须一致
		RequestEntity<Map<String,String>> entity = new RequestEntity<Map<String,String>>(map, null, null);
		ResponseEntity<Map<String, String>> exchange = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, ptr,"urlvariable");
		Map<String, String> body = exchange.getBody();
		
		return body;
	}
}
