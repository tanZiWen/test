package order;

import java.util.List;

import common.CommonBase;
import common.OrderInfo;

public class UpdatePoint extends CommonBase{
	public static void main(String[] args) {
		UpdatePoint up = new UpdatePoint();
		List<List<String>> list = up.readXLSXWithFormula("/Users/tanyuan/Desktop/更新提成点.xlsx");
		for(List<String> l:list) {
//			System.out.println(l.get(0)+"-"+l.get(1)+"-"+l.get(2));
			String contract_name = l.get(0);
			String sales_point = l.get(1);
			String abc_amount = l.get(2);
			Long order_no = up.getOrderNoByContractNo(contract_name);
			up.updateSalesOrder(order_no, Double.parseDouble(sales_point), Long.parseLong(abc_amount));
//			OrderInfo om = new OrderInfo();
//			om.setContractNo(l.get(0).trim());
//			om = (OrderInfo) up.getModelByCondition(om);
//			System.out.println(om.getOrderNo());
		}
	}
}
