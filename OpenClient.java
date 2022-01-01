

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class OpenClient extends JFrame{
	/**
	 * ??????
	 */
	private static final long serialVersionUID = 1L;
	private Container con;
	private JTextField ip;
	private JTextField caesar_key,e,d,n;
	private JButton open;
	private JLabel ip_label,caesar_label,e_label,d_label,n_label;
	public OpenClient() {
		// TODO Auto-generated constructor stub
		super("打开客户端");
		setSize(500, 300);
		setLocation(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		con=getContentPane();
		con.setLayout(null);
		
		ip=new JTextField();
		caesar_key=new JTextField();
		open=new JButton("!开始聊天!");
		ip.setBounds(50, 30, 200, 30);
		caesar_key.setBounds(50, 80, 300, 30);
		open.setBounds(350, 100, 100, 30);
		con.add(ip);
		con.add(caesar_key);
		con.add(open);
		
		e=new JTextField();
		e.setBounds(50, 130, 300, 30);
		d=new JTextField();
		d.setBounds(50, 180, 300, 30);
		n=new JTextField();
		n.setBounds(50, 230, 300, 30);
		con.add(d);
		con.add(e);
		con.add(n);
		
		ip_label=new JLabel("对方IP");
		ip_label.setBounds(0, 30, 50, 30);
		caesar_label=new JLabel("凯撒密钥");
		caesar_label.setBounds(0, 80, 50, 30);
		e_label=new JLabel("RSA-e");
		e_label.setBounds(0, 130, 50, 30);
		d_label=new JLabel("RSA-d");
		d_label.setBounds(0, 180, 50, 30);
		n_label=new JLabel("RSA-n");
		n_label.setBounds(0, 230, 50, 30);
		con.add(ip_label);
		con.add(caesar_label);
		con.add(e_label);
		con.add(d_label);
		con.add(n_label);
		
		caesar_key.setText("leafoct");
		e.setText("2588418562024289976799582843350608998423289479056907390543315382295563767868232312852843416726501755774973226440974813674941113661015800373449668875839");
		d.setText("2403509139930663068388848472037919602910929627117356054684822754109030363159034430777117621464342871724054539765137648706175005662965708776607047588966412176558340251791841186675151427155943707830125754243637350208894449377203124088695308020194751463802094030624461282639350317245572799474260833914703");
		n.setText("6854265937186164953698379532342615163128019350608565735554587703684646125818775430275586159840324746599217278355481393768373186245683891747322516303615844436959011545512431483619042401981209646630264851069386260117684920786932882910025039399388930539829216783385699939378370891545153100306945259600831");
		
		setVisible(true);
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!ip.getText().equals("")&&!caesar_key.getText().equals("")&&!OpenClient.this.e.getText().equals("")
						&&!d.getText().equals("")&&!n.getText().equals("")) {
					Kaisa_improved.setSecretKey(caesar_key.getText());	
					RSA.e=new BigInteger(OpenClient.this.e.getText());
					RSA.d=new BigInteger(d.getText());
					RSA.n=new BigInteger(n.getText());
					System.out.println("RSA密钥设置成功");
					dispose();
					Client c=new Client(ip.getText());
					c.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "不能有任何项为空","FBI WARNING",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new OpenClient();
	}

}
