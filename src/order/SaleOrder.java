package order;

public class SaleOrder {
	//销售提成点
	public String salesPoint = "0";
	//订单号
	public Long orderNo;
	//销售id
	public Long salesId;
	//标费
	public Long magtFee;
	//销售姓名
	public String salesName;
	//订单版本号
	public Long orderVersion;
	//邮件id
	public Long emailId;
	//费用模式 1为按标费分配，2为按提成点分配
	public String costModel = "1";
	//客户id
	public Long custId;
	//订单奖金计算金额
	public Long obcAmount;
	//扣减责任金金额
	public Long amountDeduction = 0L;
	//状态 默认0
	public String stateflag = "0";
	public String getSalesPoint() {
		return salesPoint;
	}
	public void setSalesPoint(String salesPoint) {
		this.salesPoint = salesPoint;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getSalesId() {
		return salesId;
	}
	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}
	public Long getMagtFee() {
		return magtFee;
	}
	public void setMagtFee(Long magtFee) {
		this.magtFee = magtFee;
	}
	public void setObcAmount(Long obcAmount) {
		this.obcAmount = obcAmount;
	}
	public void setAmountDeduction(Long amountDeduction) {
		this.amountDeduction = amountDeduction;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public Long getOrderVersion() {
		return orderVersion;
	}
	public void setOrderVersion(Long orderVersion) {
		this.orderVersion = orderVersion;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
	public String getCostModel() {
		return costModel;
	}
	public void setCostModel(String costModel) {
		this.costModel = costModel;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getObcAmount() {
		return obcAmount;
	}
	public Long getAmountDeduction() {
		return amountDeduction;
	}
	public String getStateflag() {
		return stateflag;
	}
	public void setStateflag(String stateflag) {
		this.stateflag = stateflag;
	}
}
