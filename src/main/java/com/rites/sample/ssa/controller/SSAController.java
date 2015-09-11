package com.rites.sample.ssa.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSAController {
	
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String test() {
		return "test";
	}
}
