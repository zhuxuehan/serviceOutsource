package cn.fwwb.controller.system.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RefuseController {
	@RequestMapping("/refuse")
	public String refusePage() {
		return "refuse";
	}
}
