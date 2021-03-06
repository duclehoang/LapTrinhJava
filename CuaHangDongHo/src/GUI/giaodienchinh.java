package GUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


//import HoaDon.HoaDonGUI;
//import KhachHang.KhachHangGUI;
//import LoaiSanPham.LoaiSanPhamGUI;
//import NhaCungCap.NhaCungCapGUI;
//import NhanVien.NhanVienGUI;
//import NhapHang.NhapHangGUI;
//import PhieuNhap.PhieuNhapGUI;
//
//import SanPham.SanPhamGUI;
//import TaiKhoan.TaiKhoanGUI;
//
//import PhanQuyen.PhanQuyenGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.lang.StackWalker.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import DAO.*;
import BUS.*;
import DTO.*;


public class giaodienchinh extends JFrame {
private int height = 700;
private int width = 1300;
private JList<LeftMenuItem> list;
private BorderLayout mainLayout = new BorderLayout(0,0);

private JPanel content;
private JPanel top;
private JPanel left;

private JLabel luachon;
private JLabel donhangOut=new JLabel();
private JLabel donhangIn =new JLabel();

private JButton logout;

private JPanel cuahang;
private JPanel nhaphang;
private JPanel nhanvien;
private JPanel sanpham;
private JPanel loaisanpham;
private JPanel nhacungcap;
private JPanel khachhang;
private JPanel hoadon;
private JPanel phieunhap;
private JPanel taikhoan;
private JPanel phanquyen;
private JPanel thongke;

private String TenNguoiDung ;
private String tentk;
private ThongTin tt =null;
private String quyennguoidung="";
//public giaodienchinh(String TenNguoiDung) {
public giaodienchinh() {
	ThongTinBUS thongtin = new ThongTinBUS();
	TenNguoiDung = thongtin.tennguoidung();
	
	
	try {
		tt = thongtin.DocFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	tentk= tt.getMatk();
	quyennguoidung = tt.getQuyen();
	//setLayout(mainLayout);
	setSize(width,height);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	top =CreateTopPanel(TenNguoiDung);
	
	left = CreateLeftPanel();
	
	content = new JPanel();
	content.setLayout(new BorderLayout());
	ImageIcon ima = new ImageIcon(new ImageIcon(getClass().getResource("/images/hello.png")).getImage().getScaledInstance(1000, 600, Image.SCALE_AREA_AVERAGING));
	JLabel imag = new JLabel(ima);
	
	content.add(imag);
	
	JPanel container = new JPanel();
	container.setLayout(mainLayout);
	container.add(top,BorderLayout.NORTH);
	container.add(left,BorderLayout.WEST);
	container.add(content,BorderLayout.CENTER);
	container.setBorder(new EmptyBorder(0,10,10,10));
	//khoa thay doi kich thuoc
	setResizable(false);
	add(container);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/*
	//thiet lap giua man hin
	//setLocationRelativeTo(null);
	setVisible(true);
	
	
	//full man hinh
	//setExtendedState(JFrame.MAXIMIZED_BOTH);
	*/
}
	

	public JPanel CreateTopPanel(String Ten) {
		JPanel panel = new JPanel(); //panel chinh	
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		panel.setLayout(new BorderLayout());
		
		ImageIcon iconOut = new ImageIcon();// icon dangxuat
		
		

		iconOut=new ImageIcon(getClass().getResource("/images/logout.png"));
		logout = new JButton(Ten);
		logout.setIcon(iconOut);
		logout.setBackground(new Color(220,220,220));
		logout.setFont(font);
		
		//tennguoidung = new JLabel("")
		//dangxuat.add(logout);
		//dangxuat.add(tennguoidung);
		
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
                
                if(JOptionPane.showConfirmDialog(content, "B???n c?? mu???n ????ng xu???t?","Ch?? ??",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                	setVisible(false);
                	new FormDangNhap();
                }
            }
		});
		
		luachon = new JLabel("", SwingConstants.CENTER);
		
		luachon.setFont(font);
		
		ImageIcon pass = new ImageIcon();// icon dangxuat
		
		

		pass=new ImageIcon(getClass().getResource("/images/secure.png"));
		
