package searchengine.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.SiteTable;

public interface StatisticsService {
    StatisticsResponse getStatistics();
}
