package eu.skypotion.ui.size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum UISize {

    ONE_ROW(9),
    TWO_ROWS(18),
    THREE_ROWS(27),
    FOUR_ROWS(36),
    FIVE_ROWS(45),
    SIX_ROWS(54);

    int size;

}
