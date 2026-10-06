package org.lineageos.settings.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtils {
    private static final String TAG = "FileUtils";
    private FileUtils() {}
    public static String readOneLine(String fileName) {
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName), 512);
            line = reader.readLine();
        } catch (FileNotFoundException e) {
            Log.w(TAG, "No such file " + fileName + " for reading", e);
        } catch (IOException e) {
            Log.e(TAG, "Could not read from file " + fileName, e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ignored) {}
        }
        return line;
    }
    public static boolean writeLine(String fileName, String value) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(value);
        } catch (FileNotFoundException e) {
            Log.w(TAG, "No such file " + fileName + " for writing", e);
            return false;
        } catch (IOException e) {
            Log.e(TAG, "Could not write to file " + fileName, e);
            return false;
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException ignored) {}
        }
        return true;
    }
    public static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }
    public static boolean isFileReadable(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.canRead();
    }
    public static boolean isFileWritable(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.canWrite();
    }
    public static boolean delete(String fileName) {
        try {
            return new File(fileName).delete();
        } catch (SecurityException e) {
            Log.w(TAG, "SecurityException trying to delete " + fileName, e);
            return false;
        }
    }
    public static boolean rename(String srcPath, String dstPath) {
        try {
            return new File(srcPath).renameTo(new File(dstPath));
        } catch (SecurityException | NullPointerException e) {
            Log.w(TAG, "Failed to rename " + srcPath + " to " + dstPath, e);
            return false;
        }
    }
    public static boolean getFileValueAsBoolean(String filename, boolean defValue) {
        String value = readOneLine(filename);
        return value != null ? !value.equals("0") : defValue;
    }
    public static String getFileValue(String filename, String defValue) {
        String value = readOneLine(filename);
        return value != null ? value : defValue;
    }
}
