import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;





public class GUI {

	//
	JFrame frame;
	JButton doIt;
	OnetimePad otp;
	JTextField key;
	JLabel keyl;
	File outFilef;
	
	
	//input part
	JTextArea in;
	JFileChooser inFile;
	JLabel inFileName;
	JButton inFileChoose;
	//output part
	JTextArea out;
	JFileChooser outFile;
	JLabel outFileName;
	JButton outFileChoose;
	
	//configuration part
	ButtonGroup ed; //button group for encryption/decryption switching
	JLabel encDec;
	JLabel binASCII;
	ButtonGroup ba; //button group for binary/ascii mode switching
	JRadioButton enc, dec;
	JRadioButton bin, ascii;
	
	
	void initGUI () {
		
		doIt = new JButton("Wykonaj.");
		key = new JTextField();
		key.setSize(new Dimension(200,20 ));
		key.setMaximumSize(new Dimension(200,20 ));
		keyl = new JLabel("Klucz:");
		
		inFile = new JFileChooser();
		outFile = new JFileChooser();
		in = new JTextArea();
		in.setLineWrap(true);
		in.setAutoscrolls(true);
		in.setSize(100, 100);
		in.setMinimumSize(in.getSize());
		out = new JTextArea();
		out.setLineWrap(true);
		out.setAutoscrolls(true);
		out.setSize(100,100);
		out.setMinimumSize(out.getSize());
		
		inFileName = new JLabel("no file");
		inFileName.setMinimumSize(new Dimension(200,20));
		outFileName = new JLabel("no file");
		outFileName.setMinimumSize(new Dimension(200,20));
		
		encDec = new JLabel("Co chcesz zrobic? ");
		binASCII = new JLabel("Sposób odczytu/zapisu: ");
		
		ed = new ButtonGroup();
		enc = new JRadioButton("zaszyfruj");
		enc.setSelected(true);
		dec = new JRadioButton("odszyfruj");
		ed.add(enc);
		ed.add(dec);
		
		ba = new ButtonGroup();
		bin = new JRadioButton("BIN");
		ascii = new JRadioButton("ASCII");
		ascii.setSelected(true);
		ba.add(bin);
		ba.add(ascii);
		
		inFileChoose = new JButton("wejście");
		outFileChoose = new JButton("wyjście");
		
		otp = new OnetimePad();
		otp.setMode(EncryptionMode.ASCII);
		frame = new JFrame();
		
		JScrollPane outp = new JScrollPane(out);
		JScrollPane inp = new JScrollPane(in);
		GroupLayout l = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(l);
		l.setAutoCreateGaps(true);
		l.setAutoCreateContainerGaps(true);
		l.setHorizontalGroup(l.createSequentialGroup()
				///options
				.addGroup(l.createParallelGroup()
					.addComponent(encDec)
					.addComponent(enc)
					.addComponent(dec)
					.addComponent(binASCII)
					.addComponent(bin)
					.addComponent(ascii)
				)
				///in
				.addGroup(l.createParallelGroup()
					.addComponent(inp)
					.addComponent(inFileName)
					.addComponent(inFileChoose)
				)
				///out
				.addGroup(l.createParallelGroup()
					.addComponent(outp)
					.addComponent(outFileName)
					.addComponent(outFileChoose)
				)
				.addGroup(l.createParallelGroup()
					.addComponent(keyl)
					.addComponent(key)
					.addComponent(doIt)
				)
				);
		
		l.setVerticalGroup(l.createParallelGroup()
				///options
				.addGroup(l.createSequentialGroup()
					.addComponent(encDec)
					.addComponent(enc)
					.addComponent(dec)
					.addComponent(binASCII)
					.addComponent(bin)
					.addComponent(ascii)
				)
				///in
				.addGroup(l.createSequentialGroup()
					.addComponent(inp)
					.addComponent(inFileName)
					.addComponent(inFileChoose)
				)
				///out
				.addGroup(l.createSequentialGroup()
					.addComponent(outp)
					.addComponent(outFileName)
					.addComponent(outFileChoose)
				)
				.addGroup(l.createSequentialGroup()
					.addComponent(keyl)
					.addComponent(key)
					.addComponent(doIt)
				)
				);
		
		
		frame.pack();
		frame.setSize(760, 300);
		frame.setMinimumSize(new Dimension(700, 200));

		
	}
	
