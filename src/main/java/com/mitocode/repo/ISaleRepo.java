package com.mitocode.repo;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISaleRepo extends IGenericRepo<Sale, Integer> {
    @Query(value = "SELECT * FROM fn_sales () ", nativeQuery = true)
    List<Object[]>callProcedure();

    @Query(value = "SELECT * FROM fn_sales () ", nativeQuery = true)
    List<IProcedureDTO>callProcedure2();

    //Es el del SqlResulSetMapping
    @Query(name = "Sale.fn_sales", nativeQuery = true)
    List<ProcedureDTO> callProcedure3();

    //El del NamedStoredProcedure
    // POR CORREGIR
    @Procedure(procedureName = "fn_sales")
    List<IProcedureDTO> callProcedure4();

    @Procedure(procedureName = "fn_salesParameter")
    List<IProcedureDTO> callProcedure5(@Param("p_id_client") Integer idClient);

}
