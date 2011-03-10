import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;


public class OnetimePad {
	

	
	BitSet binaryData, binaryEncoded, binaryDecoded, binaryKey;
	String asciiData, asciiEncoded, asciiDecoded, asciiKey;
	EncryptionMode mode;
	final char [] ASCIItable = new char[256];
	
	public OnetimePad() {
		asciiData = new String();
		binaryData = new BitSet();
		for (int i = 0; i < ASCIItable.length; i++) {
			ASCIItable[i]=(char)i;
//			System.out.print(ASCIItable[i]);
		}

	}
	
	private BitSet encryptBin () {
		BitSet binaryEnc = new BitSet(binaryData.size());
		for ( int i=0; i< binaryData.size(); i++) {
			if (binaryData.get(i))
				binaryEnc.set(i);
		}
		binaryEnc.xor(binaryKey);
		return binaryEnc;
	}
	
	
	private String encryptASCII () {
		String asciiEnc = new String();
		for (int i=0; i<asciiData.length(); i++) {
			int asd = asciiData.charAt(i);
			int ask = asciiKey.charAt(i);
			asciiEnc=asciiEnc+(char)((asd+ask)%ASCIItable.length);
		}
		return asciiEnc;
	}
	
	private BitSet decryptBin() {
		BitSet binaryDec = new BitSet(binaryData.size());
		for ( int i=0; i< binaryData.size(); i++) {
			if (binaryData.get(i))
				binaryDec.set(i);
		}
		binaryDec.xor(binaryKey);
		return binaryDec;
	}
	
	
	private String decryptASCII() {
		String asciiDec=new String();
		for (int i=0; i<asciiData.length(); i++) {
			int asd = asciiData.charAt(i);
			int ask = asciiKey.charAt(i);
			
			int modsub = asd-ask;
			if ( modsub < 0)
				modsub=(modsub+ASCIItable.length)%ASCIItable.length;
			else
				modsub = modsub%ASCIItable.length;
			asciiDec=asciiDec+(char)(modsub);
		}
		return asciiDec;
	}
	
	
	void getDataFromFile(File f) {
		try {
			if (mode == EncryptionMode.ASCII) {
				BufferedReader in = new BufferedReader(new FileReader(f));
				String line = new String();
				while ( (line=in.readLine()) != null) {
					asciiData=asciiData+line+'\n';
				}
				in.close();
				System.out.print(asciiData);
			} else {
				FileInputStream in = new FileInputStream(f);
				int n = in.available();
				byte [] arr = new byte[n];
				in.read(arr);
				binaryData = new BitSet(arr.length*8);
				for (int i = 0; i < arr.length; i++) {
					for ( int j=0; j<8; j++) {
						if ((((arr[i] >> j) & 1) == 1)) {
							binaryData.set(i*8+j);
						}
					}
				}
				System.out.println(binaryData.size());
				in.close();
			}
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	void getDataFromGUI (String data) {
		if (mode == EncryptionMode.ASCII) {
			asciiData =""+data;
		} else {
			binaryData = new BitSet(data.length());
//			char [] arr = data.toCharArray();
			for (int i = 0; i < data.length(); i++) {
				if (data.charAt(i) == '1')
					binaryData.set(i);
//				byte b = (byte)arr[i];
//				for ( int j=0; j<9; j++) {
//					if ((((b >> j) & 1) == 1)) {
//						binaryData.set(i*8+j);
//					}
//				}
			}
		}
	}
	
	void EncryptData(String key) {
		if (mode == EncryptionMode.ASCII) {
			String keytmp = new String();
			keytmp=""+key;
			while ( keytmp.length() < asciiData.length()) {
				keytmp=keytmp+key;
			}
			asciiKey = keytmp;
			asciiEncoded = encryptASCII();
			
		} else {
			String keytmp = new String();
			keytmp=""+key;
			while (keytmp.length()*8 <= binaryData.length() ) {
				keytmp=keytmp+key;
			}
//			System.out.println("binary data"+binaryData.size());
//			System.out.println("keytmp"+keytmp.length());
			binaryKey = new BitSet(binaryData.length());
			for ( int i=0; i<binaryData.length()/8; i++) {
				byte b =(byte) keytmp.charAt(i);
				for ( int j=0; j<8; j++) {
					if ((((b >> j) & 1) == 1)) {
						binaryKey.set(i*8+j);
					}
				}
			}
			//System.out.println("BINARYKEY=="+bitsetToString(binaryKey));
			binaryEncoded = encryptBin();
//			System.out.println(binaryEncoded.size());
		}
		
	}
	
	void DecryptData(String key) {
		if (mode == EncryptionMode.ASCII) {
			String keytmp = new String();
			keytmp=""+key;
			while ( keytmp.length() < asciiData.length()) {
				keytmp=keytmp+key;
			}
			asciiKey = keytmp;
			asciiDecoded = decryptASCII();
		} else {
			String keytmp = new String();
			keytmp=""+key;
			while ( keytmp.length()*8 <= binaryData.length()) {
				keytmp=keytmp+key;
			}
//			System.out.println("binary data"+binaryData.size());
//			System.out.println("keytmp"+keytmp.length());
			binaryKey = new BitSet(binaryData.length());
			for ( int i=0; i<binaryData.length()/8; i++) {
				byte b =(byte) keytmp.charAt(i);
				for ( int j=0; j<8; j++) {
					if ((((b >> j) & 1) == 1)) {
						binaryKey.set(i*8+j);
					}
				}
			}
			binaryDecoded = decryptBin();
		}
	}
	public BitSet getBinaryData() {
		return binaryData;
	}
	public void setBinaryData(BitSet binaryData) {
		this.binaryData = binaryData;
	}
	public String getAsciiData() {
		return asciiData;
	}
	public void setAcsiiData(String acsiiData) {
		this.asciiData = acsiiData;
	}
	public EncryptionMode getMode() {
		return mode;
	}

	public BitSet getBinaryEncoded() {
		return binaryEncoded;
	}
	public BitSet getBinaryDecoded() {
		return binaryDecoded;
	}
	public String getAsciiEncoded() {
		return asciiEncoded;
	}
	public String getAsciiDecoded() {
		return asciiDecoded;
	}
	public void setMode(EncryptionMode binary) {
		this.mode = binary;
	}
	
	
	public String binaryToString( BitSet bs) {
		
		String result = new String();
		for ( int i=0; i<bs.size(); i+=8) {
			int c=0;
			int max =8;
			if (i+8 > bs.size()) max = bs.size()-i;
			for ( byte j=0; j<max; j++) {
				if (bs.get(i+j)) {
				c= c + (1<<j);
				}
			}
			result=result+(char)c;
		}
		return result;
	}
	public String bitsetToString(BitSet bs) {
		String result= new String("");
		
		for ( int i=0;i<bs.size(); i++ ) {
			result+=bs.get(i)?'1':'0';
		}
		
		return result;
	}
	
}
