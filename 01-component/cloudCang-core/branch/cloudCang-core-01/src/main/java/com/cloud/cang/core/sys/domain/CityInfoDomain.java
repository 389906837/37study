package com.cloud.cang.core.sys.domain;


import com.cloud.cang.generic.GenericEntity;

/**
 * 查询城市数据模型
 */
public class CityInfoDomain extends GenericEntity {
	

	private static final long serialVersionUID = 1571246703084722870L;

	/**
     * 国家名称
     */
    private String sname;

    /**
     * 国家简拼名
     */
    private String sjp;

    /**
     * 国家全拼名
     */
    private String spn;

    /**
     * 省份名
     */
    private String pname;

    /**
     * 省份排序
     */
    private Integer psort;

    /**
     * 省份简拼名
     */
    private String pjp;

    /**
     * 省份全拼名
     */
    private String ppn;

    /**
     * 城市名称
     */
    private String cname;

    /**
     * 城市排序字段
     */
    private Integer csort;

    /**
     * 城市简拼名
     */
    private String cjp;

    /**
     * 城市全拼名
     */
    private String cpn;

    /**
     * 是否省会 1：省会 0：非省会
     * 
     */
    private Integer cispc;

    /**
     * 城市代码
     */
    private String cacode;
    
    /**
     * 省份ID
     */
    private String proviceId;
    
    /**
     * 城市Id
     */
    private String cityId;

	/**
	 * 县镇区域Id
	 */
	private String townId;

	/**
	 * 县镇区域名称
	 */
	private String townName;

	/**
	 * 县镇区域简拼名
	 */
	private String tjp;

	/**
	 * 县镇区域全拼名
	 */
	private String tpn;

	/**
	 * 区域排序字段
	 */
	private Integer tsort;

	/**
	 * 区域代码
	 */
	private String tacode;
    /**
     * 省份对应省份证号代码
     */
    private String pidcode;
    /**
     * 城市对应省份证号代码
     */
    private String cidcode;


	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSjp() {
		return sjp;
	}

	public void setSjp(String sjp) {
		this.sjp = sjp;
	}

	public String getSpn() {
		return spn;
	}

	public void setSpn(String spn) {
		this.spn = spn;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getPsort() {
		return psort;
	}

	public void setPsort(Integer psort) {
		this.psort = psort;
	}

	public String getPjp() {
		return pjp;
	}

	public void setPjp(String pjp) {
		this.pjp = pjp;
	}

	public String getPpn() {
		return ppn;
	}

	public void setPpn(String ppn) {
		this.ppn = ppn;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getCsort() {
		return csort;
	}

	public void setCsort(Integer csort) {
		this.csort = csort;
	}

	public String getCjp() {
		return cjp;
	}

	public void setCjp(String cjp) {
		this.cjp = cjp;
	}

	public String getCpn() {
		return cpn;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public Integer getCispc() {
		return cispc;
	}

	public void setCispc(Integer cispc) {
		this.cispc = cispc;
	}

	public String getCacode() {
		return cacode;
	}

	public void setCacode(String cacode) {
		this.cacode = cacode;
	}

	public String getProviceId() {
		return proviceId;
	}

	public void setProviceId(String proviceId) {
		this.proviceId = proviceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getTjp() {
		return tjp;
	}

	public void setTjp(String tjp) {
		this.tjp = tjp;
	}

	public String getTpn() {
		return tpn;
	}

	public void setTpn(String tpn) {
		this.tpn = tpn;
	}

	public String getTacode() {
		return tacode;
	}

	public void setTacode(String tacode) {
		this.tacode = tacode;
	}

	public String getPidcode() {
		return pidcode;
	}

	public void setPidcode(String pidcode) {
		this.pidcode = pidcode;
	}

	public String getCidcode() {
		return cidcode;
	}

	public void setCidcode(String cidcode) {
		this.cidcode = cidcode;
	}

	@Override
	public String toString() {
		return "CityInfoDomain{" +
				"sname='" + sname + '\'' +
				", sjp='" + sjp + '\'' +
				", spn='" + spn + '\'' +
				", pname='" + pname + '\'' +
				", psort=" + psort +
				", pjp='" + pjp + '\'' +
				", ppn='" + ppn + '\'' +
				", cname='" + cname + '\'' +
				", csort=" + csort +
				", cjp='" + cjp + '\'' +
				", cpn='" + cpn + '\'' +
				", cispc=" + cispc +
				", cacode='" + cacode + '\'' +
				", proviceId='" + proviceId + '\'' +
				", cityId='" + cityId + '\'' +
				", townId='" + townId + '\'' +
				", townName='" + townName + '\'' +
				", tjp='" + tjp + '\'' +
				", tpn='" + tpn + '\'' +
				", tsort=" + tsort +
				", tacode='" + tacode + '\'' +
				", pidcode='" + pidcode + '\'' +
				", cidcode='" + cidcode + '\'' +
				'}';
	}

	public Integer getTsort() {
		return tsort;
	}

	public void setTsort(Integer tsort) {
		this.tsort = tsort;
	}

}
