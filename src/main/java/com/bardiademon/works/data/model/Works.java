package com.bardiademon.works.data.model;

import com.bardiademon.works.utils.Time;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public final class Works
{
    private String name;

    @SerializedName("hourly_amount")
    private int hourlyAmount;

    @SerializedName("registeration_time")
    private LocalDateTime registerationTime;

    @SerializedName("closing_time")
    @Builder.Default
    private LocalDateTime closingTime = LocalDateTime.now().minusYears(100);

    @SerializedName("sec")
    @Builder.Default
    private Time worked = Time.of("00:00:00");

    private boolean close;


    public static final class JsonKey
    {
        public static final String
                NAME = "name",
                HOURLY_AMOUNT = "hourly_amount",
                REGISTRATION_TIME = "registration_time",
                CLOSING_TIME = "closing_time",
                WORKED = "worked",
                CLOSE = "close";
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
