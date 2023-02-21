package crypto;

import cn.hutool.crypto.digest.Digester;

/**
 * The interface Digest.
 *
 * @author loquy
 * @date 2023 /02/09 9:11
 */
public interface Digest {

    /**
     * Digest hex string.
     *
     * @param data      the data
     * @param algorithm the algorithm
     * @return the string
     * @throws Exception the exception
     */
    default String digestHex(String data, String algorithm) throws Exception {
        Digester digester = new Digester(algorithm);
        return digester.digestHex(data);
    }

    /**
     * 生成文件摘要，并转为16进制字符串
     *
     * @return 摘要 string
     * @throws Exception the exception
     */
    String digestHex() throws Exception;
}
