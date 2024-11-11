package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    Boolean existsByCarPlateAndStatus(String carPlate, char status);

    @Modifying
    @Transactional
    @Query("UPDATE Checklist c SET c.endChecklist = :newEndChecklistId, c.status = 'F' WHERE c.checklistId = :checklistId")
    void updateEndChecklistAndStatusByChecklistId(int checklistId, int newEndChecklistId);

    @Query("SELECT c FROM Checklist c WHERE c.driverId = :driverId AND (c.status = 'N' OR (c.status = 'P' AND c.route = 'F'))")
    List<Checklist> findAllByDriverIdAndStatusAndRoute(int driverId);
}
