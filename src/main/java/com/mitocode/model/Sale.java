package com.mitocode.model;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@SqlResultSetMapping(
        name = "Procedure.ProcedureDTO",
        classes = @ConstructorResult(targetClass = ProcedureDTO.class,
                columns = {
                        @ColumnResult(name = "quantityfn", type = Integer.class),
                        @ColumnResult(name = "datetimefn", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "Sale.fn_sales", //un alias de configuracion
        query = "select * from fn_sales()",
        resultSetMapping = "Procedure.ProcedureDTO"
)
///////////////////////////////////////////////////////////
@NamedStoredProcedureQuery(
        name = "getFnSales",//un alias de configuracion
        procedureName = "fn_sales",//name in the function in the DB
        resultClasses = IProcedureDTO.class
)
///////////////////////////////////////////////////////////

@NamedStoredProcedureQuery(
        name = "getFnSales2", //un alias a la configuracion
        procedureName = "fn_salesParameter", //debe coincidir con lo definido en Repo
        resultClasses = IProcedureDTO.class,
        parameters = {
                @StoredProcedureParameter(name = "p_id_client", type = Integer.class, mode = ParameterMode.IN) //,
                //@StoredProcedureParameter(name = "ABC", type = void.class, mode = ParameterMode.REF_CURSOR),
                //@StoredProcedureParameter(name = "DEF", type = String.class, mode = ParameterMode.OUT),
        })
///////////////////////////////////////////////////////////


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Sale {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSale;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false, foreignKey = @ForeignKey(name = "FK_SALE_CLIENT"))
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_SALE_USER"))
    private User user;

    @Column(nullable = false)
    private LocalDateTime dateTime; //Java.time desde Java 8

    @Column(columnDefinition = "decimal(6,2)", length = 50, nullable = false)
    private double total;

    @Column(columnDefinition = "decimal(6,2)", length = 50, nullable = false)
    private double tax;

    @Column(nullable = false)
    private boolean enable;

    // El detalle del maestro a Detalle
    //mappedBy = "sale" enlaza al atributo Sale de la clase SaleDetail
    //cascade = CascadeType.ALL define un comportamiento en casacada.
    // Si inserto en la venta, inserto en el detalle. Lo que le pasa Sale, le pasa a SaleDetail
    //para mantener integridad en detalle
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleDetail> details;
}
