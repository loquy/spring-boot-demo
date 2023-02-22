package crypto;

import crypto.asymmetric.RSA;
import crypto.asymmetric.SM2;
import crypto.constant.AlgorithmConstant;
import crypto.model.Algorithm;
import crypto.symmetric.AES;
import crypto.symmetric.DES;
import crypto.symmetric.DESede;
import crypto.symmetric.SM4;

/**
 * 加密算法工厂类
 *
 * @author loquy
 * @date 2023/02/07 10:03
 */
public class CryptoFactory {

    public static Crypto getCryptoFactory(Algorithm algorithm) throws Exception {
        switch (algorithm.getPkAlgorithm()) {
            case AlgorithmConstant.DES:
                return new DES(algorithm);
            case AlgorithmConstant.DES3:
                return new DESede(algorithm);
            case AlgorithmConstant.AES:
                return new AES(algorithm);
            case AlgorithmConstant.SM4:
                return new SM4(algorithm);
            case AlgorithmConstant.RSA:
                return new RSA(algorithm);
            case AlgorithmConstant.SM2:
                return new SM2(algorithm);
            default:
                throw new Exception("没有找到此算法：" + algorithm.getPkAlgorithm());
        }
    }
}
