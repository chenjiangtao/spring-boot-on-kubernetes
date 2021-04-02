package cn.topstream.app.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Objects;

/**
 * AES 256 加密解密工具类
 *
 * @author RenYuLiang
 */
@Slf4j
public final class Aes256CryptUtils {

    private static final String KEY_ALGORITHM = "AES";


    /**
     * 加密
     *
     * @param content  yte[]
     * @param password String
     * @param iv       String
     * @return String
     */
    public static String encrypt(final byte[] content, final String password, final String iv) {
        return Base64.toBase64String(Objects.requireNonNull(encryptForByte(content, password, iv)));
    }

    /**
     * 加密
     *
     * @param content  yte[]
     * @param password String
     * @param iv       String
     * @return String
     */
    public static byte[] encryptForByte(final byte[] content, final String password, final String iv) {
        if (log.isDebugEnabled()) {
            log.debug("明文:{}", content);
        }
        if (log.isDebugEnabled()) {
            log.debug("初始化key:{}", password);
        }
        if (log.isDebugEnabled()) {
            log.debug("初始化向量字符串iv:{}", iv);
        }
        try {
            //"AES"：请求的密钥算法的标准名称
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //256：密钥生成参数；securerandom：密钥生成器的随机源
            //SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            //  kgen.init(256, securerandom);
            //生成秘密（对称）密钥
            //SecretKey secretKey = kgen.generateKey();
            //返回基本编码格式的密钥
            byte[] enCodeFormat = toHash256Deal(password);
            //根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //将提供程序添加到下一个可用位置
            Security.addProvider(new BouncyCastleProvider());
            //创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            //"AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, getIv(iv));
            byte[] cryptography = cipher.doFinal(content);
            if (log.isDebugEnabled()) {
                log.debug("加密后密文[Base64编码]:{}", Base64.toBase64String(cryptography));
            }
            return cryptography;
        } catch (Exception e) {
            log.error("encryptForByte error",e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param cryptography byte[]
     * @param password     String
     * @param iv           String
     * @return String
     */
    public static String decrypt(final byte[] cryptography, final String password, final String iv) {
        return new String(Objects.requireNonNull(decryptForByte(cryptography, password, iv)), StandardCharsets.UTF_8);
    }

    /**
     * 解密
     *
     * @param cryptography byte[]
     * @param password     String
     * @param iv           String
     * @return String
     */
    public static byte[] decryptForByte(final byte[] cryptography, final String password, final String iv) {
        if (log.isDebugEnabled()) {
            log.debug("密文[Base64编码]:{}", Base64.toBase64String(cryptography));
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            //  kgen.init(256, securerandom);
            // SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = toHash256Deal(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, getIv(iv));
            return cipher.doFinal(cryptography);
        } catch (Exception e) {
            log.error("decryptForByte error",e);
        }
        return null;
    }

    /**
     * 获取 IV
     *
     * @param iv String
     * @return AlgorithmParameterSpec
     */
    private static AlgorithmParameterSpec getIv(String iv) {
        byte[] ivBytes = iv.getBytes();
        if (log.isDebugEnabled()) {
            log.debug("填充前iv字节[Base64编码]:{}", Base64.toBase64String(ivBytes));
        }
        byte[] ivTemp = null;
        int ivBase = 16;
        int ivBaseLength = 16;
        if (ivBytes.length < ivBaseLength) {
            int base = 16;
            if (ivBytes.length % ivBase != 0) {
                int groups = ivBytes.length / base + 1;
                ivTemp = new byte[groups * base];
                Arrays.fill(ivTemp, (byte) 0);
                System.arraycopy(ivBytes, 0, ivTemp, 0, ivBytes.length);
            }
        } else {
            ivTemp = new byte[ivBase];
            System.arraycopy(ivBytes, 0, ivTemp, 0, ivBase);
        }
        if (log.isDebugEnabled()) {
            assert ivTemp != null;
            log.debug("填充后iv字节[Base64编码]:{}", Base64.toBase64String(ivTemp));
        }
        IvParameterSpec ivParameterSpec;
        assert ivTemp != null;
        ivParameterSpec = new IvParameterSpec(ivTemp);
        System.out.println(Base64.toBase64String(ivParameterSpec.getIV()));
        return ivParameterSpec;
    }

    /**
     * 加密
     *
     * @param content  String
     * @param password String
     * @param iv       String
     * @return String
     */
    private static String encrypt(String content, String password, String iv) {
        if (log.isDebugEnabled()) {
            log.debug("明文:{}", content);
        }
        if (log.isDebugEnabled()) {
            log.debug("初始化key:{}", password);
        }
        if (log.isDebugEnabled()) {
            log.debug("初始化向量字符串iv:{}", iv);
        }
        try {
            //"AES"：请求的密钥算法的标准名称
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
            //256：密钥生成参数；securerandom：密钥生成器的随机源
            //SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            //  kgen.init(256, securerandom);
            //生成秘密（对称）密钥
            //SecretKey secretKey = kgen.generateKey();
            //返回基本编码格式的密钥
            byte[] enCodeFormat = toHash256Deal(password);
            //根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //将提供程序添加到下一个可用位置
            Security.addProvider(new BouncyCastleProvider());
            //创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            //"AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, getIv(iv));
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            byte[] cryptography = cipher.doFinal(byteContent);
            if (log.isDebugEnabled()) {
                log.debug("加密后密文[Base64编码]:{}", Base64.toBase64String(cryptography));
            }
            return Base64.toBase64String(cryptography);
        } catch (Exception e) {
            log.error("encrypt error",e);
        }
        return null;
    }

    /**
     * 转为 hash 256
     *
     * @param dataStr String
     * @return byte[]
     */
    private static byte[] toHash256Deal(final String dataStr) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(dataStr.getBytes());
            byte[] hex = digester.digest();
            if (log.isDebugEnabled()) {
                log.debug("生成摘要信息位[Base64编码]:{}", Base64.toBase64String(hex));
            }
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    /**
//     * 用于解密卡密券密接口的返回
//     *
//     * @param args String[]
//     */
//    public static void main(String[] args) {
//        final String decrypt = Aes256CryptUtils.decrypt(Base64.decode("vxf085a6u/UG/+f17LWwHGp0" +
//                "+5eiOELz5phA3FvTO5iAOEM6jDS3yuT62HjG/Iq+"), "yXfguySz8PtDJZyfY0rxCkZk1xV2Qky8", "freemud");
//        System.out.println(decrypt);
//
//    }

}
