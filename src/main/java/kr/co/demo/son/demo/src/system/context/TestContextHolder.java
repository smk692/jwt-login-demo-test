package kr.co.demo.son.demo.src.system.context;

public class TestContextHolder {
    private static final ThreadLocal<TestContext> contextHolder = new ThreadLocal<>();

    /**
     * @name : setContext
     * @param : context
     * @description : TestContext 셋팅한다.
     */
    public static void setContext(TestContext context) {
        contextHolder.set(context);
    }

    /**
     * @name : getContext
     * @return : TestContext
     * @description : TestContext 얻는다.
     */
    public static TestContext getContext() {
        TestContext context = contextHolder.get();
        if (context == null) {
            context = new TestContext();
            contextHolder.set(context);
        }
        return context;
    }

    /**
     * @name : clearContext
     * @description : context를 clear 한다.
     */
    public static void clearContext() {
        contextHolder.remove();
    }
}
