package ge.bog.ShirtInfo.ShirtData.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PASO_SHOP_SHIRTS")
@Getter
@Setter
public class PasoShopShirts {

    @Id
    @SequenceGenerator(name = "PASO_SHOP_SHIRTS_SEQ", sequenceName = "PASO_SHOP_SHIRTS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "PASO_SHOP_SHIRTS_SEQ")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEAM")
    @NotNull(message = "გუნდის სახელის ველი ცარიელია")
    private String team;

    @Column(name = "TYPE")
    @NotNull(message = "Type ველი ცარიელია")
    private String type;

    @Column(name = "ISHOME")
    @NotNull(message = "isHome ველი ცარიელია")
    @Size(min = 1, max = 1, message = "isHome ველი უნდა შეიცავდეს 1 სიმბოლოს")
    private String ishome;

    @Column(name = "SEASON")
    @NotNull(message = "სეზონის ველი ცარიელია")
    private String season;

    @Column(name = "PRICE")
    @NotNull(message = "ფასის ველი ცარიელია")
    @Min(value = 1, message = "ფასი მინიმუმ 1 ლარი უნდა იყოს")
    private Integer price;

    @Column(name = "QUANTITY")
    @NotNull(message = "რაოდენობის ველი ცარიელია")
    @Min(value = 0, message = "რაოდენობა უარყოფითი არ შეიძლება იყოს")
    private Integer quantity;

    @Column(name = "ADD_NAME_NUMBER")
    @NotNull(message = "ნომრისა და სახელის დამატებითი საფასურის ველი ცარიელია")
    @Min(value = 1, message = "ნომრისა და სახელის დამატებითი საფასური მინიმუმ 1 ლარი უნდა იყოს")
    private Integer addNameNumber;

}
