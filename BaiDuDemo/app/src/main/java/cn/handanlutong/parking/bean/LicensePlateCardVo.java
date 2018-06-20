package cn.handanlutong.parking.bean;

public class LicensePlateCardVo {
	/**
	 * id
	 */
	private Long id;
	/**
     * 号牌号码
     */
    private String hphm;
    /**
     * 包期状态 0免费 1收费 2包期 3 包期过期 4包期禁用
     */
    private String bqzt;
    /**
     * 包期剩余天数
     */
    private int syts;
    /**
     * 是否可以续费 0否 1是
     */
    private int sfxf;
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getBqzt() {
		return bqzt;
	}
	public void setBqzt(String bqzt) {
		this.bqzt = bqzt;
	}
	public int getSyts() {
		return syts;
	}
	public void setSyts(int syts) {
		this.syts = syts;
	}
	public int getSfxf() {
		return sfxf;
	}
	public void setSfxf(int sfxf) {
		this.sfxf = sfxf;
	}	
}
