package order;

import java.util.Date;

public class CustInfo {
	public Long custId;
	public String custName;
	public String sex;
	public String address;
	public String email;
	public String state;
	public String custCell;
	public String custSex;
	public Date custRegTime;
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCustCell() {
		return custCell;
	}
	public void setCustCell(String custCell) {
		this.custCell = custCell;
	}
	public String getCustSex() {
		return custSex;
	}
	public void setCustSex(String custSex) {
		this.custSex = custSex;
	}
	public Date getCustRegTime() {
		return custRegTime;
	}
	public void setCustRegTime(Date custRegTime) {
		this.custRegTime = custRegTime;
	}
	@Override
	public String toString() {
		return "CustInfo [custId=" + custId + ", custName=" + custName + "]";
	}
}
