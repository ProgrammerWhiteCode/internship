package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipController {
    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping("/rest/ships")
    public ResponseEntity<List<Ship>> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        Specification<Ship> specification = Specification.where(shipService.nameFilter(name)
                .and(shipService.planetFilter(planet))
                .and(shipService.shipTypeFilter(shipType))
                .and(shipService.prodDateFilter(after, before))
                .and(shipService.isUseFilter(isUsed))
                .and(shipService.speedFilter(minSpeed, maxSpeed))
                .and(shipService.crewSizeFilter(minCrewSize, maxCrewSize))
                .and(shipService.ratingFilter(minRating, maxRating)));

        return new ResponseEntity<>(shipService.getShipsList(specification, pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/rest/ships/count")
    public ResponseEntity<Integer> getCount(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "planet", required = false) String planet,
                                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                                            @RequestParam(value = "after", required = false) Long after,
                                            @RequestParam(value = "before", required = false) Long before,
                                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                            @RequestParam(value = "minRating", required = false) Double minRating,
                                            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        Specification<Ship> specification = Specification.where(shipService.nameFilter(name)
                .and(shipService.planetFilter(planet))
                .and(shipService.shipTypeFilter(shipType))
                .and(shipService.prodDateFilter(after, before))
                .and(shipService.isUseFilter(isUsed))
                .and(shipService.speedFilter(minSpeed, maxSpeed))
                .and(shipService.crewSizeFilter(minCrewSize, maxCrewSize))
                .and(shipService.ratingFilter(minRating, maxRating)));
        return new ResponseEntity<>(shipService.getShipsCount(specification), HttpStatus.OK);
    }

    @PostMapping("/rest/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        Ship resultShip = shipService.createShip(ship);
        return new ResponseEntity<>(resultShip, HttpStatus.OK);
    }

    @GetMapping("/rest/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable String id) {
        Ship resultShip = shipService.getShip(shipService.idToLongWithChecking(id));
        return new ResponseEntity<>(resultShip, HttpStatus.OK);
    }

    @PostMapping("/rest/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable String id,
                                           @RequestBody Ship ship) {
        Ship resultShip = this.shipService.updateShip(shipService.idToLongWithChecking(id), ship);
        return new ResponseEntity<>(resultShip, HttpStatus.OK);
    }

    @DeleteMapping("/rest/ships/{id}")
    public ResponseEntity<?> deleteShip(@PathVariable String id) {
        shipService.deleteShip(shipService.idToLongWithChecking(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}