	void setActions() {
		
		//mode selection
		bin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JRadioButton)(e.getSource())).isOpaque()) {
				otp.setMode(EncryptionMode.Binary);	
				}
			}
		});
		ascii.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( ((JRadioButton)(e.getSource())).isOpaque()) {
					otp.setMode(EncryptionMode.ASCII);
				}
			}
		});
		
		
		
		//File selection
		inFileChoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( ((JButton)(e.getSource())).isOpaque()) {
					//show open dialog
					inFile.showOpenDialog(null);
					//get data from file
//					otp.getDataFromFile(inFile.getSelectedFile());
					//set in TextArea
//					if ( otp.getMode() == EncryptionMode.ASCII) {
//						in.setText(otp.getAsciiData());
//					} else {
//						in.setText(otp.bitsetToString(otp.getBinaryData()));
//					}
					//set Filename label
					inFileName.setText(inFile.getSelectedFile().getName());
				}
			}
		});
		
		outFileChoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( ((JButton)(e.getSource())).isOpaque()) {
					//show open dialog
					outFile.showSaveDialog(null);
					outFile.setVisible(true);
					outFilef = outFile.getSelectedFile();		
					outFileName.setText(outFile.getSelectedFile().getName());
				}
			}
		});
		
		
		
		doIt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton)e.getSource()).isOpaque()) {
					
					if ( !key.getText().isEmpty()) {
						if (inFile.getSelectedFile() != null) {
							otp.setMode(ascii.isSelected()?EncryptionMode.ASCII:EncryptionMode.Binary);
							otp.getDataFromFile(inFile.getSelectedFile());
							in.setText(otp.getMode()==
								EncryptionMode.ASCII?
									otp.getAsciiData():
									otp.bitsetToString(otp.getBinaryData()));
						} else {
//						if (otp.getAsciiData().isEmpty() && otp.getBinaryData().isEmpty()) {
							if (!in.getText().isEmpty())
								otp.getDataFromGUI(in.getText());
							else {
								JOptionPane.showMessageDialog(frame, "Nie podano danych wejściowych");
								return;
							}
						}								
//						else {
						//do it properly! :D
						try {
							if (enc.isSelected()) {
								otp.EncryptData(key.getText());
								//save result
								if (otp.getMode() == EncryptionMode.ASCII) {
									out.setText(otp.getAsciiEncoded());
									OutputStreamWriter o= new OutputStreamWriter(new FileOutputStream(outFilef));
									String s = out.getText(0,out.getText().length());
									o.write(s);
									o.close();
									File ff = new File (outFilef.getAbsoluteFile()+".key");
									ff.createNewFile();
									o = new OutputStreamWriter(new FileOutputStream(ff));
									o.write(key.getText());
									o.close();
								} else {
									out.setText(otp.bitsetToString(otp.getBinaryEncoded()));
									OutputStreamWriter o= new OutputStreamWriter(new FileOutputStream(outFilef));
									String s = otp.binaryToString(otp.getBinaryEncoded());
									s=s.substring(0,s.length());
									o.write(s);
									o.close();
									File ff = new File (outFilef.getAbsoluteFile()+".key");
									ff.createNewFile();
									o = new OutputStreamWriter(new FileOutputStream(ff));
									o.write(key.getText());
									o.close();
								}
							} else if (dec.isSelected()) {
								otp.DecryptData(key.getText());
								//save result
								if (otp.getMode() == EncryptionMode.ASCII) {
									out.setText(otp.getAsciiDecoded());
									OutputStreamWriter o= new OutputStreamWriter(new FileOutputStream(outFilef));
									String s = out.getText(0,out.getText().length());
									o.write(s);
									o.close();
								} else {
									out.setText(otp.bitsetToString(otp.getBinaryDecoded()));
									OutputStreamWriter o= new OutputStreamWriter(new FileOutputStream(outFilef));
									String s = otp.binaryToString(otp.getBinaryDecoded());
									s=s.substring(0,s.length());
									o.write(s);
									o.close();
								}
							}
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frame, "Nie podano sciezki pliku do zapisu");
						}
						
					}	else {
						JOptionPane.showMessageDialog(frame,"Nie podano klucza.\n");	
					}
					otp = null;
					otp= new OnetimePad();
					inFile = new JFileChooser();
					outFile = new JFileChooser();
					otp.setMode((ascii.isSelected()?EncryptionMode.ASCII:EncryptionMode.Binary));
				}

			}
		});
		
		
		frame.addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);			
			}
		});
	}
	
	
	public GUI() {
		initGUI();
		setActions();
	}
	
	
	
	public static void main(String[] args) {
		new GUI().frame.setVisible(true);

	}

}
