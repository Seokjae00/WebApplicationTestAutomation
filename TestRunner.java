import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JunitTestSuite.class);

        if (result.wasSuccessful())
            System.out.println("테스트에 성공했습니다.");
        else {
            System.out.println("테스트에 실패한 항목이 있습니다.\n");

            System.out.println("실패 목록");
            for (Failure failure : result.getFailures())
                System.out.println(failure.toString());
        }
    }
}
