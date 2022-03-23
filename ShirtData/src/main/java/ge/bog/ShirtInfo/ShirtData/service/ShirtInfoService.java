package ge.bog.ShirtInfo.ShirtData.service;

import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;

import java.util.List;

public interface ShirtInfoService {

    PasoShopShirts addShirt(PasoShopShirts shirt);
    List<PasoShopShirts> getShirts(String team);
    PasoShopShirts updateShirt(PasoShopShirts shirt);
    String deleteShirt(String team);

}
