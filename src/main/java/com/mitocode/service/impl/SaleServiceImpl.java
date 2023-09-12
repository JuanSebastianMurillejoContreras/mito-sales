package com.mitocode.service.impl;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import com.mitocode.model.SaleDetail;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ISaleRepo;
import com.mitocode.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    private final ISaleRepo repo;
    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<ProcedureDTO> callProcedure() {
        List<ProcedureDTO> list = new ArrayList<>();
        repo.callProcedure().forEach(e -> {
            // Esta lógica es util cuando yo deseo modificar o calcular algo y modificar los datos.
            ProcedureDTO dto = new ProcedureDTO();
            dto.setQuantityfn((Integer) e[0]);
            dto.setDatetimefn((String) e[1]);
            list.add(dto);
        });
        return list;
    }

    @Override
    public List<IProcedureDTO> callProcedure2() {
        return repo.callProcedure2();
    }

    @Override
    public List<ProcedureDTO> callProcedure3() {
        return repo.callProcedure3();
    }

    @Transactional
    @Override
    public List<IProcedureDTO> callProcedure4() {
        return repo.callProcedure4();
    }

    @Override
    public Sale getSaleMoreExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal))
                .orElse(new Sale());
    }

    @Override
    public String getBestSellerPerson() {
        Map<String,Double> byUser =  repo.findAll()
                .stream()
                .collect(groupingBy(s -> s.getUser().getUserName(), summingDouble(Sale::getTotal)));

        return Collections.max(byUser.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    @Override
    public Map<String, Long> getSalesCountBySeller() {
        return  repo.findAll()
                .stream()
                .collect(groupingBy(s -> s.getUser().getUserName(), counting()));
    }


    @Override
    public Map<String, Double> getMostSellerProduct() {
        Stream<Sale>  saleStream = repo.findAll().stream();
        Stream<List<SaleDetail>> listStream = saleStream.map(e-> e.getDetails());

        Stream<SaleDetail> saleDetailStream = listStream.flatMap(list -> list.stream());

        Map<String, Double> byProduct = saleDetailStream
                .collect(groupingBy(d->d.getProduct().getName(), summingDouble(SaleDetail::getQuantity)));

        return byProduct.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }

}
