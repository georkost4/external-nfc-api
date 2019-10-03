package com.skjolberg.hce.tech.mifare;

import android.nfc.TransceiveResult;
import android.os.RemoteException;
import android.util.Log;

import com.acs.smartcard.ReaderException;
import com.skjolberg.hce.tech.CommandTechnology;
import com.skjolberg.nfc.command.ACRCommands;
import com.skjolberg.nfc.command.PassthroughCommandException;
import com.skjolberg.nfc.command.ReaderCommandException;
import com.skjolberg.service.IsoDepWrapper;

public class PN532DefaultTechnology extends DefaultTechnology implements CommandTechnology {

    protected static final String TAG = PN532DefaultTechnology.class.getName();

    protected IsoDepWrapper reader;
    private boolean print;

    public PN532DefaultTechnology(int tagTechnology, int slotNumber, IsoDepWrapper reader, boolean print) {
        super(tagTechnology, slotNumber);

        this.reader = reader;
        this.print = print;
    }

    @Override
    public TransceiveResult transceive(byte[] data, boolean raw) throws RemoteException {
        try {
            byte[] transceive;
            if (!raw) {
                if (print) {
                    Log.d(TAG, "Transceive request " + ACRCommands.toHexString(data));
                }
                transceive = reader.transceive(data);

                if (print) {
                    Log.d(TAG, "Transceive raw response " + ACRCommands.toHexString(transceive));
                }
            } else {
                if (print) {
                    Log.d(TAG, "Transceive raw request " + ACRCommands.toHexString(data));
                }

                transceive = reader.transmitPassThrough(data);

                if (print) {
                    Log.d(TAG, "Transceive raw response " + ACRCommands.toHexString(transceive));
                }
            }

            return new TransceiveResult(TransceiveResult.RESULT_SUCCESS, transceive);
        } catch (PassthroughCommandException e) {
            Log.d(TAG, "Problem sending command", e);

            return new TransceiveResult(TransceiveResult.RESULT_FAILURE, null);
        } catch (ReaderCommandException e) {
            Log.d(TAG, "Problem sending command", e);

            return new TransceiveResult(TransceiveResult.RESULT_FAILURE, null);
        } catch (ReaderException e) {
            Log.d(TAG, "Problem sending command", e);

            return new TransceiveResult(TransceiveResult.RESULT_FAILURE, null);
        }
    }
}


