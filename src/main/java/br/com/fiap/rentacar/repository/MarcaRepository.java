package br.com.fiap.rentacar.repository;

import br.com.fiap.rentacar.entity.Marca;
import br.com.fiap.rentacar.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {


    List<Marca> findByNomeIgnoreCase(String nome);

    List<Marca> findByNomeIgnoreCaseAndIdNot(String nome, Long id);

    //SELECT m.* FROM TBL_MODELO m where m.ID_MARCA = ?
    @Query("select m from Modelo m where m.marca.id = :idMarca")
    List<Modelo> findModelos(Long idMarca);

    //SELECT m.* FROM TBL_MODELO m JOIN TBL_MARCA mc ON mc.ID_MARCA = m.ID_MARCA where m.TX_NOME = ?
    @Query("Select m from Modelo m where m.marca.nome = :nomeMarca")
    List<Modelo> findModelosByMarca(String nomeMarca);

}