		JButton doimk = new JButton("ChangePassword");
		doimk.setIcon(pass);
		doimk.setBackground(new Color(220,220,220));
		doimk.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				JFrame f= new JFrame();
				JPanel p =new DoiMKGUI(tentk,tt);
				f.setSize(400, 300);;
				f.add(p);
				f.setVisible(true);
				
			}
		});
		panel.add(logout,BorderLayout.WEST);
		panel.add(luachon,BorderLayout.CENTER);
		panel.add(doimk,BorderLayout.EAST);
		
		
		panel.setBorder(new EmptyBorder(10, 10, 10, 30));
		return panel;
	}
	
	

	public JPanel CreateLeftPanel() {
		
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane sp = new JScrollPane(list = createList());
		panel.add(sp,BorderLayout.CENTER);
		return panel;
	}
	
	private JList<LeftMenuItem> createList() {
		DefaultListModel<LeftMenuItem> model = new DefaultListModel<>();
		model.addElement(new LeftMenuItem("C???a h??ng","shop"));
		model.addElement(new LeftMenuItem("Nh???p h??ng","checklist"));
		model.addElement(new LeftMenuItem("S???n ph???m","watch"));
		model.addElement(new LeftMenuItem("Lo???i s???n ph???m","phanloai"));
		model.addElement(new LeftMenuItem("H??a ????n","bill"));
		model.addElement(new LeftMenuItem("Phi???u nh???p","phieunhap"));
		
		model.addElement(new LeftMenuItem("Nh??n vi??n","employee"));
		
		model.addElement(new LeftMenuItem("Kh??ch h??ng","user"));
		model.addElement(new LeftMenuItem("Nh?? cung c???p","company"));
		model.addElement(new LeftMenuItem("Th???ng k??","thongke"));
		model.addElement(new LeftMenuItem("Ph??n quy???n","shield"));
		model.addElement(new LeftMenuItem("T??i kho???n","account"));
		
		
		list = new JList<LeftMenuItem>(model);
		list.setCellRenderer(new LeftMenuItemRenderer());
		
		
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				LeftSelec();
			}
		});
		return list;
	}
	public JPanel ImagePanel(){
	    //Create game panel and attributes
	    JPanel gamePanel = new JPanel();
	    ImageIcon ima = new ImageIcon(new ImageIcon(getClass().getResource("/images/sorry.png")).getImage().getScaledInstance(1000, 600, Image.SCALE_AREA_AVERAGING));
	    JLabel lb = new JLabel(ima);
	    gamePanel.add(lb);
	    //Set Return
	    return gamePanel;
	}
	private void LeftSelec() {
		String nameAction=String.valueOf(list.getSelectedValue());
		//JOptionPane.showConfirmDialog(this, nameAction);
		content.removeAll();
		top.remove(donhangIn);
		top.remove(donhangOut);
		JPanel imag = ImagePanel();
		switch(nameAction){
			case "C???a h??ng":{
				luachon.setText("C???A H??NG");
				
				
				cuahang = new CuaHangGUI();
				
				content.add(cuahang);
				break;
			}
			case "H??a ????n":{
				luachon.setText("H??A ????N");
				
				hoadon = new HoaDonGUI();
				
				content.add(hoadon);
				break;
			}
			case"Phi???u nh???p":{
				luachon.setText("PHI???U NH???P");
				
				phieunhap = new PhieuNhapGUI();
				
				content.add(phieunhap);
				break;
			}
			case "S???n ph???m":{
				luachon.setText("S???N PH???M");
				if(sanpham == null) {
					sanpham= new SanPhamGUI();
					
				}
				content.add(sanpham);
				
				break;
			}
			case "Lo???i s???n ph???m":{
				luachon.setText("LO???I S???N PH???M");
				if(loaisanpham == null) {
					loaisanpham= new LoaiSanPhamGUI();
					
				}
				content.add(loaisanpham);
				
				break;
			}
			case "Nh???p h??ng":{
				
				
				luachon.setText("NH???P H??NG");
				if(nhaphang ==null) {
				nhaphang = new NhapHangGUI();
				}
				content.add(nhaphang);
				break;
			}
			case "Nh??n vi??n":{
				luachon.setText("NH??N VI??N");
				if(quyennguoidung.equals("Q2") || quyennguoidung.equals("Q1")) {
					
					if(nhanvien == null) {
					nhanvien = new NhanVienGUI();
					}
					content.add(nhanvien);
					break;
				}
				else {
					
					JOptionPane.showMessageDialog(null, "B???n kh??ng ????? quy???n truy c???p!!!");
					content.add(imag);
					break;
				}
				
			}
			case "Th???ng k??":{
				luachon.setText("TH???NG K??");
				if(quyennguoidung.equals("Q2") || quyennguoidung.equals("Q1")) {
					thongke = new ThongKeGUI();
					content.add(thongke);
					
					break;
				}
				else {
					JOptionPane.showMessageDialog(null, "B???n kh??ng ????? quy???n truy c???p!!!");
					content.add(imag);
					break;
				}
				
			}
			case "Kh??ch h??ng":{
				luachon.setText("KH??CH H??NG");
			
				khachhang = new KhachHangGUI();
				
				content.add(khachhang);
				break;
			}
			case "Nh?? cung c???p":{
				luachon.setText("NH?? CUNG C???P");
				if(nhacungcap == null) {
				nhacungcap = new NhaCungCapGUI();
				}
				content.add(nhacungcap);
				break;
			}
			case "T??i kho???n":{
				luachon.setText("T??I KHO???N");
				if(quyennguoidung.equals("Q1")) {
					
					if(taikhoan ==null) {
					taikhoan = new TaiKhoanGUI();
					}
					content.add(taikhoan);
				}
				else {
					JOptionPane.showMessageDialog(null, "B???n kh??ng ????? quy???n truy c???p!!!");
					content.add(imag);
				}
				break;
			}
			case "Ph??n quy???n":{
				luachon.setText("PH??N QUY???N");
				if(quyennguoidung.equals("Q1")) {
					
					if(phanquyen ==null) {
					phanquyen = new PhanQuyenGUI();
					}
					content.add(phanquyen);
				}
				else {
					JOptionPane.showMessageDialog(null, "B???n kh??ng ????? quy???n truy c???p!!!");
					content.add(imag);
				}
				break;
			}
		}
		
		revalidate();
		repaint();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new giaodienchinh() ;
		 
		
	}

}
