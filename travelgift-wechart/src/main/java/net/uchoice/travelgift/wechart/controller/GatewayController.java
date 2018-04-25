package net.uchoice.travelgift.wechart.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import net.uchoice.travelgift.wechart.config.GlobalConfig;
import net.uchoice.travelgift.wechart.manager.MessageManager;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.util.MessageUtil;
import net.uchoice.travelgift.wechart.util.SignUtil;

@RestController
@RequestMapping("/wechart")
public class GatewayController {
	
	@Autowired
	MessageManager messageManager;

	private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public String validate(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
			throws NoSuchAlgorithmException {
		log.info("aaaaaa");
		boolean result = SignUtil.checkSignature(GlobalConfig.getToken(), signature, timestamp, nonce);
		return result ? echostr : "";
	}

	@RequestMapping(value = "/api", method = RequestMethod.POST)
	public String receive(HttpServletRequest request) {
		String response = "";
		try {
			String xml = IOUtils.toString(request.getInputStream(), "UTF-8");
			InputMessage message = MessageUtil.xmlToBean(xml, InputMessage.class);
			response = MessageUtil.messageToXml(messageManager.process(message));
			if(null == response) {
				log.error(String.format("message handle result null. [%s]", JSON.toJSONString(message)));
				response = "";
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
}
