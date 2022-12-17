package com.bardiademon.works.data.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDateTime;

@Builder
@Getter
public final class Works
{
    private final String name;

    @SerializedName("hourly_amount")
    private final int hourlyAmount;

    @SerializedName("registeration_time")
    private final LocalDateTime registerationTime;

    @SerializedName("closing_time")
    private final LocalDateTime closingTime;

    @SerializedName("sec")
    @Builder.Default
    private final Time worked = new Time(-12600000);

    private final boolean close;

    @Override
    public String toString()
    {
        return getName();
    }
}
