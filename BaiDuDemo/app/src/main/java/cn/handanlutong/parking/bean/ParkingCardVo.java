package cn.handanlutong.parking.bean;

public class ParkingCardVo {
	/**
	 * 月卡id
	 */
	private Long id;
	/**
     * 月卡名称
     */
    private String name;
    /**
     * 售价金额
     */
    private Integer sjje;
    /**
     * 原价金额
     */
    private Integer yjje;
    /**
     * 是否售完 0 否 1是
     */
    private String status;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSjje() {
		return sjje;
	}
	public void setSjje(Integer sjje) {
		this.sjje = sjje;
	}
	public Integer getYjje() {
		return yjje;
	}
	public void setYjje(Integer yjje) {
		this.yjje = yjje;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ParkingCardVo [name=" + name + ", sjje=" + sjje + ", yjje="
				+ yjje + ", status=" + status + "]";
	}
	public ParkingCardVo(String name, Integer sjje, Integer yjje, String status) {
		super();
		this.name = name;
		this.sjje = sjje;
		this.yjje = yjje;
		this.status = status;
	}
	public ParkingCardVo() {
		super();
	}
    
}
