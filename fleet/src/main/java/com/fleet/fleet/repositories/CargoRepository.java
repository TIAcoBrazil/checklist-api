package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    Cargo findByCargoId(int cargoId);
}
