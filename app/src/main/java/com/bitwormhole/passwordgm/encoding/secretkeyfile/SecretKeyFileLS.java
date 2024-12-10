package com.bitwormhole.passwordgm.encoding.secretkeyfile;

import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.encoding.pem.PEMUtils;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.CipherUtils;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptFile;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptFileUtils;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.utils.FileOptions;
import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class SecretKeyFileLS {

    private SecretKeyFileLS() {
    }


    public static SecretKeyFile load(SecretKeyFile src) throws IOException {
        SecretKeyFile dst = new SecretKeyFile(src);
        Loader l = new Loader(dst);
        l.load();
        return dst;
    }

    public static void store(SecretKeyFile skf) throws IOException {
        Saver saver = new Saver(skf);
        saver.save();
    }

    private static class Loader {
        private final SecretKeyFile target;

        CryptFile crypt_file;
        PEMDocument doc;


        KeyPair key_pair;
        SecretKey secret_key;
        SecretKeyFile.Head head;
        PropertyTable properties;
        String text;
        Path file;

        public Loader(SecretKeyFile t) {
            this.target = t;
        }

        private void init() {
            this.key_pair = target.getKeypair();
            this.file = target.getFile();
        }

        private void readFile() throws IOException {
            text = FileUtils.readText(file);
        }

        private void decrypt() {

            PrivateKey pri = key_pair.getPrivate();
            Encryption en1 = new Encryption();

            en1.setProvider(null);
            en1.setAlgorithm(pri.getAlgorithm());
            en1.setPadding(head.outerPadding);
            en1.setMode(head.outerMode);
            en1.setIv(null);
            en1.setEncrypted(crypt_file.getBody());
            en1.setPlain(null);

            Encryption en2 = CipherUtils.decrypt(en1, pri);
            byte[] key = en2.getPlain();
            this.secret_key = new SecretKeySpec(key, head.innerAlgorithm);
        }

        private void decodePEMDocument() {
            doc = PEMUtils.decode(text);
        }

        private void decodeCryptFile() throws IOException {
            crypt_file = CryptFileUtils.convert(doc);
            properties = crypt_file.getHead();
            head = SecretKeyFile.props2head(properties);
        }

        public void load() throws IOException {
            this.init();
            this.readFile();
            this.decodePEMDocument();
            this.decodeCryptFile();
            this.decrypt();
            this.target.setSecretkey(this.secret_key);
        }
    }

    private static class Saver {

        private final SecretKeyFile target;

        CryptFile crypt_file;
        PEMDocument doc;


        KeyPair key_pair;
        SecretKey secret_key;
        SecretKeyFile.Head head;
        PropertyTable properties;
        String text;
        Path file;


        Saver(SecretKeyFile skf) {
            this.target = skf;
        }

        private void init() {
            key_pair = target.getKeypair();
            secret_key = target.getSecretkey();
            file = target.getFile();
            head = target.getHead();
            crypt_file = new CryptFile();
            doc = new PEMDocument();
        }

        private void encrypt() {

            PublicKey pub = key_pair.getPublic();
            Encryption en1 = new Encryption();

            en1.setProvider(null);
            en1.setAlgorithm(pub.getAlgorithm());
            en1.setPadding(head.outerPadding);
            en1.setMode(head.outerMode);
            en1.setIv(null);
            en1.setEncrypted(null);
            en1.setPlain(secret_key.getEncoded());

            Encryption en2 = CipherUtils.encrypt(en1, pub);
            crypt_file.setBody(en2.getEncrypted());
        }

        private void encodeCryptFile() throws IOException {
            properties = SecretKeyFile.head2props(head);
            crypt_file.setHead(properties);
            doc = CryptFileUtils.convert(crypt_file);
        }

        private void encodePEMDocument() {
            text = PEMUtils.encode(doc);
        }

        private void writeFile() throws IOException {
            FileOptions opt = new FileOptions();
            opt.mkdirs = true;
            opt.create = true;
            opt.write = true;
            opt.truncate = true;
            FileUtils.writeText(text, file, opt);
        }

        public void save() throws IOException {
            this.init();
            this.encrypt();
            this.encodeCryptFile();
            this.encodePEMDocument();
            this.writeFile();
        }
    }
}
