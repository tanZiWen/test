package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportCustomerWithEmail extends CommonBase {
	public static void main(String[] args) {
		ExportCustomerWithEmail ecw = new ExportCustomerWithEmail();
		Statement stmt = ecw.createStatement();
		String sql = "select cust_name, cust_cell, email from cust_info where email != ''";
		File outFile = new File("/Users/tanyuan/Documents/email.csv"); // 读取的CSV文件
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "GBK"));
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				String cust_name = result.getString("cust_name");
				String cust_cell = result.getString("cust_cell");
				String email = result.getString("email");
				writer.write(cust_name + "," + cust_cell + "," + email);
				writer.newLine();
			}
			writer.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Success!");
	}
}
