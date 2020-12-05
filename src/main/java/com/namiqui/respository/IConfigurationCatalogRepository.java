package com.namiqui.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.namiqui.models.ConfigurationCatalog;

/**
 * Clase repositorio para obtener las configuraciones default.
 * @author OGUM
 *
 */
@Repository
public interface IConfigurationCatalogRepository extends JpaRepository<ConfigurationCatalog, Long> {

	
}
