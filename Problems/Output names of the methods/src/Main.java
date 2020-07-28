class CreateInstance {

    public static SuperClass create() {

        SuperClass instance = new SuperClass() {
            @Override
            public void method3() {
                System.out.println("method3");
            }

            @Override
            public void method2() {
                System.out.println("method2");
            }
        };/* create an instance of an anonymous class here,
                                 do not forget ; on the end */

        return instance;
    }
}

abstract class SuperClass {

    public static void method1() {
        System.out.println("method1");
    }

    public void method2() {
        System.out.println("method2");
    }

    public abstract void method3();
}