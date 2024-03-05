package potato.server.fixture;

import potato.server.user.domain.User;

import java.lang.reflect.Field;

public class UserFixture {
    public static User createUser(Long id) throws IllegalAccessException, NoSuchFieldException {
        User user = User.builder().build();

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);

        return user;
    }
}
