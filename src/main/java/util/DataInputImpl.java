package util;

import util.DataInput;

import java.util.Scanner;

public class DataInputImpl implements DataInput {
    Scanner in = new Scanner(System.in);
    public String inputData() {
    return in.nextLine();
    }
}
