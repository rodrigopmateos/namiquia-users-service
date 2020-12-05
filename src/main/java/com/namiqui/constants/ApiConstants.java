package com.namiqui.constants;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApiConstants {

	@Value("${configuration.configurations.default}")
	private List<String> defaultConfigs;
}
