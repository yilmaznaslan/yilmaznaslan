package reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParameterPassingMechanism {

    void modifyObjectV2(Dog obj) {
        obj = new Dog(20);
    }

    @Test
    void ASSERT_THAT___() {
        Dog myObj = new Dog(10);

        modifyObjectV2(myObj);

        Assertions.assertEquals(20, myObj.getAge());
    }

    @Test
    void ASSERT_THAT__REferen() {
        Dog dog1 = new Dog(10);
        Dog dog2 = dog1; // This means both dog2 and dog1 now refer to the same object in memory

        dog2.setAge(20);
        Assertions.assertEquals(20, dog1.getAge());

        dog1.setAge(30);
        Assertions.assertEquals(30, dog2.getAge());
    }

    void modifyObject(final Dog obj) {
        obj.setAge(obj.getAge() * 2);
    }

    void modifyObject(int value) {
        value = value * 2;
    }

    @Test
    void testPassByReference() {
        Dog myObj = new Dog(10);

        modifyObject(myObj);

        Assertions.assertEquals(20, myObj.getAge());
    }

    @Test
    void testPassByValue() {
        final int value = 10;

        modifyObject(value);

        System.out.println(value);
        Assertions.assertEquals(10, value);
    }


}
