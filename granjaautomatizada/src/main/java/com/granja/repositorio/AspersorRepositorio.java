package com.granja.repositorio;

import com.granja.entidad.AspersorEntidad;
import com.granja.entidad.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//Comunicarse con la entidad AspersorEntidad en la base de datos
@Repository
//junto con spring le inyecta automaticamente con @autowired
public interface AspersorRepositorio extends JpaRepository<AspersorEntidad, String> {
    //sin escribir codigo tenemos gracias a extends
    //save(aspersor)
    //findById(id)
    //findAll()
    //deleteById(id)
    //existsById(id)
    //count()

    //Spring lee el nombre del método y genera la consulta
    //Todos los aspersores asignados a esa parcela
    List<AspersorEntidad> findByParcela(ParcelaEntity parcela);
    //Todos los aspersores no asignados a parcelas
    List<AspersorEntidad> findByParcelaIsNull();


    //se guarda en un servicio normalmente se inyecta con @service
}