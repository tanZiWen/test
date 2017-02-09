package order;


public class SalesCustRel {
	public Long salesId;
	public Long custId;
	public String salesName;
	public Long emailId;
	public String salesArea;
	public String custBelongState = "1";
	public Long getSalesId() {
		return salesId;
	}
	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
	public String getSalesArea() {
		return salesArea;
	}
	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}
	public String getCustBelongState() {
		return custBelongState;
	}
	public void setCustBelongState(String custBelongState) {
		this.custBelongState = custBelongState;
	}
}
