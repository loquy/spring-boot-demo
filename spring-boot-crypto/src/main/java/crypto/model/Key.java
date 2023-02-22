package crypto.model;

import cn.hutool.core.codec.Base64;

import javax.crypto.SecretKey;
import java.security.KeyPair;

/**
 * The type Key.
 *
 * @author loquy
 * @date 2023 /02/07 13:51
 */
public class Key {

    /**
     * 对称加密密钥
     */
    private SecretKey secretKey;
    /**
     * 非对称加密的公钥和私钥
     */
    private KeyPair keyPair;
    /**
     * base64密钥
     */
    private String base64SecretKey;
    /**
     * base64私钥
     */
    private String base64PrivateKey;
    /**
     * base64公钥
     */
    private String base64PublicKey;

    /**
     * Instantiates a new Key.
     *
     * @param secretKey the secret key
     */
    public Key(SecretKey secretKey) {
        this.secretKey = secretKey;
        setBase64SecretKey(secretKey);
    }

    /**
     * Instantiates a new Key.
     *
     * @param keyPair the key pair
     */
    public Key(KeyPair keyPair) {
        this.keyPair = keyPair;
        setBase64PrivateKey(keyPair);
        setBase64PublicKey(keyPair);
    }

    /**
     * Gets secret key.
     *
     * @return the secret key
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /**
     * Sets secret key.
     *
     * @param secretKey the secret key
     */
    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Gets key pair.
     *
     * @return the key pair
     */
    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * Sets key pair.
     *
     * @param keyPair the key pair
     */
    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * Gets base 64 secret key.
     *
     * @return the base 64 secret key
     */
    public String getBase64SecretKey() {
        return base64SecretKey;
    }

    /**
     * Sets base 64 secret key.
     */
    public void setBase64SecretKey(SecretKey secretKey) {
        this.base64SecretKey = Base64.encodeWithoutPadding(secretKey.getEncoded());
    }

    /**
     * Gets base 64 private key.
     *
     * @return the base 64 private key
     */
    public String getBase64PrivateKey() {
        return base64PrivateKey;
    }

    /**
     * Sets base 64 private key.
     */
    public void setBase64PrivateKey(KeyPair keyPair) {
        this.base64PrivateKey = Base64.encodeWithoutPadding(keyPair.getPrivate().getEncoded());
    }

    /**
     * Gets base 64 public key.
     *
     * @return the base 64 public key
     */
    public String getBase64PublicKey() {
        return base64PublicKey;
    }

    /**
     * Sets base 64 public key.
     */
    public void setBase64PublicKey(KeyPair keyPair) {
        this.base64PublicKey = Base64.encodeWithoutPadding(keyPair.getPublic().getEncoded());
    }
}
