package crypto;

import crypto.constant.AlgorithmConstant;
import crypto.asymmetric.*;
import crypto.symmetric.*;
import crypto.model.Algorithm;

/**
 * 加密算法工厂类
 *
 * @author loquy
 * @date 2023/02/07 10:03
 */
public class CryptoFactory {

    public static Crypto getCryptoFactory(Algorithm algorithm) throws Exception {
        switch (algorithm.getPkAlgorithm()) {
            case AlgorithmConstant.DES :
                return new DES(algorithm);
            case AlgorithmConstant.DES3 :
                return new DESede(algorithm);
            case AlgorithmConstant.AES :
                return new AES(algorithm);
            case AlgorithmConstant.SM4 :
                return new SM4(algorithm);
            case AlgorithmConstant.RSA :
                return new RSA(algorithm);
            case AlgorithmConstant.SM2 :
                return new SM2(algorithm);
            default:
                throw new Exception("没有找到此算法：" + algorithm.getPkAlgorithm());
        }
    }
}
