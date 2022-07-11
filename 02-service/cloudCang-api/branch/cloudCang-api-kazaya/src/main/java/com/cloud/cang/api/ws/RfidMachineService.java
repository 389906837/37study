package com.cloud.cang.api.ws;
import java.util.Date;

import com.cloud.cang.api.antbox.constant.RestResult;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.api.sp.service.CommodityInfoService;
import com.cloud.cang.api.sp.service.CommodityRfidService;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sp.CommodityRfid;
import com.cloud.cang.rfid.CommodityRfidDto;
import com.cloud.cang.rfid.MerchantDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rfidMachine")
public class RfidMachineService {
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private CommodityRfidService commodityRfidService;

    /**
     * 查询商户
     * @param username 用户名或者手机号
     * @param password 密码
     * @return
     */
    @RequestMapping("/selectMerchant")
    @ResponseBody
    public RestResult<List<MerchantDto>> selectMerchant(String username, String password) {
        List<MerchantDto> list = merchantInfoService.selectMerchantsByuserNameOrmobile(username, password);
        return new RestResult(list);
    }

    /**
     * 查询商品信息
     * @param merchantId 商户ID
     * @param commodityCode 商品编号
     * @param commodityName 商品名称
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public RestResult<List<CommodityInfo>> productList(String merchantId,String commodityCode, String commodityName) {
        List<CommodityInfo> list = commodityInfoService.selectCommodityList(merchantId,commodityCode,commodityName);
        return new RestResult(list);
    }

    /**
     * 查询商户标签
     * @param merchantId
     * @param rfidList
     * @return
     */
    @RequestMapping("/commdityRfidList")
    @ResponseBody
    public RestResult<List<CommodityRfid>> commdityRfidList(String merchantId,
                                                            @RequestParam("rfidList") ArrayList<String> rfidList) {
        List<CommodityRfid> list = new ArrayList<>();
        if(rfidList.size() >0){
            list = commodityRfidService.commdityRfidList(merchantId,rfidList);
            return new RestResult(list);
        }
        return new RestResult(list);
    }

    /**
     * 商品绑定标签
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveCommdityRfid", method = RequestMethod.POST)
    @ResponseBody
    public RestResult<List<CommodityRfid>> saveCommdityRfid(@RequestBody CommodityRfidDto dto) {
        try {
            List<String> rfids = dto.getRfids();
            for (String rfid : rfids) {
                boolean isExist = (getCommdityRfid(rfid) == null ? false:true);
                if(!isExist){
                    CommodityRfid entity = new CommodityRfid();
                    entity.setRfid(rfid);
                    entity.setScommodityCode(dto.getScommodityCode());
                    entity.setScommodityName(dto.getScommodityName());
                    entity.setSmerchantId(dto.getSmerchantId());
                    entity.setSoperatorId(dto.getSoperatorId());
                    entity.setTaddTime(new Date());

                    commodityRfidService.insert(entity);
                }
            }
            return new RestResult(RestResult.CD1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult(RestResult.CD0);
    }

    /**
     * 解除绑定（物理删除）
     * @param dto
     * @return
     */
    @RequestMapping(value = "/deleteCommdityRfid", method = RequestMethod.POST)
    @ResponseBody
    public RestResult<List<CommodityRfid>> deleteCommdityRfid(@RequestBody CommodityRfidDto dto) {
        try {
            if(dto == null || CollectionUtils.isEmpty(dto.getRfids())){
                return new RestResult(RestResult.CD1);
            }

            List<String> rfids = dto.getRfids();
            if (rfids.size() == 0) {
                return new RestResult(RestResult.CD1);
            }

            commodityRfidService.batchDeleteByRfids(dto.getRfids());
            return new RestResult(RestResult.CD1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult(RestResult.CD0);
    }


    private CommodityRfid getCommdityRfid(String rfid){
        CommodityRfid entity = new CommodityRfid();
        entity.setRfid(rfid);
        List<CommodityRfid> list = commodityRfidService.selectByEntityWhere(entity);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
