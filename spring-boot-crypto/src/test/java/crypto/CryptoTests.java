package crypto;

import crypto.constant.AlgorithmConstant;
import crypto.model.Algorithm;
import crypto.model.Key;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author loquy
 * @date 2023/02/21 15:14
 */
@SpringBootTest
public class CryptoTests {

    @Test
    void asymmetricTest() throws Exception {
        cryptoTest(AlgorithmConstant.RSA, "RSA");
        cryptoTest(AlgorithmConstant.SM2, "SM2");
    }

    @Test
    void symmetricTest() throws Exception {
        cryptoTest(AlgorithmConstant.AES, "AES");
        cryptoTest(AlgorithmConstant.DES, "DES");
        cryptoTest(AlgorithmConstant.DES3, "DESede");
        cryptoTest(AlgorithmConstant.SM4, "SM4");
    }

    @Test
    void digestTest() throws Exception {
        digestTest(AlgorithmConstant.MD5, "MD5");
        digestTest(AlgorithmConstant.SHA1, "SHA1");
        digestTest(AlgorithmConstant.SHA256, "SHA256");
        digestTest(AlgorithmConstant.SM3, "SM3");
    }

    private void cryptoTest(String algorithmName, String data) throws Exception {
        Algorithm algorithm = new Algorithm(algorithmName, null, null, null, null, data);
        Key key = CryptoFactory.getCryptoFactory(algorithm).generateKey();
        algorithm.setKey(key.getBase64SecretKey());
        algorithm.setPrivateKey(key.getBase64PrivateKey());
        algorithm.setPublicKey(key.getBase64PublicKey());
        String encrypt = CryptoFactory.getCryptoFactory(algorithm).encrypt();
        algorithm.setRawData(encrypt);
        String decrypt = CryptoFactory.getCryptoFactory(algorithm).decrypt();
        System.out.println("=====================");
        System.out.println(encrypt);
        System.out.println(decrypt);
    }

    private void digestTest(String algorithmName, String data) throws Exception {
        Algorithm algorithm = new Algorithm(null, null, null, null, algorithmName, data);
        String encrypt = DigestFactory.getDigestFactory(algorithm).digestHex();
        System.out.println("=====================");
        System.out.println(encrypt);
    }
}
