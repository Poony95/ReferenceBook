package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import db.ConnectionProvider;
import vo.BookVO;

public class BookDAO {
	
	public ArrayList<BookVO> loadlist (){
		ArrayList<BookVO> list = new ArrayList<>();
		
		try {
			String sql = "select * from book order by bookid";
			
			Connection conn = ConnectionProvider.getconnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int bookied = rs.getInt(1);
				String bookname = rs.getString(2);
				String publisher = rs.getString(3);
				int price = rs.getInt(4);
				
				BookVO vo = new BookVO(bookied, bookname, publisher, price);
				list.add(vo);
			}
			ConnectionProvider.close(rs, stmt, conn);
		} catch (Exception e) {
			System.out.println("예외발생 load"+e.getMessage());
		}
		return list;
	}
	
	public ArrayList<BookVO> recommandBook(String name){
		ArrayList<BookVO> list = new ArrayList<>();
		try {
			String sql = "select * from "
					+ "book where  "
					+ "bookid in (select distinct bookid  "
					+ "from (select distinct bookid  "
					+ "from orders "
					+ "where custid in (select custid  "
					+ "from ( select custid, count(custid) "
					+ "from orders  "
					+ "where bookid in (select bookid "
					+ "from orders "
					+ "where custid =  "
					+ "(select custid from customer where name=?)) "
					+ "and custid != (select custid from  "
					+ "				customer where name = ?) "
					+ "group by custid "
					+ "order by count(custid) desc) "
					+ "where rownum <= 3) ) "
					+ "where bookid not in (select bookid "
					+ "from orders "
					+ "where custid =  "
					+ "(select custid from customer where name=?)) "
					+ "union "
					+ "select bookid "
					+ "from (select bookid, count(bookid) "
					+ "from orders "
					+ "group by bookid "
					+ "order by count(bookid) desc) "
					+ "where rownum <= 5)";
			
				Connection conn = ConnectionProvider.getconnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, name);
				pstmt.setString(3, name);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int bookied = rs.getInt(1);
					String bookname = rs.getString(2);
					String publisher = rs.getString(3);
					int price = rs.getInt(4);
					
					BookVO vo = new BookVO(bookied, bookname, publisher, price);
					list.add(vo);
				}
				ConnectionProvider.close(rs, pstmt, conn);
			} catch (Exception e) {
				System.out.println("예외발생 recommand: "+e.getMessage());
			}
	
		return list;
	}
}
