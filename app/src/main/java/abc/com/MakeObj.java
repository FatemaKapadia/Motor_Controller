package abc.com;

public class MakeObj {

    public static String msg="01";

    private int computeCode()
    {
        int x, y, code;

        x = msg.charAt(0) - '0';
        y = msg.charAt(1) - '0';
        code = 10*x + y;

        return code;
    }

    public Result makeObj( )
    {
        int code = computeCode();
        Result obj = new Result();
        obj.setCurr(code);
        obj.setPower(code);

        return obj;
    }

}
