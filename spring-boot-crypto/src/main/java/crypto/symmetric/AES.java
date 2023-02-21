package crypto.symmetric;

import cn.hutool.crypto.SecureUtil;
import crypto.constant.AlgorithmConstant;
import crypto.Crypto;
import crypto.model.Key;
import crypto.model.Algorithm;

/**
 * @author loquy
 * @date 2023/02/07 10:06
 */
public class AES implements Crypto {

    private final Algorithm algorithm;

    public AES(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String encrypt() throws Exception {
        return symmetricEncrypt(algorithm.getRawData(), algorithm.getKey(), AlgorithmConstant.AES);
    }

    @Override
    public String decrypt() throws Exception {
        return symmetricDecrypt(algorithm.getRawData(), algorithm.getKey(), AlgorithmConstant.AES);
    }

    @Override
    public Key generateKey() {
        return new Key(SecureUtil.generateKey(AlgorithmConstant.AES));
    }

}
