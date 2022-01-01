

import java.math.BigInteger;
import java.util.Random;

public class GenerateRSAKey {
	public static BigInteger p, q, n, ora, e, d;

	public static BigInteger getN() {
		Random r = new Random();
//		手动定义初始p,q为500位。不出意外的话，n就是999位。即RSA密钥长度999位
//		Initially define p and q as 500 bit. Therefore, n should be 999bit, meaning that length of RSA key is 999 bit
//		根据概率论，probablePrime返回的数可以认为一定是质数
//		Accroding to Probability Theorm, we can confirm that probablePrime must return a prime
		p = BigInteger.probablePrime(500, r);
		q = BigInteger.probablePrime(500, r);
		n = p.multiply(q);
		return n;
	}
//	Ora是个人按日语发音的拼法，并不准确，正确应写作Euler
//	Ora is pronunciation in Japanese by myself, which is not correct. Right spelling is Euler
	public static BigInteger getOra() {
//		根据欧拉公式，当n是两个质数pq的积时，Euler(n)=(p-1)(q-1)
//		Accroding to Euler Formula, when n is product of two prime p and q, Euler(n)=(p-1)(q-1)
		BigInteger one = new BigInteger("1");
//		get p-1		and 	q-1
		BigInteger p_minus_one = p.subtract(one), q_minus_one = q.subtract(one);
//		(p-1)(q-1)
		ora = p_minus_one.multiply(q_minus_one);
		one = null;
		p_minus_one = null;
		q_minus_one = null;
		System.gc();
		return ora;
	}
//	随机生成一个900位的加密密钥e，看是否与欧拉互质。即gcd(e,eular)=1。不满足就继续生成
//	generate a encryption key e randomly. Then check whether gcd(e,euler)=1. if not, generate a new e;
	public static BigInteger getE() {
		Random r = new Random();
		do {
			e = new BigInteger(900, r);
			System.out.println("找一次");
		} while (!ora.gcd(e).toString().equals("1"));
		return e;
	}
//	计算解密密钥d，使用扩展欧几里得算法
//	calculate decryption key d, using Extended Euclidean algorithm
//	代码思路来自 https://blog.csdn.net/a1219532602/article/details/89811733	非常感谢
//	code from https://blog.csdn.net/a1219532602/article/details/89811733	Thanks very much
	public static BigInteger[] ex_gcd(BigInteger a, BigInteger b, BigInteger[] x, BigInteger[] y) {

		BigInteger[] result = new BigInteger[3];

		if (b.compareTo(new BigInteger("0")) == 0) {
			result[0] = a;
			result[1] = x[0];
			result[2] = y[0];
			return result;
		}
		BigInteger q = a.divide(b);
		BigInteger tx1 = x[0].subtract(q.multiply(x[1]));
		BigInteger ty1 = y[0].subtract(q.multiply(y[1]));
		BigInteger[] tx = { x[1], tx1 };
		BigInteger[] ty = { y[1], ty1 };
		return ex_gcd(b, a.remainder(b), tx, ty);
	}
//	ed≡1 mod φ	--------->	ed + kφ = 1		e,φ are known, while d, k are unknown. But d,k are integer!
//	This eqution can be written as ax+by=1
	public static BigInteger ex_gcd(BigInteger a, BigInteger b) {
		BigInteger[] x = { new BigInteger("1"), new BigInteger("0") };
		BigInteger[] y = { new BigInteger("0"), new BigInteger("1") };
		BigInteger result[] = ex_gcd(a, b, x, y);
		d = result[1];
		return d;
//		result三个元素分别是gcd,x和y
//		3 elements of result are gcd, x and y
//		ax+by=gcd
//		也就是说a=e,b=φ，得到的x就是e的模反
//		means a=e,b=φ, x is d
	}
//	运行下面这个生成密钥
//	run main method to generate key
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		long t=System.currentTimeMillis();
//		System.out.println("随机生成质数pq，并求得n");
//		BigInteger N = getN();
//		System.out.println("p=" + p.toString());
//		System.out.println("q=" + q.toString());
//		System.out.println("n=" + N);
//		System.out.println("根据n求得欧拉函数的值,这个很重要");
//		BigInteger O = getOra();
//		System.out.println("Ora=" + O.toString());
//		System.out.println("随便找一个e满足条件，这个是加密用的密钥");
//		BigInteger E = getE();
//		System.out.println("e="+E.toString());
//		System.out.println("通过e和欧拉找解密用的密钥d,使用拓展欧几里得算法");
//		BigInteger D=ex_gcd(E, O);
//		System.out.println("d="+D.toString());
//		System.out.println("生成密钥完毕,记得保存");
//		System.out.println("总共用时"+(System.currentTimeMillis()-t)+"毫秒");
//	}

}
