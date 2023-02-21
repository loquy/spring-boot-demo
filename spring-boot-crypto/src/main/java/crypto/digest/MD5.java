package crypto.digest;

import crypto.constant.AlgorithmConstant;
import crypto.Digest;
import crypto.model.Algorithm;

/**
 * @author loquy
 * @date 2023/02/09 9:12
 */
public class MD5 implements Digest {

    private final Algorithm algorithm;

    public MD5(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String digestHex() throws Exception {
        return digestHex(algorithm.getRawData(), AlgorithmConstant.MD5);
    }
}
