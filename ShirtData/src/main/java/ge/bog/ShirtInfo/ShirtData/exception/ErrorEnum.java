package ge.bog.ShirtInfo.ShirtData.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    NO_ACCESS_TO_DATA("ოპერაციის განსახორციელებლად საჭიროა ავტორიზაცია და ადმინის უფლებების ქონა!", HttpStatus.FORBIDDEN),
    INVALID_ISHOME("isHome ველი უნდა შეიცვადეს ან Y-ს, ან N-ს", HttpStatus.BAD_REQUEST),
    INVALID_TYPE("type პარამეტრი არასწორია. (Kit,Training,Retro) - აქედან ერთ-ერთი უნდა იყოს", HttpStatus.BAD_REQUEST),
    SHIRT_ALREADY_EXISTS("მოცემული მაისური უკვე არსებობს", HttpStatus.FORBIDDEN),
    SHIRT_NOT_EXISTS("მოცემული პარამეტრებით მაისური არ არსებობს", HttpStatus.NOT_FOUND),
    UPDATE_IS_LIMITED("შესაძლებელია მხოლოდ შემდეგი ველების დააფდეითება - price, quantity, addNameNumber", HttpStatus.FORBIDDEN),
    CANT_REDUCE_QUANTITY("ხელით არაა შესაძლებელი მაისურების რაოდენობის შემცირება. შესაძლებელია მხოლოდ გაზრდა", HttpStatus.FORBIDDEN),
    INSUFFICIENT_STOCKS("მარაგში არაა მოცემული რაოდენობის მაისური", HttpStatus.FORBIDDEN),
    INSUFFICIENT_MONEY("თანხა არასაკმარისია!", HttpStatus.FORBIDDEN);


    private final String message;
    private final HttpStatus status;

}
