package ge.bog.ShirtInfo.ShirtData.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDate;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException e) {;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(e.getErrorEnum() != null) {
            status = e.getErrorEnum().getStatus();
        }
        log.error(e.getMessage() + " , " + status + ", " + LocalDate.now());
        ExceptionResponse res = new ExceptionResponse(e.getMessage(), status, LocalDate.now());
        return new ResponseEntity<>(res, res.getStatus());
    }

}
