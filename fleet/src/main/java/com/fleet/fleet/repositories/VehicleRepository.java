package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Boolean existsByPlate(String carPlate);
}
