package crypto.digest;

import crypto.Digest;
import crypto.constant.AlgorithmConstant;
import crypto.model.Algorithm;

/**
 * @author loquy
 * @date 2023/02/09 9:13
 */
public class SHA1 implements Digest {

    private final Algorithm algorithm;

    public SHA1(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String digestHex() throws Exception {
        return digestHex(algorithm.getRawData(), AlgorithmConstant.SHA1);
    }
}
