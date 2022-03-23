package ge.bog.ShirtInfo.ShirtData.aop;
import ge.bog.ShirtInfo.ShirtData.entity.PasoShopShirts;
import ge.bog.ShirtInfo.ShirtData.exception.ErrorEnum;
import ge.bog.ShirtInfo.ShirtData.exception.GeneralException;
import ge.bog.ShirtInfo.ShirtData.repository.ShirtInfoRepository;
import ge.bog.ShirtInfo.ShirtData.utils.Types;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Aspect
@Component
@Slf4j
public class ShirtInfoAspect {

    @Value("http://localhost:8080")
    private String shirtsStoreUrl;
    private final RestTemplate restTemplate;
    private final ShirtInfoRepository shirtInfoRepository;

    public ShirtInfoAspect(RestTemplate restTemplate, ShirtInfoRepository shirtInfoRepository) {
        this.restTemplate = restTemplate;
        this.shirtInfoRepository = shirtInfoRepository;
    }

    @Before(value = "execution(* ge.bog.ShirtInfo.ShirtData.controller.ShirtInfoController.addShirt(..)) && args(userName, password, shirt, b))", argNames = "userName,password,shirt,b")
    public void addShirtValidation(String userName, String password, PasoShopShirts shirt, BindingResult b) {
        logInAndAdmin(userName,password);
        checkInputs(b);
        validateCreation(shirt);
        convertToUpperCase(shirt);
    }

    @Before(value = "execution(* ge.bog.ShirtInfo.ShirtData.controller.ShirtInfoController.getShirts(..)) && args(userName, password, team))", argNames = "userName,password,team")
    public void getShirtsValidation(String userName, String password, String team) {
        logInAndAdmin(userName,password);
    }

    @Before(value = "execution(* ge.bog.ShirtInfo.ShirtData.controller.ShirtInfoController.updateShirt(..)) && args(userName, password, shirt, b))", argNames = "userName,password,shirt,b")
    public void updateShirtValidation(String userName, String password, PasoShopShirts shirt, BindingResult b) {
        logInAndAdmin(userName,password);
        checkInputs(b);
        if(!isAlreadyContains(shirt)) {
            throw new GeneralException(ErrorEnum.SHIRT_NOT_EXISTS);
        }
        quantityCantReduceChecker(shirt);
    }

    @Before(value = "execution(* ge.bog.ShirtInfo.ShirtData.controller.ShirtInfoController.deleteShirt(..)) && args(userName, password, team))", argNames = "userName,password,team")
    public void deleteShirtValidation(String userName, String password, String team) {
        logInAndAdmin(userName,password);
        List<PasoShopShirts> res = shirtInfoRepository.findByTeam(team.toUpperCase()).orElse(null);
        if(res == null || res.size() == 0) {
            throw new GeneralException(ErrorEnum.SHIRT_NOT_EXISTS);
        }
    }


    private void quantityCantReduceChecker(PasoShopShirts shirt) {
        PasoShopShirts currentShirt = shirtInfoRepository.findByTeamAndTypeAndIshomeAndSeason(shirt.getTeam(),shirt.getType(),
                                                                                              shirt.getIshome(),shirt.getSeason()).orElse(null);
        if(currentShirt != null) {
            if (shirt.getQuantity() < currentShirt.getQuantity()) {
                throw new GeneralException(ErrorEnum.CANT_REDUCE_QUANTITY);
            }
        }
    }

    private boolean isAdmin(String userName, String password) {

        ResponseEntity<Boolean> responseEntity = null;
        try {
            String url = String.format("%s/PasoShop/get/isAdmin/%s/%s", shirtsStoreUrl, userName, password);
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Boolean>() {});
        } catch(HttpClientErrorException e) {
            log.error(e.getMessage());
        }
        if(responseEntity == null || responseEntity.getBody() == null) {
            return false;
        }
        return responseEntity.getBody();
    }

    private void logInAndAdmin(String userName, String password) {
        if(!isAdmin(userName, password)) {
            throw new GeneralException(ErrorEnum.NO_ACCESS_TO_DATA);
        }
    }

    private void convertToUpperCase(PasoShopShirts shirt) {
        shirt.setTeam(shirt.getTeam().toUpperCase());
        shirt.setType(shirt.getType().toUpperCase());
        shirt.setIshome(shirt.getIshome().toUpperCase());
    }

    private void validateCreation(PasoShopShirts shirt) {
        String isHome = shirt.getIshome();
        String type = shirt.getType();
        if(!isHome.equalsIgnoreCase("Y") && !isHome.equalsIgnoreCase("N")) {
            throw new GeneralException(ErrorEnum.INVALID_ISHOME);
        }
        if(!type.equalsIgnoreCase(Types.KIT.name()) &&
           !type.equalsIgnoreCase(Types.TRAINING.name()) &&
           !type.equalsIgnoreCase(Types.RETRO.name())) {
            throw new GeneralException(ErrorEnum.INVALID_TYPE);
        }
       if(isAlreadyContains(shirt)) {
            throw new GeneralException(ErrorEnum.SHIRT_ALREADY_EXISTS);
       }
    }

    private boolean isAlreadyContains(PasoShopShirts shirt) {
        String team = shirt.getTeam().toUpperCase();
        String type = shirt.getType().toUpperCase();
        String isHome = shirt.getIshome().toUpperCase();
        String season = shirt.getSeason().toUpperCase();
        PasoShopShirts res = shirtInfoRepository.findByTeamAndTypeAndIshomeAndSeason(team,type,isHome,season).orElse(null);
        return res != null;
    }

    private void checkInputs(BindingResult b) {
        if(b.hasErrors()) {
            String resError;
            for(Object o : b.getAllErrors()) {
                if(o instanceof FieldError) {
                    FieldError fe = (FieldError) o;
                    resError = fe.getDefaultMessage();
                    throw new GeneralException(fe.getField() + " - " + resError);
                }
            }
        }
    }

}
