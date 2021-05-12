package KhachHang;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
public class ButtonRemoveGUI extends JButton{
	public ButtonRemoveGUI() {
		this.setText(" Xóa ");
		this.setIcon(new ImageIcon(this.getClass().getResource("/images/remove.png")));
		this.setBackground(new Color(220,220,220));
		//this.setForeground(Color.WHITE);	//đổi màu chữ
		this.addMouseListener(new MouseAdapter() {	//hover
						
			@Override
			public void mouseExited(MouseEvent e) {	
				// TODO Auto-generated method stub
				setBackground(new Color(220,220,220));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				setBackground(new Color(169,169,169));
			}
		});
	}

}
