package crypto.asymmetric;

import cn.hutool.crypto.SecureUtil;
import crypto.constant.AlgorithmConstant;
import crypto.Crypto;
import crypto.model.Key;
import crypto.model.Algorithm;

/**
 * @author loquy
 * @date 2023/02/07 10:08
 */
public class SM2 implements Crypto {

    private final Algorithm algorithm;

    public SM2(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String encrypt() throws Exception {
        return asymmetricEncrypt(algorithm.getRawData(), algorithm.getPublicKey(), AlgorithmConstant.SM2);
    }

    @Override
    public String decrypt() throws Exception {
        return asymmetricDecrypt(algorithm.getRawData(), algorithm.getPrivateKey(), AlgorithmConstant.SM2);
    }

    @Override
    public Key generateKey() {
        return new Key(SecureUtil.generateKeyPair(AlgorithmConstant.SM2));
    }
}
