package order;

import java.sql.Timestamp;
import java.util.Date;

public class OrderInfo {
	//客户地区
	public String area;
	//合伙企业Id
	public Long partComp;
	//合同类型 1为A类型，2为B类型，默认1
	public String contractType = "1";
	//是否证件复印件 0为是，1为否，默认0
	public String isId = "0";
	//证件号
	public String idNo; 
	//证件类型 1为身份证，2为护照，3为港澳通行证，4为驾驶者
	public String idType = "1";
	//下单金额
	public Long orderAmount;
	//产品系数
	public String prodDiffcoe;
	//订单号
	public Long orderNo;
	//创建日期
	public Date entryTime;
	//下单日期
	public Date createTime;
	//客户id
	public Long custNo;
	//产品号
	public Long prodNo;
	//订单版本号
	public Long orderVersion;
	//是否通过审核
	public String isChecked="2";
	//邮件Id
	public Long emailId; 
	//开户行
	public String bankNo;
	//银行卡号
	public String bankCard;
	//实际投资人id
	public Long investorNo;
	//客户类型
	public String custType;
	//折后标费
	public String acountFee;
	//客户地址
	public String custAddress;
	//销售类型 1为直客，2为非直客
	public String orderType;
	//合同号
	public String contractNo;
	//额外奖励
	public String extraAward;
	//数据录入人员
	public Long register;
	//订单状态 默认0
	public String stateflag = "0";
	//客户邮箱
	public String custEmail = "";
	//客户邮寄地址
	public String mailAddress;
	//客户工作地址
	public String workAddress;
	//客户电话
	public String custCell;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getIsId() {
		return isId;
	}
	public void setIsId(String isId) {
		this.isId = isId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getProdDiffcoe() {
		return prodDiffcoe;
	}
	public void setProdDiffcoe(String prodDiffcoe) {
		this.prodDiffcoe = prodDiffcoe;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getOrderVersion() {
		return orderVersion;
	}
	public void setOrderVersion(Long orderVersion) {
		this.orderVersion = orderVersion;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public Long getInvestorNo() {
		return investorNo;
	}
	public void setInvestorNo(Long investorNo) {
		this.investorNo = investorNo;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getAcountFee() {
		return acountFee;
	}
	public void setAcountFee(String acountFee) {
		this.acountFee = acountFee;
	}
	public Long getPartComp() {
		return partComp;
	}
	public void setPartComp(Long partComp) {
		this.partComp = partComp;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getCustNo() {
		return custNo;
	}
	public void setCustNo(Long custNo) {
		this.custNo = custNo;
	}
	public Long getProdNo() {
		return prodNo;
	}
	public void setProdNo(Long prodNo) {
		this.prodNo = prodNo;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getExtraAward() {
		return extraAward;
	}
	public void setExtraAward(String extraAward) {
		this.extraAward = extraAward;
	}
	public Long getRegister() {
		return register;
	}
	public void setRegister(Long register) {
		this.register = register;
	}
	public String getStateflag() {
		return stateflag;
	}
	public void setStateflag(String stateflag) {
		this.stateflag = stateflag;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getCustCell() {
		return custCell;
	}
	public void setCustCell(String custCell) {
		this.custCell = custCell;
	}
}
