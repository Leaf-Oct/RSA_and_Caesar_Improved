

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Client extends JFrame {
	private InetAddress target;
	private JPanel contentPane; // bottom pane
	private JLabel lblThisName; // show user name
	private JTextField transformMessage; // message send text frame
	private JButton buttonSend; // send button
	private JTextArea textReceive; // message receiving area
	private Container con;
	private DatagramSocket receive;

	public Client(String ip) {
		// TODO Auto-generated constructor stub
		super("UDP客户端");
		try {
			target = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("你麻麻的这是什么ip");
			dispose();
			System.exit(0);
		}
		setSize(1024, 768);
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		con = getContentPane();
		con.setLayout(null);
		setLayout();
		try {
			receive = new DatagramSocket(2333);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadMessageThread rmt = new ReadMessageThread(receive);
		rmt.start();
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				receive.close();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setLayout() {
//		bottom input pane
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 650, 1024, 68);
		con.add(contentPane);
//		Left Name
		lblThisName = new JLabel("本机");
		lblThisName.setBounds(0, 0, 100, 68);
		contentPane.add(lblThisName);
//		input text frame
		transformMessage = new JTextField();
		transformMessage.setBounds(100, 0, 800, 68);
		contentPane.add(transformMessage);
//		send button
		buttonSend = new JButton("Send");
		buttonSend.setBounds(900, 0, 100, 68);
		contentPane.add(buttonSend);
//		text receive
		textReceive = new JTextArea();
		textReceive.setBounds(10, 20, 984, 600);
		textReceive.setEditable(false);
		JPanel show_massage = new JPanel();
		show_massage.setLayout(null);
		show_massage.setBounds(10, 10, 1004, 630);
		show_massage.add(textReceive);
		show_massage.setBorder(BorderFactory.createTitledBorder("Chat Area"));
		con.add(show_massage);

//		send button event
		buttonSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String source = transformMessage.getText();
				textReceive.append("我\n"+source+"\n");
				transformMessage.setText("");
				System.out.println("原文"+source);
				String caesar_secret = Kaisa_improved.encrypt(source);
				System.out.println("凯撒加密"+caesar_secret+caesar_secret.length());
				String RSA_secret[] = RSA.Encryption(caesar_secret);
				DatagramSocket sender = null;
				DatagramPacket data;
				try {
					sender = new DatagramSocket();
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (String s : RSA_secret) {
					System.out.println(s);
					byte[] b = s.getBytes();
					data = new DatagramPacket(b, b.length, target, 2333);
					try {
						sender.send(data);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("上面是先凯撒后RSA的结果\n");
				byte[] end = "%end%".getBytes();
				data = new DatagramPacket(end, end.length, target, 2333);
				try {
					sender.send(data);
//					System.out.println("发送语句结束符号");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					sender.close();
				}
			}
		});
	}

	private class ReadMessageThread extends Thread {
		DatagramSocket receiver;

		public ReadMessageThread(DatagramSocket ds) {
			// TODO Auto-generated constructor stub
			receiver = ds;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			DatagramPacket pack = null;

			byte b[] = new byte[8192];
			try {
				pack = new DatagramPacket(b, b.length);
			} catch (Exception e) {}
			ArrayList<String> RSA_message=new ArrayList<>();
			while(true) {
				try {
					if(receive.isClosed()) {
						break;
					}
					receiver.receive(pack);
					String s=new String(pack.getData(),0,pack.getLength());
					if(s.equals("%end%")) {
						String RSA_secret[]=RSA_message.toArray(new String[RSA_message.size()]);
						String caesar=RSA.Decryption(RSA_secret);
						String original=Kaisa_improved.decrypt(caesar);
						RSA_message.clear();
						textReceive.append(pack.getAddress().toString()+"\n"+original+"\n");
						continue;
					}
					RSA_message.add(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
