package com.ssafy.daero.trip.vo;

import com.ssafy.daero.trip.dto.AlbumDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumVo extends AlbumDto {
    public enum ProfileResult {
        DELETED, NO_SUCH_USER, NO_CONTENT
    }

    private JourneyVo.ProfileResult result;
}
