package NhanVien;

import NhanVien.NhanvienDTO;
import NhanVien.NhanvienBUS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


public class NhanVienGUI extends javax.swing.JPanel {
    DefaultTableModel model;
    JTable tblDSNV;
    JTextField txtMaNV, txtHoten, txtDiachi, txtSDT, txtTim;
    JLabel ltitle, lMa, lHoten, lNgaysinh, lDiachi, lSDT, lTim,lgioitinh;
    JLabel check1,check2,check3,check4,check5,check6;
    JButton btnthem, btnsua, btnxoa,btnreset;
    JScrollPane sptbl;
    JComboBox cbgioitinh,cbngay,cbthang,cbnam;
    String cb[]={"Giới tính","Nam","Nữ"};
    
    JPanel pframe,pmenu,padd;
    private final int weight =950;
    private final int height =750;

    Vector title = new Vector();
    
    public NhanVienGUI() {
        init();
    }
    private int kiemTraHopLe(){
        int kt=0;
        if(txtMaNV.getText().isEmpty()){
            check1.setText("*Mã không được để trống!");
            kt++;
        }
        else if(isNumeric(txtMaNV.getText())==false || txtMaNV.getText().length()!=10){
            check1.setText("*Mã không hợp lệ!");
            kt++;
        }
        if(txtHoten.getText().isEmpty()){
            check2.setText("*Họ không được để trống!");
            kt++;
        }
        else if(isAlpha(txtHoten.getText())==false || txtHoten.getText().length()>30){
            check2.setText("*Họ chứa các kí tự không hợp lệ!");
            kt++;
        }
        if(cbngay.getSelectedIndex()==0){
            if(cbthang.getSelectedIndex()==0){
                if(cbnam.getSelectedIndex()==0){
                    check3.setText("*Ngày, tháng, năm không được để trống!");
                    kt++;
                }
                else{
                    check3.setText("*Ngày và tháng không được để trống!");
                    kt++;
                }
            }
            else if(cbnam.getSelectedIndex()==0){
                check3.setText("*Ngày và năm không được để trống!");
                kt++;
            }
            else{
                check3.setText("*Ngày không được để trống!");
                kt++;
            }
        }
        else if(cbthang.getSelectedIndex()==0){
            if(cbnam.getSelectedIndex()==0){
                check3.setText("*Tháng và năm không được để trống!");
                kt++;
            }
            else{
                check3.setText("*Tháng không được để trống!");
                kt++;
            }
        }
        else if(cbnam.getSelectedIndex()==0){
            check3.setText("*Năm không được để trống!");
            kt++;
        }
        if(cbgioitinh.getSelectedItem()=="Giới tính"){
            check6.setText("*Giới tính không được để trống!");
            kt++;
        }
        if(txtDiachi.getText().isEmpty()){
            check5.setText("*Địa chỉ không được để trống!");
            kt++;
        }
    
        if(txtSDT.getText().isEmpty()){
            check4.setText("*Số điện thoại không được để trống!");
            kt++;
        }
        else if(isNumeric(txtSDT.getText())==false || txtSDT.getText().length()>11 || 
                txtSDT.getText().length()<10){
            check4.setText("*Số điện thoại không hợp lệ!");
            kt++;
        }
        return kt;
    }
    private boolean isNumeric(String str){
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
    private boolean isAlpha(String name){
        char[] chars=name.toCharArray();
        for(char c:chars) {
            if(c==' '){}
            else if(!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

    private void btnThemActionPerformed(ActionEvent e) throws ParseException {
            cleanWarning();
            if(kiemTraHopLe()==0){
            NhanvienDTO nv=new NhanvienDTO();    
            nv.MaNV = txtMaNV.getText();
            nv.TenNV = txtHoten.getText();
            String birth=cbngay.getSelectedItem().toString()+"-"+cbthang.getSelectedItem().toString()+"-"+cbnam.getSelectedItem().toString();
            nv.NgaySinh=new SimpleDateFormat("dd-MM-yyyy").parse(birth);
            nv.DiaChi = txtDiachi.getText();
            nv.SDT = txtSDT.getText();
            nv.Gioitinh= cbgioitinh.getSelectedItem().toString();
            NhanvienBUS bus = new NhanvienBUS();
            bus.them(nv);
            Vector row = new Vector();
            row.add(nv.MaNV);
            row.add(nv.TenNV);
            row.add(new SimpleDateFormat("dd-MM-yyyy").format(nv.NgaySinh));
            row.add(nv.DiaChi);
            row.add(nv.SDT);
            row.add(nv.Gioitinh);
            model.addRow(row);
            tblDSNV.setModel(model);
            }
            else
            JOptionPane.showMessageDialog(null, "thêm thất bại");
        
    }
    private void cleanWarning(){
            check1.setText(null);
            check2.setText(null);
            check3.setText(null);
            check4.setText(null);
            check5.setText(null);
            check6.setText(null);
    }

    private void btnSuaActionPerformed(ActionEvent e) throws ParseException  {
        NhanvienDTO nv = new NhanvienDTO();
        NhanvienBUS bus = new NhanvienBUS();
        int i = tblDSNV.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Xin vui lòng chọn dòng cần sửa!");
        } else {
            cleanWarning();
            nv.MaNV = txtMaNV.getText();
            nv.TenNV = txtHoten.getText().replaceAll("\\s\\s+"," ").trim();
            String birth=cbngay.getSelectedItem().toString()+"-"+cbthang.getSelectedItem().toString()+"-"+cbnam.getSelectedItem().toString();
            nv.NgaySinh=new SimpleDateFormat("dd-MM-yyyy").parse(birth);
            nv.DiaChi = txtDiachi.getText();
            nv.SDT = txtSDT.getText();
            nv.Gioitinh=cbgioitinh.getSelectedItem().toString();
            bus.sua(i, nv);
            model.setValueAt(nv.MaNV, i, 0);
            model.setValueAt(nv.TenNV, i, 1);
            model.setValueAt((new SimpleDateFormat("dd-MM-yyyy").format(nv.NgaySinh)),i,2);
            model.setValueAt(nv.DiaChi, i, 3);
            model.setValueAt(nv.SDT, i, 4);
            model.setValueAt(nv.Gioitinh,i,5);
            tblDSNV.setModel(model);
        }
    }

    private void btnXoaActionPerformed(ActionEvent e) {
        NhanvienDTO nv = new NhanvienDTO();
        NhanvienBUS bus = new NhanvienBUS();
        int i = tblDSNV.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn record cần xóa");
        } else {
            String id = tblDSNV.getModel().getValueAt(i, 0).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa dòng đã chọn có mã: " + id, "Lựa chọn", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                bus.xoa(i, id);
                model.removeRow(i);
                tblDSNV.setModel(model);
            }
        }
    }

    private void timkiemTheokytu(String s) {
        NhanvienBUS bus = new NhanvienBUS();
        s.replaceAll("\\s\\s+", "").trim();
        removeTable();
        xuatBang(bus.timKiem(s));
    }
    private void removeTable(){
        for(int i=model.getRowCount()-1;i>=0;i--){
            model.removeRow(i);
        }
    }
    private void xuatBang(ArrayList<NhanvienDTO>x){
        for(NhanvienDTO nv:x){
            Vector row=new Vector();
            row.add(nv.getMaNV());
            row.add(nv.getTenNV());
            row.add(new SimpleDateFormat("dd-MM-yyyy").format(nv.getNgaySinh()));
            row.add(nv.getDiaChi());
            row.add(nv.getSDT());
            row.add(nv.getGioitinh());
            model.addRow(row);
            tblDSNV.setModel(model);
        }
        x.clear();
    }

    private void selectedRow(MouseEvent e) {
        int i = tblDSNV.getSelectedRow();
        if (i != -1) {
            txtMaNV.setEditable(false);
            txtMaNV.setText(tblDSNV.getModel().getValueAt(i, 0).toString());
            txtHoten.setText(tblDSNV.getModel().getValueAt(i, 1).toString());
            cbngay.setSelectedIndex(Integer.parseInt(tblDSNV.getModel().getValueAt(i, 2).toString().substring(0,2)));
            cbthang.setSelectedIndex(Integer.parseInt(tblDSNV.getModel().getValueAt(i, 2).toString().substring(3,5)));
            cbnam.setSelectedIndex(getIndexOfNam(Integer.parseInt(tblDSNV.getModel().getValueAt(i, 2).toString().substring(6,10))));            
            txtDiachi.setText(tblDSNV.getModel().getValueAt(i, 3).toString());
            txtSDT.setText(tblDSNV.getModel().getValueAt(i, 4).toString());
            cbgioitinh.setSelectedItem(tblDSNV.getModel().getValueAt(i, 5).toString());
        }
    }
    private int getIndexOfNam(int x){
        int z=0;
        for(int i=2021;i>=1950;i--){
            z++;
            if(x==i){
                return z;
            }
        }
        return -1;
    }
   

    void init() {
        JPanel pframe=new JPanel();
        pframe.setBackground(Color.CYAN);
        pframe.setLayout(null);
        
        pframe.setSize(weight,height);
        setSize(weight, height);
        pmenu = new JPanel();
        pmenu.setLayout(null);
        pmenu.setBounds(0, 0, 1000, 40);
        
        padd=new JPanel();
        padd.setBounds(20,50,750,200);
        
        ltitle = new JLabel("Quản Lý Nhân Viên");
        ltitle.setFont(new Font("Calibri", Font.BOLD, 24));
        ltitle.setBounds(400,1, 200, 50);
        
        lMa = new JLabel("Mã Nhân Viên:");
        lMa.setFont(new Font("Calibri", Font.BOLD, 20));
        lMa.setBounds(10, 20, 150, 40);

        lHoten = new JLabel("Họ Tên: ");
        lHoten.setFont(new Font("Calibri", Font.BOLD, 20));
        lHoten.setBounds(10, 70, 150, 40);

        lNgaysinh = new JLabel("Ngày Sinh:");
        lNgaysinh.setFont(new Font("Calibri", Font.BOLD, 20));
        lNgaysinh.setBounds(10, 120, 150, 40);

        lDiachi = new JLabel("Địa Chỉ");
        lDiachi.setFont(new Font("Calibri", Font.BOLD, 20));
        lDiachi.setBounds(400, 70, 150, 40);

        lSDT = new JLabel("SDT");
        lSDT.setFont(new Font("Calibri", Font.BOLD, 20));
        lSDT.setBounds(400, 20, 150, 40);
        
        lgioitinh=new JLabel("Giới tính");
        lgioitinh.setFont(new Font("Calibri", Font.BOLD, 20));
        lgioitinh.setBounds(400, 120, 150, 40);
        
        lTim=new JLabel("Tìm kiếm");
        lTim.setFont(new Font("Calibri", Font.BOLD, 20));
        lTim.setBounds(30,270,150,65);
        
        check1=new JLabel("*Mã không được để trống*");
        check1.setFont(new Font("Calibri", Font.BOLD, 15));
        check1.setForeground(Color.red);
        check1.setBounds(145,50,250,20);
        
        check2=new JLabel("*Tên nhân viên không được để trống*");
        check2.setFont(new Font("Calibri", Font.BOLD, 15));
        check2.setForeground(Color.red);
        check2.setBounds(145,100,250,20);
        
        check3=new JLabel("*Ngày sinh không được để trống*");
        check3.setFont(new Font("Calibri", Font.BOLD, 15));
        check3.setForeground(Color.red);
        check3.setBounds(145,160,250,20);
          
        check4=new JLabel("*Số điện thoại không được để trống*");
        check4.setFont(new Font("Calibri", Font.BOLD, 15));
        check4.setForeground(Color.red);
        check4.setBounds(505,50,250,20);
        
        check5=new JLabel("*Địa chỉ không được để trống*");
        check5.setFont(new Font("Calibri", Font.BOLD, 15));
        check5.setForeground(Color.red);
        check5.setBounds(505,100,250,20);
      
        check6=new JLabel("*Giới tính không được để trống*");
        check6.setFont(new Font("Calibri", Font.BOLD, 15));
        check6.setForeground(Color.red);
        check6.setBounds(505,160,250,20);
               
        txtMaNV = new JTextField();
        txtMaNV.setBounds(145, 20, 200, 30);

        txtHoten = new JTextField();
        txtHoten.setBounds(145, 70, 200, 30);
        
        cbngay=new JComboBox();
        cbngay.setBounds(145,130,70,30);
        cbngay.addItem("Ngày");
        
        cbthang=new JComboBox();
        cbthang.setBounds(215,130,70,30);
        cbthang.addItem("Tháng");
        
        cbnam=new JComboBox();
        cbnam.setBounds(280,130,80,30);
        cbnam.addItem("Năm");
        
        for(int i=1;i<=31;i++){
            cbngay.addItem(Integer.toString(i));
        }
        for(int i=1;i<=12;i++){
            cbthang.addItem(Integer.toString(i));
        }
        for(int i=2021;i>=1950;i--){
            cbnam.addItem(Integer.toString(i));
        }
        
        txtDiachi = new JTextField();
        txtDiachi.setBounds(505, 70, 200, 30);
        
        txtTim=new JTextField();
        txtTim.setBounds(205,270,400,50);
        
        txtSDT = new JTextField();
        txtSDT.setBounds(505, 20, 200, 30);
        
        cbgioitinh=new JComboBox(cb);
        cbgioitinh.setBounds(505, 130, 200, 30);
        
        btnthem = new JButton("Thêm");
        btnthem.setFont(new Font("Calibri", Font.BOLD, 20));
        btnthem.setBounds(800, 50, 100, 40);

        btnsua = new JButton("Sửa");
        btnsua.setFont(new Font("Calibri", Font.BOLD, 20));
        btnsua.setBounds(800, 100, 100, 40);

        btnxoa = new JButton("Xóa");
        btnxoa.setFont(new Font("Calibri", Font.BOLD, 20));
        btnxoa.setBounds(800, 150, 100, 40);
        
        btnreset = new JButton("Reset");
        btnreset.setFont(new Font("Calibri", Font.BOLD, 20));
        btnreset.setBounds(800, 200, 100, 40);

        //Thêm label,textfield vào panel và frame
        pmenu.add(ltitle);   
        pmenu.setBackground(Color.LIGHT_GRAY);
        padd.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        
        padd.add(lMa);
        padd.add(lHoten);
        padd.add(lNgaysinh);
        padd.add(lDiachi);
        padd.add(lSDT);
        padd.add(lgioitinh);
        padd.add(check1);
        padd.add(check2);
        padd.add(check3);
        padd.add(check4);
        padd.add(check5);
        padd.add(check6);
        
        padd.add(txtMaNV);
        padd.add(txtHoten);
        padd.add(cbngay);
        padd.add(cbthang);
        padd.add(cbnam); 
        padd.add(txtDiachi);
        padd.add(txtSDT);
        padd.add(cbgioitinh);
        padd.setLayout(new BorderLayout());
 
        pframe.add(padd);
        pframe.add(pmenu);
        pframe.add(lTim);
        pframe.add(txtTim);
        pframe.add(btnthem);
        pframe.add(btnsua);
        pframe.add(btnxoa);
        pframe.add(btnreset);
        
        add(pframe);
        
        NhanvienBUS bus = new NhanvienBUS();
        tblDSNV = new JTable();
        sptbl = new JScrollPane();
        title.add("Mã Nhân Viên");
        title.add("Họ Tên");
        title.add("Ngày Sinh");
        title.add("Địa Chỉ");
        title.add("SĐT");
        title.add("Giới tính");
        bus.docDSNV();
        model = new DefaultTableModel(title, 0);
        for (NhanvienDTO nv : NhanvienBUS.dsnv) {
            Vector row = new Vector();
            row.add(nv.MaNV);
            row.add(nv.TenNV);
            row.add(new SimpleDateFormat("dd-MM-yyyy").format(nv.NgaySinh));
            row.add(nv.DiaChi);
            row.add(nv.SDT);
            row.add(nv.Gioitinh);
            model.addRow(row);
        }
        tblDSNV.setModel(model);
        sptbl.setBounds(0, 350,weight,(height/2)+25);
        sptbl.setViewportView(tblDSNV);
        pframe.add(sptbl);
        setLayout(null);
       
        btnthem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {    
                try {
                    btnThemActionPerformed(e);
                }
                catch (ParseException ex){
                    JOptionPane.showMessageDialog(null,"Thông tin ngày sinh sai.");
                }
            }
        });
        btnsua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    btnSuaActionPerformed(e);
                }
                catch (ParseException ex){
                    JOptionPane.showMessageDialog(null,"Thông tin ngày sinh sai.");
                }
            }
        });
        btnxoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnXoaActionPerformed(e);
            }
        });
        btnreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtMaNV.setText(null);
                txtHoten.setText(null);
                txtDiachi.setText(null);
                txtSDT.setText(null);
                cbgioitinh.setSelectedItem("Giới tính");
                cbngay.setSelectedIndex(0);
                cbthang.setSelectedIndex(0);
                cbnam.setSelectedIndex(0);
                txtMaNV.setEditable(true);
            }
        });
        txtTim.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void warn(){
                timkiemTheokytu(txtTim.getText());
            }
        });
       
        
        tblDSNV.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow(e);
            }
        });
    }
}
