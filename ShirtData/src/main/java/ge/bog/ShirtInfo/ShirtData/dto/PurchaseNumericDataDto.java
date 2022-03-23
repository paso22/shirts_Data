package ge.bog.ShirtInfo.ShirtData.dto;

import ge.bog.ShirtInfo.ShirtData.exception.GeneralException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseNumericDataDto {

    private Integer shirtPrice;
    private Integer totalCost;
    private GeneralException e;

}
