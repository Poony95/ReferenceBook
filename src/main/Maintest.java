package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.BookDAO;
import vo.BookVO;

public class Maintest extends JFrame {
	JTextField jtf_search;
	JTable table;
	Vector<String> colname;
	Vector<Vector<String>> rowdata;
	
	public Maintest() {
		colname = new Vector<String>();
		colname.add("도서번호");
		colname.add("도서이름");
		colname.add("출판사");
		colname.add("가격");
		
		jtf_search = new JTextField(15);
		rowdata = new Vector<Vector<String>>();
		table = new JTable(rowdata,colname);
		JScrollPane jsp = new JScrollPane(table);
		
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(new JLabel("고객 이름 : "));
		p.add(jtf_search);
		JButton btn = new JButton("검색");
		p.add(btn);
		
		add(p, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		
		loadlist();
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rowdata.clear();
				BookDAO dao = new BookDAO();
				String name = jtf_search.getText();
				ArrayList<BookVO> list = dao.recommandBook(name);
				
				for( BookVO b: list) {
					Vector<String> v = new Vector<>();
					v.add(b.getBookied()+"");
					v.add(b.getBookname());
					v.add(b.getPublisher());
					v.add(b.getPrice()+"");
					rowdata.add(v);
				}
				table.updateUI();
			}
		});
	}
	
	public void loadlist() {
		rowdata.clear();
	
		BookDAO dao = new BookDAO();
		ArrayList<BookVO> list = dao.loadlist();
		for(BookVO b:list) {
			Vector<String> v = new Vector<>();
			v.add(b.getBookied()+"");
			v.add(b.getBookname());
			v.add(b.getPublisher());
			v.add(b.getPrice()+"");
			rowdata.add(v);
		}
		table.updateUI();
	}
	public static void main(String[] args) {
		new Maintest();
	}

}
