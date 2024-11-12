package com.fcamara.backendtest.dto.parkingLot;

import java.util.Date;

public record HourlyReportDTO(Date date, Integer hour, Long entryCount, Long exitCount) {
}
