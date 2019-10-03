package com.skjolberg.hce.tech.mifare;

import android.nfc.tech.NfcA;

import com.skjolberg.hce.tech.TagTechnology;
import com.skjolberg.service.IsoDepWrapper;

public class PN532NfcAAdapter extends PN532DefaultTechnology {

    protected static final String TAG = PN532NfcAAdapter.class.getName();

    public PN532NfcAAdapter(int slotNumber, IsoDepWrapper reader, boolean print) {
        super(TagTechnology.NFC_A, slotNumber, reader, print);
    }

    public String toString() {
        return NfcA.class.getSimpleName();
    }

}
