package com.candoit.weather.model;

import com.candoit.weather.api.WeatherDeserializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @SerializedName("int_number")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("province")
    @Expose
    private String province;
    @DateTimeFormat(pattern="dd-MM'T'HH:mm:ss")
    private LocalDateTime lastUpdate;
    @SerializedName("lat")
    @Expose
    private Double latitude;
    @SerializedName("lon")
    @Expose
    private Double longitude;
    @SerializedName("weather")
    @JsonAdapter(WeatherDeserializer.class)
    @Expose
    private Double temperature;


}
