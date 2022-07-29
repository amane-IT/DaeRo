package com.ssafy.daero.trip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {
    private int tripSeq;
    private String imageUrl;
    private String title;
    private char expose;
    private char likeYn;
}
