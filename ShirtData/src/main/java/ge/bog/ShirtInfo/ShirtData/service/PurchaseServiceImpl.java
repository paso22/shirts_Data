package ge.bog.ShirtInfo.ShirtData.service;

import ge.bog.ShirtInfo.ShirtData.dto.PurchaseNumericDataDto;
import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;
import ge.bog.ShirtInfo.ShirtData.exception.ErrorEnum;
import ge.bog.ShirtInfo.ShirtData.exception.GeneralException;
import ge.bog.ShirtInfo.ShirtData.repository.ShirtInfoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ShirtInfoRepository shirtInfoRepository;

    public PurchaseServiceImpl(ShirtInfoRepository shirtInfoRepository) {
        this.shirtInfoRepository = shirtInfoRepository;
    }

    @Override
    public PurchaseNumericDataDto getNumericData(String team, String type, String isHome, String season
                                                ,String hasNameNumber, Integer quantity, Integer wallet) {
        PasoShopShirts res = shirtInfoRepository.findByTeamAndTypeAndIshomeAndSeason(team,type,
                                                             isHome,season).orElse(null);
        if(res == null) {
            return getFinalResult(null, null, new GeneralException(ErrorEnum.SHIRT_NOT_EXISTS));
        }
        return validateSufficient(res, hasNameNumber, quantity, wallet);

    }

    private PurchaseNumericDataDto validateSufficient(PasoShopShirts res, String hasNameNumber, Integer quantity, Integer wallet) {
        int maxQuantity = res.getQuantity();
        int price = res.getPrice();
        int nameNumber = hasNameNumber.equalsIgnoreCase("Y") ? res.getAddNameNumber() : 0;

        if(quantity > maxQuantity) {
            return getFinalResult(null, null, new GeneralException(ErrorEnum.INSUFFICIENT_STOCKS));
        }
        int totalCost = quantity * (price + nameNumber);
        if(totalCost > wallet) {
            return getFinalResult(null, null, new GeneralException(ErrorEnum.INSUFFICIENT_MONEY));
        }
        reduceStockNumber(res, quantity);
        return getFinalResult(price,totalCost,null);

    }

    private void reduceStockNumber(PasoShopShirts res, Integer quantity) {
        res.setQuantity(res.getQuantity() - quantity);
        shirtInfoRepository.save(res);
    }

    private PurchaseNumericDataDto getFinalResult(Integer price, Integer cost, GeneralException e) {
        return PurchaseNumericDataDto.builder()
                .shirtPrice(price)
                .totalCost(cost)
                .e(e)
                .build();
    }
}
