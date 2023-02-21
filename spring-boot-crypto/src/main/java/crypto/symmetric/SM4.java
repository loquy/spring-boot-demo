package crypto.symmetric;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import crypto.constant.AlgorithmConstant;
import crypto.Crypto;
import crypto.model.Key;
import crypto.model.Algorithm;

/**
 * @author loquy
 * @date 2023/02/07 10:07
 */
public class SM4 implements Crypto {

    private final Algorithm algorithm;

    public SM4(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String encrypt() throws Exception {
        return symmetricEncrypt(algorithm.getRawData(), algorithm.getKey(), AlgorithmConstant.SM4);
    }

    @Override
    public String decrypt() throws Exception {
        return symmetricDecrypt(algorithm.getRawData(), algorithm.getKey(), AlgorithmConstant.SM4);
    }

    @Override
    public Key generateKey() {
        return new Key(new SymmetricCrypto(AlgorithmConstant.SM4).getSecretKey());
    }
}
