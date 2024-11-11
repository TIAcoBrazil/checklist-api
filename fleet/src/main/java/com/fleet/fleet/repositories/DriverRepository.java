package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    boolean existsBySectorIdAndDriverId(int sectorId, int driverId);

}
