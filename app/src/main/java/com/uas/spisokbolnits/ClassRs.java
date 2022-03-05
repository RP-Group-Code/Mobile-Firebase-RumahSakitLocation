package com.uas.spisokbolnits;

public class ClassRs {
    String key;

    String NamaRs;
    String AlamatRs;
    String NoRs;
    String WaktuRs;

    public ClassRs(){

    }

    public ClassRs(String namaRs, String alamatRs, String NomorRs, String waktuRs) {
        NamaRs = namaRs;
        AlamatRs = alamatRs;
        NoRs = NomorRs;
        WaktuRs = waktuRs;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamaRs() {
        return NamaRs;
    }

    public void setNamaRs(String namaRs) {
        NamaRs = namaRs;
    }

    public String getAlamatRs() {
        return AlamatRs;
    }

    public void setAlamatRs(String alamatRs) {
        AlamatRs = alamatRs;
    }

    public String getNoRs() {
        return NoRs;
    }

    public void setNoRs(String noRs) {
        NoRs = noRs;
    }

    public String getWaktuRs() {
        return WaktuRs;
    }

    public void setWaktuRs(String waktuRs) {
        WaktuRs = waktuRs;
    }
}
