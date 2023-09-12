package com.mitocode.service;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Role;
import com.mitocode.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer>{

    List<ProcedureDTO> callProcedure();
    List<IProcedureDTO> callProcedure2();
    List<ProcedureDTO> callProcedure3();
    List<IProcedureDTO> callProcedure4();

    Sale getSaleMoreExpensive(); //get the mayor sale
    String getBestSellerPerson();//obtener el nombre del mejor vendedor
    Map<String, Long> getSalesCountBySeller();//Cantidad de ventas por vendendor
    Map<String, Double> getMostSellerProduct();//Producto mas vendido

}
