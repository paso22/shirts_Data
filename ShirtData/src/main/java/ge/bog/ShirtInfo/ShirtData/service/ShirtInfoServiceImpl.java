package ge.bog.ShirtInfo.ShirtData.service;

import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;
import ge.bog.ShirtInfo.ShirtData.repository.ShirtInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShirtInfoServiceImpl implements ShirtInfoService {

    private final ShirtInfoRepository shirtInfoRepository;

    public ShirtInfoServiceImpl(ShirtInfoRepository shirtInfoRepository) {
        this.shirtInfoRepository = shirtInfoRepository;
    }

    @Override
    public PasoShopShirts addShirt(PasoShopShirts shirt) {
        shirtInfoRepository.save(shirt);
        return shirt;
    }

    @Override
    public List<PasoShopShirts> getShirts(String team) {
        Optional<List<PasoShopShirts>> shirts = shirtInfoRepository.findByTeam(team.toUpperCase());
        return shirts.orElse(null);
    }

    @Override
    public PasoShopShirts updateShirt(PasoShopShirts shirt) {
        PasoShopShirts currShirt = shirtInfoRepository.findByTeamAndTypeAndIshomeAndSeason(shirt.getTeam(),shirt.getType(),
                            shirt.getIshome(),shirt.getSeason()).orElse(null);
        if(currShirt != null) {
            setFieldsToUpdate(shirt, currShirt);
            shirtInfoRepository.save(currShirt);
        }
        return currShirt;
    }

    @Override
    public String deleteShirt(String team) {
        List<PasoShopShirts> res = shirtInfoRepository.findByTeam(team.toUpperCase()).orElse(null);
        int deletedShirts;
        if(res == null || res.size() == 0) {
            return "nothing was deleted!";
        }
        deletedShirts = res.size();
        shirtInfoRepository.deleteAll(res);
        return deletedShirts + " shirts deleted!";
    }

    private void setFieldsToUpdate(PasoShopShirts givenShirt, PasoShopShirts shirtToUpdate) {
            shirtToUpdate.setPrice(givenShirt.getPrice());
            shirtToUpdate.setQuantity(givenShirt.getQuantity());
            shirtToUpdate.setAddNameNumber(givenShirt.getAddNameNumber());
    }
}
