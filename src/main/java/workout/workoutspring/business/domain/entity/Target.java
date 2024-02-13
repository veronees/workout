package workout.workoutspring.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Target {
    CHEST("가슴"),
    BACK("등"),
    SHOULDER("어깨"),
    LEG("하체"),
    ARM("팔");

    private final String name;

//    Target(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
}
