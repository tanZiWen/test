package order;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.lang.model.element.VariableElement;

import common.CommonBase;

public class InsertOrder extends CommonBase {
	public static void main(String[] args) {
		InsertOrder io = new InsertOrder();
		List<List<String>> list = io.readXLSXWithFormula("/Users/tanyuan/Desktop/订单导入模板.xlsx");
		Long cust_no;
		Map<Integer, Long> map1 = new HashMap<Integer, Long>();
		Map<Integer, Long> map2= new HashMap<Integer, Long>();
		for(List<String> ls:list) {
			UpmUser upmUser = new UpmUser();
			ProductInfo productInfo = new ProductInfo();
			LpInfo lpInfo = new LpInfo();
			OrgInfo orgInfo = new OrgInfo();
			CustSeeInfo custSeeInfo = new CustSeeInfo();
			SalesCustRel salesCustRel = new SalesCustRel();
			BillInfo billInfo = new BillInfo();
			CustInfo custInfo = new CustInfo();
			String cust_type_name =ls.get(1);
			int cust_type = 1;
			System.out.println(cust_type_name);
			if("个人".equals(cust_type_name.trim())) {
				cust_type = 1;
			}else if("机构".equals(cust_type_name.trim())){
				cust_type = 2;
			}else {
				System.out.println("客户类型不存！！");
				continue;
			}
			String cust_name = ls.get(0);
			String invest_cust_phone = ls.get(3);
			String invest_cust_name = ls.get(2);
			String rm_name = ls.get(4);
			String rm_status = ls.get(6);
			String cust_address = ls.get(8);
			String email = ls.get(9);
			if(cust_type == 1) {
				boolean isExistCustByName = io.isExistCustByName(cust_name);
				boolean isExistCustByMobile = io.isExistCustByMobile(invest_cust_phone);
				if(!isExistCustByMobile) {
					if(isExistCustByName) {
						System.out.println("个人-电话不存在，姓名存在："+cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						continue;
					}else {
						CustInfo cf = new CustInfo();
						System.out.println("个人-电话不存在，姓名不存在："+cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						upmUser.setRealName(rm_name);
						upmUser = (UpmUser) io.getModelByCondition(upmUser);
						cf.setAddress(cust_address);
						cf.setCustCell(invest_cust_phone);
						cf.setEmail(email);
						cf.setCustSex("0");
						Long cust_id = io.seq();
						cf.setCustId(cust_id);
						cf.setCustRegTime(new Timestamp(System.currentTimeMillis()));
						cf.setState("3");
						cf.setEmail(email);
						cf.setCustName(cust_name);
						io.insertOrder(cf);
						
						custSeeInfo.setCustId(cust_id);
						custSeeInfo.setEmailId(io.seq());
						custSeeInfo.setSeeId(io.seq());
						io.insertOrder(custSeeInfo);
						
						salesCustRel.setCustId(cust_id);
						salesCustRel.setSalesArea(upmUser.getArea());
						salesCustRel.setEmailId(io.seq());
						salesCustRel.setSalesId(upmUser.getUserId());
						salesCustRel.setSalesName(upmUser.getRealName());
						io.insertOrder(salesCustRel);
						
						billInfo.setEmailId(io.seq());
						billInfo.setId(cust_id);
						io.insertOrder(billInfo);
					}
				}else {
					if(isExistCustByName) {
						System.out.println("个人-电话存在，姓名存在："+cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
					}else {
						System.out.println("个人-电话存在，姓名不存在："+cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						continue;
					}
				}
				custInfo.setCustName(cust_name);
				custInfo = (CustInfo) io.getModelByCondition(custInfo);
				cust_no = custInfo.getCustId();
			}else {
				boolean isExistOrgByName = io.isExistOrgByName(cust_name);
				if(!isExistOrgByName) {
					System.out.println("org name "+cust_name+" is not exist!");
					continue;
				}
				boolean isExistCustByName = io.isExistCustByName(invest_cust_name);
				boolean isExistCustByMobile = io.isExistCustByMobile(invest_cust_phone);
				if(!isExistCustByMobile) {
					if(isExistCustByName) {
						System.out.println("投资人-电话不存在，姓名存在："+invest_cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						continue;
					}else {
						System.out.println("投资人-电话不存在，姓名不存在："+invest_cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						CustInfo cf = new CustInfo();
						upmUser.setRealName(rm_name);
						upmUser = (UpmUser) io.getModelByCondition(upmUser);
						cf.setAddress(cust_address);
						cf.setCustCell(invest_cust_phone);
						cf.setEmail(email);
						cf.setCustSex("0");
						Long cust_id = io.seq();
						cf.setCustId(cust_id);
						cf.setCustRegTime(new Timestamp(System.currentTimeMillis()));
						cf.setState("3");
						cf.setEmail(email);
						cf.setCustName(invest_cust_name);
						io.insertOrder(cf);
						
						custSeeInfo.setCustId(cust_id);
						custSeeInfo.setEmailId(io.seq());
						custSeeInfo.setSeeId(io.seq());
						io.insertOrder(custSeeInfo);
						
						salesCustRel.setCustId(cust_id);
						salesCustRel.setSalesArea(upmUser.getArea());
						salesCustRel.setEmailId(io.seq());
						salesCustRel.setSalesId(upmUser.getUserId());
						salesCustRel.setSalesName(upmUser.getRealName());
						io.insertOrder(salesCustRel);
						
						billInfo.setEmailId(io.seq());
						billInfo.setId(cust_id);
						io.insertOrder(billInfo);
					}
				}else {
					if(isExistCustByName) {
						System.out.println("投资人-电话存在，姓名存在："+invest_cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
					}else {
						System.out.println("投资人-电话存在，姓名不存在："+invest_cust_name+" rm:"+rm_name+ " rm_status:"+rm_status);
						continue;
					}
				}
				orgInfo.setOrgName(cust_name);
				orgInfo = (OrgInfo) io.getModelByCondition(orgInfo);
				cust_no = orgInfo.getOrgId();
			}
			
			if(!"".equals(invest_cust_name.trim())) { 
				boolean isExistInvestName = io.isExistCustByName(invest_cust_name);
				if(!isExistInvestName) {
					System.out.println("invest person name "+invest_cust_name+" is not exist!");
					continue;
				}
			}
			
			boolean isExistRMByName = io.isExistRMByName(rm_name);
			if(!isExistRMByName) {
				System.out.println("rm name "+rm_name+" is not exist!");
				continue;
			}
			
			String rm_employee_code = ls.get(5);
			String rm_phone = ls.get(7);
			
			String prod_name = ls.get(10);
			boolean isExistProductByName = io.isExistProductByName(prod_name);
			if(!isExistProductByName) {
				System.out.println("product name "+prod_name+" is not exist!");
				continue;
			}
			
			String partner_com_name = ls.get(11);
			boolean isExistLPByName = io.isExistLPByName(partner_com_name.trim());
			if(!isExistLPByName) {
				System.out.println("LP name "+partner_com_name+" is not exist!");
				continue;
			}
			String order_amount = ls.get(12);
			String order_time = ls.get(13);
			String order_area = ls.get(14);
			String product_diffcoe = ls.get(15);
			String sales_point = ls.get(16);
			String extra_award = ls.get(17);
			String deduction = ls.get(18);
			String reward_amount = ls.get(19);
			String reward_should = ls.get(20);
			String reward_reserved = ls.get(21);
			String order_type_name = ls.get(22);
			int order_type = 1;
			if("直客".equals(order_type_name)) {
				order_type = 1;
			}else if("非直客".equals(order_type_name)) {
				order_type = 2;
			}else {
				System.out.println("销售类型不存在!");
				continue;
			}
			String contract_type_name = ls.get(23);
			int contract_type = 0;
			if("A类".equals(contract_type_name)) {
				contract_type = 1;
			}else if("B类".equals(contract_type_name)) {
				contract_type = 2;
			}else if("其他".equals(contract_type_name)) {
				contract_type = 3;
			}else {
				System.out.println("合同类型不存在!");
				continue;
			}
			String contract_no = ls.get(24);
			String id_type_name = ls.get(25);
			int id_type = 0;
			if("身份证".equals(id_type_name)) {
				id_type = 1;
			}else if("护照".equals(id_type_name)) {
				id_type = 2;
			}else if("港澳通行证".equals(id_type_name)) {
				id_type = 3;
			}else if("驾驶证".equals(id_type_name)) {
				id_type = 4;
			}else {
				System.out.println("证件类型不存在!!");
				continue;
			}
			String id_no = ls.get(26);
			String bank_no = ls.get(27);
			String bank_card = ls.get(28);
			String mail_address = ls.get(29);
			String work_address = ls.get(30);
			String flag = ls.get(31);
			
			Long order_version = io.seq();
			Long order_no = io.seq();
			
			OrderInfo orderModel = new OrderInfo();
			orderModel.setCustNo(cust_no);
			orderModel.setCustType(String.valueOf(cust_type));
			if(!"".equals(invest_cust_name.trim())) {
				custInfo.setCustName(invest_cust_name);
				custInfo = (CustInfo) io.getModelByCondition(custInfo);
				orderModel.setInvestorNo(custInfo.getCustId());
			}
			orderModel.setCustAddress(cust_address);
			productInfo.setProdName(prod_name);
			productInfo = (ProductInfo) io.getModelByCondition(productInfo);
			orderModel.setProdNo(productInfo.getProdId());
			lpInfo.setPartnerComName(partner_com_name);
			lpInfo = (LpInfo) io.getModelByCondition(lpInfo);
			orderModel.setPartComp(lpInfo.getLpId());
			orderModel.setOrderAmount(Long.parseLong(order_amount));
			orderModel.setCreateTime(new Timestamp(System.currentTimeMillis()) );
			orderModel.setArea(io.dictMapping(order_area));
			orderModel.setProdDiffcoe(product_diffcoe);
			if(!"".equals(extra_award.trim())) {
				orderModel.setExtraAward(extra_award);
			}
			orderModel.setOrderType(String.valueOf(order_type));
			orderModel.setContractType(String.valueOf(contract_type));
			orderModel.setContractNo(contract_no);
			orderModel.setIdType(String.valueOf(id_type));
			orderModel.setIdNo(id_no);
			orderModel.setBankNo(bank_no);
			orderModel.setBankCard(bank_card);
			orderModel.setOrderVersion(order_version);
			orderModel.setOrderNo(order_no);
			orderModel.setRegister((long) 1000225);
			orderModel.setCustEmail(email);
			orderModel.setMailAddress(mail_address);
			orderModel.setWorkAddress(work_address);
			orderModel.setCustCell(invest_cust_phone);
			
			orderModel.setEntryTime(java.sql.Date.valueOf(order_time));
			orderModel.setEmailId(io.seq());
			if(flag != null && !"".equals(flag) && !map1.containsKey(Integer.parseInt(flag))) {
				io.insertOrder(orderModel);
			}
			
			if(flag != null && !"".equals(flag) && !map1.containsKey(Integer.parseInt(flag))) {
				map1.put(Integer.parseInt(flag), order_no);
			}
			if(flag != null && !"".equals(flag) && !map2.containsKey(Integer.parseInt(flag))) {
				map2.put(Integer.parseInt(flag), order_version);
			}
		
			SaleOrder saleOrder = new SaleOrder();
			System.out.println(sales_point);
			if(sales_point.matches("^[.0-9]*$")) {
				saleOrder.setSalesPoint(sales_point);
			}
			if(flag != null && !"".equals(flag) && map1.containsKey(Integer.parseInt(flag))) {
				saleOrder.setOrderNo(map1.get(Integer.parseInt(flag)));
				saleOrder.setOrderVersion(map2.get(Integer.parseInt(flag)));
			}else {
				saleOrder.setOrderNo(order_no);
				saleOrder.setOrderVersion(order_version);
			}
			
			saleOrder.setSalesName(rm_name);
			saleOrder.setCustId(orderModel.getCustNo());
			upmUser.setRealName(rm_name);
			upmUser = (UpmUser) io.getModelByCondition(upmUser);
			saleOrder.setSalesId(upmUser.getUserId());
			saleOrder.setEmailId(io.seq());
			Long magt_fee = (long) (orderModel.getOrderAmount()*Double.parseDouble(orderModel.getProdDiffcoe()));
			saleOrder.setMagtFee(magt_fee);
			Long _deduction = 0L;
			if(!"".equals(deduction.trim())) {
				_deduction = Long.valueOf(deduction);
				saleOrder.setAmountDeduction(_deduction);
			}
			if("".equals(reward_amount.trim()) || Long.parseLong(reward_amount.trim()) < 0) {
				saleOrder.setObcAmount(0l);
			}else {
				saleOrder.setObcAmount(Long.parseLong(reward_amount));
			}
			
			io.insertOrder(saleOrder);
			
			io.updateBillInfo(custInfo.getCustId());
			io.updateCustStatus(custInfo.getCustId());
		}
	}

	private static void insertOrg(InsertOrder io) {
		List<List<String>> list = io.readXLSXWithFormula("/Users/tanyuan/Desktop/机构客户导入模板.xlsx");
		for(List<String> ls: list) {
			String org_name = ls.get(0);
			String org_license = ls.get(1);
			String org_legal = ls.get(2);
			io.insertOrg(org_name, org_license, org_legal);
		}
	}
}
