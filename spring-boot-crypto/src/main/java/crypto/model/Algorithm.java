package crypto.model;

/**
 * @author loquy
 * @date 2023/02/21 15:00
 */
public class Algorithm {

    public Algorithm(String pkAlgorithm, String key, String publicKey, String privateKey, String hashAlgorithm, String rawData) {
        this.pkAlgorithm = pkAlgorithm;
        this.key = key;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.hashAlgorithm = hashAlgorithm;
        this.rawData = rawData;
    }

    /**
     * 公开密钥算法
     */
    private String pkAlgorithm;

    /**
     * 对称加密密钥（DES、DES3、AES、SM4）
     */
    private String key;

    /**
     * 非对称加密：公钥（公钥加密）
     */
    private String publicKey;

    /**
     * 非对称加密：私钥（私钥解密）
     */
    private String privateKey;

    /**
     * 哈希算法
     */
    private String hashAlgorithm;

    /**
     * 原始数据
     */
    private String rawData;

    public String getPkAlgorithm() {
        return pkAlgorithm;
    }

    public void setPkAlgorithm(String pkAlgorithm) {
        this.pkAlgorithm = pkAlgorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
