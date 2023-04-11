package searchengine.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.config.Site;

import java.util.List;
@Repository
public interface PageRepository extends CrudRepository<PageTable,Integer> {
    @Transactional
    @Modifying
    @Query("delete from PageTable p where p.site = ?1")
    void deleteBySite(SiteTable site);
}
