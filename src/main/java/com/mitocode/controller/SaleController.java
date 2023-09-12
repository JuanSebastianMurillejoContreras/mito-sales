package com.mitocode.controller;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.dto.SaleDTO;
import com.mitocode.model.Sale;
import com.mitocode.service.ISaleService;
import com.mitocode.service.impl.SaleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final ISaleService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> readAll() throws Exception{
        List<SaleDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> readById(@PathVariable("id") Integer id) throws Exception{
        SaleDTO obj = convertToDto(service.readById(id));
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO dto) throws Exception{
        Sale obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(this.convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody SaleDTO dto) throws Exception{
        dto.setIdSale(id);
        Sale obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////////Procedures///////////////
    @GetMapping("/resume")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume(){
        return new ResponseEntity<>(service.callProcedure(),HttpStatus.OK);
    }

    @GetMapping("/resume2")
    public ResponseEntity<List<IProcedureDTO>> getSaleResume2(){
        return new ResponseEntity<>(service.callProcedure2(),HttpStatus.OK);
    }

    @GetMapping("/resume3")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume3(){
        return new ResponseEntity<>(service.callProcedure3(),HttpStatus.OK);
    }

    @GetMapping("/resume4")
    public ResponseEntity<List<IProcedureDTO>> getSaleResume4(){
        return new ResponseEntity<>(service.callProcedure4(),HttpStatus.OK);
    }

    /////////////////////////////////////////////
    @GetMapping("/mostexpensive")
    public ResponseEntity<SaleDTO> getMostExpensive(){
        SaleDTO dto = convertToDto(service.getSaleMoreExpensive());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/bestseller")
    public ResponseEntity<String> getBestSeller(){
        String user = service.getBestSellerPerson();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/sellercount")
    public ResponseEntity<Map<String, Long>> getSellerCount(){
        Map<String, Long> byUser = service.getSalesCountBySeller();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }

    @GetMapping("/bestproduct")
    public ResponseEntity<Map<String, Double>> getBestProduct(){
        Map<String, Double> byUser = service.getMostSellerProduct();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }

    private SaleDTO convertToDto(Sale obj){
        return mapper.map(obj,SaleDTO.class);
    }
    private Sale convertToEntity(SaleDTO dto){
        return mapper.map(dto, Sale.class);
    }
}
