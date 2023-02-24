package com.quarkus.bootcamp.nttdata.resources;

import lombok.Data;

@Data
public class Promotion {
	
	   private  String key;
	   private  String titulo;
	   private  String tienda;
	   private  double monto;
	   private String fechaIniVigencia;
	   private String fechaFinVigencia;
	   private Integer stock;
	   
	   
}
