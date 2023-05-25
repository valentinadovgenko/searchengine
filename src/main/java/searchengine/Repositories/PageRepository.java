package searchengine.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;
import searchengine.model.Site;


@Repository
public interface PageRepository extends CrudRepository<Page,Integer> {
    @Transactional
    void deleteBySite(Site site);


}
