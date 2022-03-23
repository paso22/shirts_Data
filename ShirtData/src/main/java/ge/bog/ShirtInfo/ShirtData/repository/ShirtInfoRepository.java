package ge.bog.ShirtInfo.ShirtData.repository;

import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShirtInfoRepository extends JpaRepository<PasoShopShirts, Integer> {

    Optional<List<PasoShopShirts>> findByTeam(String team);
    Optional<PasoShopShirts> findByTeamAndTypeAndIshomeAndSeason(String team, String type, String isHome, String season);

}
