package pl.pgf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pgf.model.City;

@Repository
public interface CityDao extends JpaRepository<City, Long> {

	City findByName(String name);

}
