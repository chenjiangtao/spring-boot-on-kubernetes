package cn.topstream.app.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA生成公钥私钥工具
 */
public class RsaKeysUtil {
    public static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    /**
     * RSA是目前最有影响力的公钥加密算法，该算法基于一个十分简单的数论事实：将两个大素数相乘十分容易，但那时想要对其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥，即公钥，而两个大素数组合成私钥。公钥是可发布的供任何人使用，私钥则为自己所有，供解密之用。
     * <p>
     * 解密者拥有私钥，并且将由私钥计算生成的公钥发布给加密者。加密都使用公钥进行加密，并将密文发送到解密者，解密者用私钥解密将密文解码为明文。
     * <p>
     * 以甲要把信息发给乙为例，首先确定角色：甲为加密者，乙为解密者。首先由乙随机确定一个KEY，称之为密匙，将这个KEY始终保存在机器B中而不发出来；然后，由这个 KEY计算出另一个KEY，称之为公匙。这个公钥的特性是几乎不可能通过它自身计算出生成它的私钥。接下来通过网络把这个公钥传给甲，甲收到公钥后，利用公钥对信息加密，并把密文通过网络发送到乙，最后乙利用已知的私钥，就对密文进行解码了。以上就是RSA算法的工作流程。
     * <p>
     * 算法实现过程为：
     * <p>
     * 1. 随意选择两个大的质数p和q，p不等于q，计算N=pq。
     * 2. 根据欧拉函数，不大于N且与N互质的整数個数為(p-1)(q-1)。
     * 3. 选择一个整数e与(p-1)(q-1)互质，并且e小于(p-1)(q-1)。
     * 4. 用以下这个公式计算d：d× e ≡ 1 (mod (p-1)(q-1))。
     * 5. 将p和q的记录销毁。
     * <p>
     * 以上内容中，(N,e)是公钥，(N,d)是私钥。
     * <p>
     * <p>
     * RSA算法的应用。
     * <p>
     * 1. RSA的公钥和私钥是由KeyPairGenerator生成的，获取KeyPairGenerator的实例后还需要设置其密钥位数。
     * 设置密钥位数越高，加密过程越安全，一般使用1024位。     *
     * KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
     * keyPairGen.initialize(1024);
     * <p>
     * <p>
     * 2.公钥和私钥可以通过KeyPairGenerator执行generateKeyPair()后生成密钥对KeyPair，
     * 通过KeyPair.getPublic()和KeyPair.getPrivate()来获取。
     * 动态生成密钥对，这是当前最耗时的操作，一般要2s以上。
     * KeyPair keyPair = keyPairGen.generateKeyPair();
     * 公钥
     * PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
     * 私钥
     * PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
     * <p>
     * byte[] publicKeyData = publicKey.getEncoded();
     * byte[] privateKeyData = publicKey.getEncoded();
     * 公钥和私钥都有它们自己独特的比特编码，可以通过getEncoded()方法获取，返回类型为byte[]。通过byte[]可以再度将公钥或私钥还原出来。
     */
    //map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 2048个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

//    public static void main(String[] args) {
//        Map<String, Object> keyMap;
//        try {
//            keyMap = initKey();
//            String publicKey = getPublicKey(keyMap);
//            System.out.println("publicKey----"+publicKey);
//            String privateKey = getPrivateKey(keyMap);
//            System.out.println("privateKey---"+privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
