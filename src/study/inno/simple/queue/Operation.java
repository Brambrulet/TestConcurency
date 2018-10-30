package study.inno.simple.queue;

public enum Operation {
    op_1(200), op_2(300), op_3(350), op_4(250), op_5(450);

    private int opLen;

    Operation(int opLen) {
        this.opLen = opLen;
    }

    public int getOpLen() {
        return opLen;
    }
}
