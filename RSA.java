
import java.math.BigInteger;

//import 
public class RSA {
//	edn需先提前设置好
//	e d n must initial before encryption or decryption
	public static BigInteger e, d, n;

//	传入要加密的字符串，仅限ascii码的字符。每个字符加密后变为一个字符串。所以最终返回一个字符串数组
//	pass the string needed encrypt, the string can only contain char of ASCII.
//	Each char become a string after encrypting. Therefore it returns a string array
	public static String[] Encryption(String origin) {
		int length = origin.length();
		String[] secret = new String[length];
		char[] mingwen = origin.toCharArray();
		for (int i = 0; i < length; i++) {
			BigInteger b = new BigInteger(Integer.toString(mingwen[i])).modPow(e, n);
			secret[i] = b.toString();
		}

		return secret;
	}

//	解密字符串数组，最后返回字符串
//	decrypt string array, then return original string
	public static String Decryption(String secret[]) {
		StringBuffer sb = new StringBuffer();
		for (String s : secret) {
			BigInteger b = new BigInteger(new BigInteger(s).modPow(d, n).toString());
			sb.append((char) b.intValue());
		}
		return sb.toString();
	}

//	卧槽~java自带的方法比这个还快，所以直接不用了。本来想用课本上的 Modular Exponentiation的
//	I realize Modular Exponentiation in textbook, but BigInteger provides a method .modPow(), which is faster than mine. So I abandon this
//	public static BigInteger ModularExponentiation(BigInteger base,BigInteger power,BigInteger mod) {
//		char power_bit[]=power.toString(2).toCharArray();
//		BigInteger result=new BigInteger("1");
//		int power_bit_length=power_bit.length;
//		BigInteger p=base.mod(mod);
//		for(int i=power_bit_length-1;i>=0;i--) {
//			if(power_bit[i]=='1') {
//				result=result.multiply(p).mod(mod);
//			}
//			p=p.multiply(p).mod(mod);
//		}		
//		return result;
//	}

}
