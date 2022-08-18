package com.ssafy.daero.admin.vo;

import com.ssafy.daero.trip.dto.TripPlaceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class TripPlaceVo extends TripPlaceDto {
    private ArrayList<Integer> tags;
}
