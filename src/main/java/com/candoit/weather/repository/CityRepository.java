package com.candoit.weather.repository;

import com.candoit.weather.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

 City findCityById(Integer id);
 Page<City> findAll(Pageable p);
}
