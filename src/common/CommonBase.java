package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.tool.PrettyPrinter;

import order.OrderInfo;

public class CommonBase {
	DataSourceProp dsp;
	Connection conn;
	Map<String, String> areaMap;
	
	public CommonBase() {
		super();
		dsp = new DataSourceProp();
		this.connectPostgresConnect();
		areaMap = new HashMap<String, String>();
		areaMap.put("福州", "FZ");
		areaMap.put("杭州", "HZ");
		areaMap.put("上海", "SH");
		areaMap.put("广州", "GZ");
		areaMap.put("青岛", "QD");
		areaMap.put("珠海", "ZH");
		areaMap.put("北京", "BJ");
		areaMap.put("深圳", "SZ");
		areaMap.put("激活", "JH");
		areaMap.put("内部", "NB");
		areaMap.put("家族", "JZ");
		areaMap.put("总部", "ZB");
	}

	public DataSourceProp getDsp() {
		return dsp;
	}

	public void setDsp(DataSourceProp dsp) {
		this.dsp = dsp;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void connectPostgresConnect() {
		try {
			Class.forName(this.dsp.getDrivername());
			this.conn = DriverManager.getConnection(this.dsp.getConnecturl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Statement createStatement() {
		Statement stmt = null;
		try {
			stmt = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public Map<Integer, String[]> readCSVFile(String filename) {
		BufferedReader br = null;
		File file = new File(filename);
		Map<Integer, String[]> map = new HashMap<Integer, String[]>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
			String line = null;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] result = line.split(",");
				map.put(i, result);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String dictMapping(String name) {
		return areaMap.get(name);
	}
	
	public List<List<String>> readXLSXWithFormula(String filename) {
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			FileInputStream file = new FileInputStream(new File(filename));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				List<String> l = null;
				l = new ArrayList<String>();
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch(evaluator.evaluateInCell(cell).getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								l.add(sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
							}else if (cell.getCellStyle().getDataFormatString().indexOf("%") != -1) {
								BigDecimal b = new BigDecimal(cell.getNumericCellValue()*100);
								double f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
								l.add(String.valueOf(f1));
							}else {
								l.add(String.valueOf(new DecimalFormat("#").format(cell.getNumericCellValue())));
							}
							break;
						case Cell.CELL_TYPE_STRING:
							l.add(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							System.out.println(cell.getCellFormula());
							l.add(String.valueOf(cell.getCellFormula()));
							break;
						case Cell.CELL_TYPE_BLANK:
							l.add("");
							break;
						default:
							l.add("");
							break;
					}
				}
				list.add(l);
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean isExistCustByName(String cust_name) {
		String sql = "select * from cust_info where cust_name='" + cust_name + "'";
		return this.ExecuteQuery(sql);
	}

	public boolean isExistRMByName(String rm_name) {
		String sql = "select * from upm_user where real_name='" + rm_name + "'";
		return this.ExecuteQuery(sql);
	}
	
	public boolean isExistRMByEmployeeCode(String employee_code) {
		String sql = "select * from upm_user where employee_code='" + employee_code + "'";
		return this.ExecuteQuery(sql);
	}
	
	public boolean isExistRMByEmail(String email) {
		String sql = "select * from upm_user where email='" + email + "'";
		return this.ExecuteQuery(sql);
	}

	public boolean isExistCustByMobile(String mobile) {
		String sql = "select * from cust_info where cust_cell='" + mobile + "'";
		return this.ExecuteQuery(sql);
	}
	
	public void updateUserByEmailWithPhone(String email, String phone) {
		String sql = "update upm_user set phone = "+phone+" where email = '"+email+"'";
		this.ExecuteUpdate(sql);
	}
	
	public boolean isExistProductByName(String name) {
		String sql = "select * from product_info where prod_name='"+name+"'";
		return this.ExecuteQuery(sql);
	}
	
	public boolean isExistLPByName(String name) {
		String sql = "select * from lp_info where partner_com_name='"+name+"'";
		return this.ExecuteQuery(sql);
	}
	
	public boolean isExistOrgByName(String name) {
		String sql = "select * from org_info where org_name='"+name+"'";
		return this.ExecuteQuery(sql);
	}
	
	public void insertUser(int user_id, String employee_code, String real_name, String sex_name, String area_name, String phone, String email) {
		String sex = "";
		String area = "";
		if("男".equals(sex_name)) {
			sex = "male";
		}else {
			sex = "female";
		}
		if("上海".equals(area_name)) {
			area = "SH";
		}else if("杭州".equals(area_name)) {
			area = "HZ";
		}else if("深圳".equals(area_name)) {
			area = "SZ";
		}else if("激活".equals(area_name)) {
			area = "JH";
		}
		String sql = "insert into upm_user (user_id, employee_code, real_name, sex, area, phone, status, email) values ("+user_id+", '"+employee_code+"', '"+real_name+"','"+sex+"' ,'"+area+"', "+ phone+", 'exitWork', '"+email+"')";
//		System.out.println(sql);
		this.ExecuteUpdate(sql);
	}
	
	public void insertOrg(String org_name, String org_license, String org_legal) {
		Long id = this.seq();
		String sql = "insert into org_info (org_id, org_name, org_license, org_legal) values ("+id+", '"+org_name+"', '"+org_license+"', '"+org_legal+"')";
//		System.out.println(sql);
		this.ExecuteUpdate(sql);
	}
	
	public String convertModelNameToDBName(String classname) {
		StringBuilder sb = new StringBuilder(); ;
		for(int i=0; i<classname.length(); i++) {
			char c = classname.charAt(i);
			if(Character.isUpperCase(c) && i!=0) {
				sb.append('_');
			}
			sb.append(c);
		}
		return sb.toString().toLowerCase();
	}
	
	public Object getFieldValueByName(String fieldName, Object o) {  
       try {    
           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
           String getter = "get" + firstLetter + fieldName.substring(1);   
           Method method = o.getClass().getMethod(getter, new Class[] {});    
           Object value = method.invoke(o, new Object[] {});    
           return value;    
       } catch (Exception e) {    
    	   e.printStackTrace();
           return null;    
       }    
    }
	
	public Object getModelByCondition(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		String dbname = this.convertModelNameToDBName(object.getClass().getSimpleName());
		String sql = "";
		sql += "select * from "+dbname;
		int i = 0;
		for(Field field: fields) {
			Object value = this.getFieldValueByName(field.getName(), object);
			if(value != null) {
				String fieldName = convertModelNameToDBName(field.getName());
				switch (field.getType().getSimpleName()) {
					case "String":
						if(i==0) {
							sql += " where "+fieldName+"='"+value+"'";
							i++;
						}else {
							sql += " and "+fieldName+"='"+value+"'";
						}
						break;
					case "Long": 
						if((Long)value != 0) {
							if(i == 0) {
								sql += " where "+fieldName+"="+value;
								i++;
							}else {
								sql += " and "+fieldName+"="+value;
							}
						}
						break;
					case "int":
					case "Integer":
						if((int)value != 0) {
							if(i == 0) {
								sql += " where "+fieldName+"="+value;
								i++;
							}else {
								sql += " and "+fieldName+"="+value;
							}
						}
						break;
					default:
						System.out.println("getModelByCondition error:"+fieldName+"-"+value);
						break;
				}
			}
		}
		Statement st = this.createStatement();
		try {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				for(Field field: fields) {
					String fieldName = convertModelNameToDBName(field.getName());
					switch (field.getType().getSimpleName()) {
						case "String":
							field.set(object, rs.getString(fieldName));
							break;
						case "Long": 
							field.set(object, rs.getLong(fieldName));
							break;
						case "int":
							field.set(object, rs.getInt(fieldName));
							break;
						case "Integer":
							field.set(object, rs.getInt(fieldName));
							break;
						case "Date":
							field.set(object, rs.getDate(fieldName));
							break;
						default:
							System.out.println("getModelByCondition error"+fieldName+field.getType().getSimpleName());
							break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
//		System.out.println(object);
		return object;
	}
	
	public void insertOrder(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		String sql = "";
		String dbname = this.convertModelNameToDBName(object.getClass().getSimpleName());
		int i = 0;
		sql += "insert into "+ dbname+"(";
		for(Field field: fields) {
			Object value = this.getFieldValueByName(field.getName(), object);
			if(value != null) {
				String fieldName = convertModelNameToDBName(field.getName());
				if(i == 0) {
					sql += fieldName;
				}else {
					sql += ","+fieldName;
				}
				i++;
//				System.out.println("name:"+fieldName+",value:"+value);
			}
		}
		sql += ") values (";
		i = 0;
		for(Field field: fields) {
			Object value = this.getFieldValueByName(field.getName(), object);
			if(value != null) {
				if(i != 0) {
					sql += ","; 
				}
				switch (field.getType().getSimpleName()) {
					case "Timestamp":
					case "Date": 
					case "String":
						sql += "'"+value+"'";
						break;
					case "Long":
					case "Double":
					case "double":
					case "int":
					case "Integer":
						sql += value;
						break;
					default:
						System.out.println("insertOrder error name:"+field.getType().getSimpleName());
						break;
				}
				i++;
			}
		}
		sql += ");";
//		System.out.println(sql);
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBillInfo(Long cust_id) {
		String sql = "UPDATE bill_info SET effect_sign='1' WHERE id="+cust_id;
		this.ExecuteUpdate(sql);
	}
	
	public void updateCustStatus(Long cust_id) {
		String sql = "update cust_info set cust_level='2' where cust_id="+cust_id;
		this.ExecuteUpdate(sql);
	}
	
	public String getUserRealName(String cust_name) {
		String sql = "select u.real_name from upm_user u, sales_cust_rel s, cust_info c where u.user_id = s.sales_id and c.cust_id = s.cust_id and c.cust_name='"+cust_name+"'";
		Statement st = this.createStatement();
		String str = "空";
		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString("real_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public void updateSalesOrder(Long order_no, Double sales_point, Long abc_amount) {
		String sql = "update sale_order set sales_point="+sales_point+",obc_amount="+abc_amount+" where order_no="+order_no;
		System.out.println(sql);
		this.ExecuteUpdate(sql);
	}
	
	public Long getOrderNoByContractNo(String contract_no) {
		String sql = "select order_no from order_info where contract_no='"+contract_no+"'";
		Statement st = this.createStatement();
		Long order_no = 0l;
		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				order_no = rs.getLong("order_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order_no;
	}
	
	public long seq(){
		Statement st = this.createStatement();
    	String sql="select * from public.seq_id()";
    	Long id = 0L;
    	try {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return id;
	}
	
	public void ExecuteUpdate(String sql) {
		Statement st = this.createStatement();
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public boolean ExecuteQuery(String sql) {
		Statement st = this.createStatement();
		boolean isExist = true;
		try {
			ResultSet rs = st.executeQuery(sql);
			if (!rs.next()) {
				isExist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
}

class DataSourceProp {
	String drivername = "org.postgresql.Driver";
	String url = "jdbc:postgresql://192.168.8.205:5432/oauth";
	String username = "psoauth";
	String password = "Pr0nsav@1234";
	String connecturl;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnecturl() {
		return connecturl;
	}

	public void setConnecturl(String connecturl) {
		this.connecturl = connecturl;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public DataSourceProp(String url, String username, String password, String drivername) {
		super();
		StringBuffer sb = new StringBuffer();
		this.url = url;
		this.username = username;
		this.password = password;
		this.drivername = drivername;
		this.connecturl = sb.append(this.url + "?" + "user=" + this.username + "&password=" + this.password).toString();
	}

	public DataSourceProp() {
		super();
		StringBuffer sb = new StringBuffer();
		this.connecturl = sb.append(this.url + "?" + "user=" + this.username + "&password=" + this.password).toString();
	}
}
