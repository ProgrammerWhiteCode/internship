package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ShipService {
    Page<Ship> getShipsList(Specification<Ship> specification, Pageable sortedBy);
    Integer getShipsCount(Specification<Ship> specification);
    Ship createShip(Ship ship);
    Ship getShip(Long id);
    Ship updateShip(Long id, Ship ship);
    void deleteShip(Long id);
    Long idToLongWithChecking(String id);

    Specification<Ship> nameFilter(String name);
    Specification<Ship> planetFilter(String planet);
    Specification<Ship> shipTypeFilter(ShipType shipType);
    Specification<Ship> prodDateFilter(Long after, Long before);
    Specification<Ship> isUseFilter(Boolean isUsed);
    Specification<Ship> speedFilter(Double minSpeed, Double maxSpeed);
    Specification<Ship> crewSizeFilter(Integer minCrewSize, Integer maxCrewSize);
    Specification<Ship> ratingFilter(Double minRating, Double maxRating);
}
