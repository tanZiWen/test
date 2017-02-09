package order;

public class BillInfo {
	public Long id;
	public String custType="1";
	public String effectSign="0";
	public Long emailId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getEffectSign() {
		return effectSign;
	}
	public void setEffectSign(String effectSign) {
		this.effectSign = effectSign;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
}
