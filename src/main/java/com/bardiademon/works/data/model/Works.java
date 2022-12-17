package com.bardiademon.works.data.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public final class Works
{
    private final String name;
    private final int hourlyAmount;
    private final LocalDateTime registerationTime;
    private final LocalDateTime closingTime;
    private final long minutes;
    private final boolean close;
}
