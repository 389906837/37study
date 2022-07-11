package com.cloud.cang.api.sb.service;

import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.model.sb.DeviceSnapshot;
import com.cloud.cang.generic.GenericService;

import java.util.Set;

public interface DeviceSnapshotService extends GenericService<DeviceSnapshot, String> {

    DeviceSnapshot getDeviceSnapshotByBoxId(Long deviceId);

    void updateCurrentShopper(Long boxId, CustomerDto customerDto);

    void resetCurrentShopper(Long boxId);

    void updateLastInventoryInfo(Long boxId, Set<String> lables, CustomerDto customerDto);
}