package com.cloud.cang.wap.sm.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.wap.sm.service.DeviceStockService;
import com.cloud.cang.wap.sm.vo.DeviceStockList;
import com.cloud.cang.wap.sm.vo.DeviceStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yan on 2019/2/18.
 */
@Controller
@RequestMapping("/personal")
public class DeviceStockController {
    @Autowired
    DeviceStockService deviceStockService;

    /**
     * 查看设备库存商品明细
     *
     * @param deviceCode
     * @return
     */
    @RequestMapping("/myDeviceStock")
    @ResponseBody
    public ResponseVo myDeviceStock(String deviceCode) {
        List<DeviceStockList> deviceStockList = deviceStockService.selectDeviceStock(deviceCode);
        DeviceStockVo deviceStockVo = new DeviceStockVo();
        deviceStockVo.setDeviceStockVos(deviceStockList);
        return ResponseVo.getSuccessResponse(deviceStockVo);
    }
}
