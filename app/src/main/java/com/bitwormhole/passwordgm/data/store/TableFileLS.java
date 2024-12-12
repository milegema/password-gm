package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretDataFile;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;

final class TableFileLS {


    private SecretKey key;
    private PropertyTable table;
    private Path file;

    public TableFileLS() {
    }


    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public PropertyTable getTable() {
        return table;
    }

    public void setTable(PropertyTable table) {
        this.table = table;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void load() throws IOException {

        // read from file
        SecretDataFile sd_file = new SecretDataFile();
        sd_file.setFile(this.file);
        sd_file.setKey(this.key);
        byte[] plain = sd_file.read();

        // decode PropertyTable
        this.table = PropertyTableLS.decode(plain);
    }

    public void save() throws IOException {

        // encode PropertyTable
        byte[] plain = PropertyTableLS.encode(this.table);

        byte[] tmp_iv = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 21, 22, 23, 24, 25, 26, 27, 28};


        // write to file
        SecretDataFile sd_file = new SecretDataFile();
        sd_file.setFile(this.file);
        sd_file.setKey(this.key);
        sd_file.setPadding(PaddingMode.PKCS7Padding); // auto
        sd_file.setMode(CipherMode.CFB); // auto
        sd_file.setIv(tmp_iv); // auto

        sd_file.write(plain);
    }
}
