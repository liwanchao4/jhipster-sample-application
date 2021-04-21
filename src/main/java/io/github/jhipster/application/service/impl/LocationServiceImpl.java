package io.github.jhipster.application.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.jhipster.application.domain.Location;
import io.github.jhipster.application.repository.LocationRepository;
import io.github.jhipster.application.repository.search.LocationSearchRepository;
import io.github.jhipster.application.service.LocationService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Location}.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    private final LocationSearchRepository locationSearchRepository;

    public LocationServiceImpl(LocationRepository locationRepository, LocationSearchRepository locationSearchRepository) {
        this.locationRepository = locationRepository;
        this.locationSearchRepository = locationSearchRepository;
    }

    @Override
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        Location result = locationRepository.save(location);
        locationSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Location> partialUpdate(Location location) {
        log.debug("Request to partially update Location : {}", location);

        return locationRepository
            .findById(location.getId())
            .map(
                existingLocation -> {
                    if (location.getStreetAddress() != null) {
                        existingLocation.setStreetAddress(location.getStreetAddress());
                    }
                    if (location.getPostalCode() != null) {
                        existingLocation.setPostalCode(location.getPostalCode());
                    }
                    if (location.getCity() != null) {
                        existingLocation.setCity(location.getCity());
                    }
                    if (location.getStateProvince() != null) {
                        existingLocation.setStateProvince(location.getStateProvince());
                    }

                    return existingLocation;
                }
            )
            .map(locationRepository::save)
            .map(
                savedLocation -> {
                    locationSearchRepository.save(savedLocation);

                    return savedLocation;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        log.debug("Request to get all Locations");
        return locationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
        locationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> search(String query) {
        log.debug("Request to search Locations for query {}", query);
        return StreamSupport
            .stream(locationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
