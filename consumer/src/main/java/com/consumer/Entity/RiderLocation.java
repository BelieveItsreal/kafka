package com.consumer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RiderLocation {
    private String riderId;
    private double latitude;
    private double longitude;
}
