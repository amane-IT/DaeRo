package com.ssafy.daero.trip.vo;

import com.ssafy.daero.trip.dto.JourneyDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyVo extends JourneyDto {
    public enum ProfileResult {
        DELETED, NO_SUCH_USER, SUCCESS, NO_CONTENT
    }

    private ProfileResult result;
}
