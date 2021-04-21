package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Location} entity.
 */
public interface LocationSearchRepository extends ElasticsearchRepository<Location, Long> {}
