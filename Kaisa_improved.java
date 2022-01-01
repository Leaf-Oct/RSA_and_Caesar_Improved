
import java.util.HashSet;

//我提出的改进版凯撒加密，虽然跟原版比大相径庭，但我的灵感确确实实是从原凯撒来的。
//I come up with this improved Caesar Encryption. Although it has much difference with original, I get afflatus from original Caesar definitely.
//网上好像没找到相同的，所以应该是我原创吧~~如果已经有人提出了回来踢我
//It seems that there isn't same method in internet. If there is somebody has come up with this idea before me, return here and kick/hit/beat/knock me	<( ‵□′)───C＜─___-)||
//只对26个字母有效
//only have effect on 26 letters

//Kaisa is Chinese pronunciation. Caesar is right spelling.
public class Kaisa_improved {
	private static String password;
	private static char password_table[][];

	public static String decrypt(String secret) {
		StringBuffer sb = new StringBuffer();
		char secret_array[] = secret.toCharArray();
		for (char c : secret_array) {
			if (c >= 'a' && c <= 'z') {
				int index = 0;
				for (; index < 26; index++) {
					if (password_table[0][index] == c) {
						char append_char = (char) ('a' + index);
						sb.append(append_char);
						break;
					}
				}
			} else if (c >= 'A' && c <= 'Z') {
				int index = 0;
				for (; index < 26; index++) {
					if (password_table[1][index] == c) {
						char append_char = (char) ('A' + index);
						sb.append(append_char);
						break;
					}
				}
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static boolean setSecretKey(String s) {
//		判断密钥是否符合规范。小于26位，且无重复字母
//		judge key is legal or not. Its length should less than 26, and no repeating letter
		if (s.length() >= 26) {
			System.err.println("太长了吧？！");
			return false;
		}
		HashSet<Character> judge = new HashSet<>();
		for (char c : s.toCharArray()) {
			boolean result = judge.add(c);
			if (!result) {
				System.err.println("有重复字符");
				return false;
			}
		}
		password = s;
		System.out.println("凯撒密钥设置为" + password);
		System.out.println("开始初始化小写密码表");
		password_table = new char[2][26];
		s = s.toLowerCase();
		judge.clear();
		char lower_upper_password[] = s.toCharArray();
		for (int i = 0; i < lower_upper_password.length; i++) {
			password_table[0][i] = lower_upper_password[i];
			judge.add(lower_upper_password[i]);
		}
		char c = 'z';
		for (int i = lower_upper_password.length; i < 26; i++, c--) {
			while (judge.contains(c)) {
				c--;
			}
			password_table[0][i] = c;
		}
		System.out.println("小写密码表初始化完成");
		System.out.println("开始初始化大写密码表");
		s = s.toUpperCase();
		judge.clear();
		lower_upper_password = s.toCharArray();
		for (int i = 0; i < lower_upper_password.length; i++) {
			password_table[1][i] = lower_upper_password[i];
			judge.add(lower_upper_password[i]);
		}
		c = 'Z';
		for (int i = lower_upper_password.length; i < 26; i++, c--) {
			while (judge.contains(c)) {
				c--;
			}
			password_table[1][i] = c;
		}
		System.out.println("大写密码表设置完成");
		System.out.println("设置成功");
		return true;
	}

	public static String encrypt(String original) {
		StringBuffer sb = new StringBuffer();
		char original_array[] = original.toCharArray();
		for (char c : original_array) {
			if (c >= 'a' && c <= 'z') {
				sb.append(password_table[0][c - 'a']);
				continue;
			}
			if (c >= 'A' && c <= 'Z') {
				sb.append(password_table[1][c - 'A']);
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
