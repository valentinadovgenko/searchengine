package searchengine.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SiteRepository  extends CrudRepository<SiteTable, Integer> {

    SiteTable findByName(String name);

    void deleteByName(String name);

}