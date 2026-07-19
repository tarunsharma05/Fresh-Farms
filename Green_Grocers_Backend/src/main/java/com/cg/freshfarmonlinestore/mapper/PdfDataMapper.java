package com.cg.freshfarmonlinestore.mapper;
import java.util.HashMap;

import java.util.Map;
 
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import com.cg.freshfarmonlinestore.dto.OrderDto;
@Service
public class PdfDataMapper {
	public Context setData(OrderDto orderDto) {
		Context context = new Context();
		Map<String, Object> data = new HashMap<>();
		data.put("order", orderDto);
		context.setVariables(data);
		return context;
	}
}