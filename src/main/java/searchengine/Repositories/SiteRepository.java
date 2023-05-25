package searchengine.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Site;


@Repository
public interface SiteRepository  extends CrudRepository<Site, Integer> {

    Site findByName(String name);

}