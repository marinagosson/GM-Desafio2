package com.greenmile.desafio2.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.greenmile.desafio2.entities.CSV;

@Component
public class Process implements ItemProcessor<CSV, CSV> {

	@Override
	public CSV process(CSV item) throws Exception {
		return item;
	}

}
