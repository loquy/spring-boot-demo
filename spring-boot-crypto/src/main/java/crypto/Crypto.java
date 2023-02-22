package crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import crypto.model.Key;
import org.apache.commons.lang3.StringUtils;

/**
 * The interface Crypto.
 *
 * @author loquy
 * @date 2023 /02/07 11:23
 */
public interface Crypto {

    /**
     * 对称加密算法加密.
     *
     * @param data      the data
     * @param secretKey the secret key
     * @param algorithm the algorithm
     * @return the string
     * @throws Exception the exception
     */
    default String symmetricEncrypt(String data, String secretKey, String algorithm) throws Exception {
        isBase64(secretKey, "密钥");
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(algorithm, Base64.decode(secretKey));
        return symmetricCrypto.encryptHex(data);
    }

    /**
     * 对称加密算法解密.
     *
     * @param data      the data
     * @param secretKey the secret key
     * @param algorithm the algorithm
     * @return the string
     * @throws Exception the exception
     */
    default String symmetricDecrypt(String data, String secretKey, String algorithm) throws Exception {
        isBase64(secretKey, "密钥");
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(algorithm, Base64.decode(secretKey));
        return symmetricCrypto.decryptStr(data, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 不对称加密算法加密.
     *
     * @param data      the data
     * @param publicKey the public key
     * @param algorithm the algorithm
     * @return the string
     * @throws Exception the exception
     */
    default String asymmetricEncrypt(String data, String publicKey, String algorithm) throws Exception {
        isBase64(publicKey, "公钥");
        AsymmetricCrypto asymmetricCrypto = new AsymmetricCrypto(algorithm, null, publicKey);
        return asymmetricCrypto.encryptHex(data, KeyType.PublicKey);
    }

    /**
     * 不对称加密算法解密.
     *
     * @param data       the data
     * @param privateKey the private key
     * @param algorithm  the algorithm
     * @return the string
     * @throws Exception the exception
     */
    default String asymmetricDecrypt(String data, String privateKey, String algorithm) throws Exception {
        isBase64(privateKey, "私钥");
        AsymmetricCrypto asymmetricCrypto = new AsymmetricCrypto(algorithm, privateKey, null);
        return asymmetricCrypto.decryptStr(data, KeyType.PrivateKey);
    }

    /**
     * Is base 64.
     *
     * @param base64 the base 64
     * @param column the column
     * @throws Exception the exception
     */
    default void isBase64(String base64, String column) throws Exception {
        if (StringUtils.isEmpty(base64)) {
            throw new Exception(column + "不能为空");
        }
        byte[] decode = Base64.decode(base64);
        String encode = Base64.encodeWithoutPadding(decode);
        if (!Base64.isBase64(base64) || !base64.equals(encode)) {
            throw new Exception(column + "格式不正确");
        }
    }

    /**
     * 加密
     *
     * @return the string
     * @throws Exception the exception
     */
    String encrypt() throws Exception;

    /**
     * 解密
     *
     * @return the string
     * @throws Exception the exception
     */
    String decrypt() throws Exception;

    /**
     * 针对对称加密生成密钥，针对非对称加密生成一对公钥和私钥
     *
     * @return the key
     */
    Key generateKey();
}
