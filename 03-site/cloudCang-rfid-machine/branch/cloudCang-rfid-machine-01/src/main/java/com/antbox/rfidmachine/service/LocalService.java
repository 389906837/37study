package com.antbox.rfidmachine.service;

import com.antbox.common.RestResult;
import com.antbox.domain.Merchant;
import com.antbox.domain.RfidMachine;
import com.antbox.domain.RfitRssi;
import com.antbox.rfidmachine.domain.ProductLotno;
import com.antbox.rfidmachine.dto.ProductDto;
import com.antbox.rfidmachine.dto.RfidProductDto;
import com.antbox.rfidmachine.dto.UserDto;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sp.CommodityRfid;
import com.cloud.cang.rfid.CommodityRfidDto;
import com.cloud.cang.rfid.MerchantDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * Created by DK on 17/5/9.
 */
public interface LocalService {

    Call<RestResult<UserDto>> localLogin(@Body UserDto dto);

    @GET("/rfidMachine/productList")
    Call<RestResult<List<CommodityInfo>>> productList(@Query("merchantId") String merchantId,
                                                      @Query("commodityCode") String commodityCode,
                                                      @Query("commodityName") String commodityName);

    @GET("/rfidMachine/commdityRfidList")
    Call<RestResult<List<CommodityRfid>>> commdityRfidList(@Query("merchantId") String merchantId,
                                                           @Query("rfidList") List<String> rfidList);

    @POST("/rfidMachine/saveCommdityRfid")
    Call<RestResult> saveCommdityRfid(@Body CommodityRfidDto dto);

    @POST("/rfidMachine/deleteCommdityRfid")
    Call<RestResult> deleteCommdityRfid(@Body CommodityRfidDto dto);

    @GET("/rfidMachine/selectMerchant")
    Call<RestResult<List<MerchantDto>>> selectMerchant(@Query("username") String username,
                                                       @Query("password") String password);

    Call<RestResult<RfidMachine>> downloadNewestRfidVersion();

    Call<RestResult<RfitRssi>> findRfitRssiValue(@HeaderMap Map<String, String> headers);

    Call<RestResult<List<ProductLotno>>> productLotnoList(@HeaderMap Map<String, String> headers, @Query("productId") Long productId);
}
