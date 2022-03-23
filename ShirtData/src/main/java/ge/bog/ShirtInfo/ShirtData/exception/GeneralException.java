package ge.bog.ShirtInfo.ShirtData.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralException extends RuntimeException {

    private ErrorEnum errorEnum;

    public GeneralException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }

    public GeneralException(String msg) {
        super(msg);
        this.errorEnum = null;
    }

}
