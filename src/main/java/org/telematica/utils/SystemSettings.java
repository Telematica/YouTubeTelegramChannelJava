package org.telematica.utils;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class SystemSettings {
    public static void initSettings() {
        try {
            // System.out.println encoding issue addressed here
            System.setOut(
                    new PrintStream(
                            new FileOutputStream(FileDescriptor.out),
                            true,
                            "UTF-8"
                    )
            );
        } catch (UnsupportedEncodingException e) {
            throw new InternalError("VM does not support mandatory encoding UTF-8");
        }
    }
}
