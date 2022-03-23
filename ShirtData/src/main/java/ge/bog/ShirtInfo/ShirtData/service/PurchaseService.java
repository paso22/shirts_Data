package ge.bog.ShirtInfo.ShirtData.service;

import ge.bog.ShirtInfo.ShirtData.dto.PurchaseNumericDataDto;

public interface PurchaseService {


    PurchaseNumericDataDto getNumericData(String team, String type, String isHome, String season, String hasNameNumber, Integer quantity, Integer wallet);
}
