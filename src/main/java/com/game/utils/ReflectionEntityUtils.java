package com.game.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReflectionEntityUtils {

    public static <T> T updateAllFields(T orig, T newData) {
            if ((orig == null || newData == null) ||
                (orig.getClass() != newData.getClass())) {
            throw new UnsupportedOperationException();
        }
        Field[] updated = newData.getClass().getDeclaredFields();
        Map<String, Field> originFieldMap =
                Arrays.stream(orig.getClass().getDeclaredFields())
                        .collect(Collectors.toMap(Field::getName, x -> x));
        for (Field field : updated) {
            field.setAccessible(true);
            if (field.getName().equals("id")) continue;
            try {
                Object val = field.get(newData);
                if (val != null) {
                    Field updatedField = originFieldMap.get(field.getName());
                    updatedField.setAccessible(true);
                    updatedField.set(orig, val);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return orig;
    }

    public static <T> boolean isEmpty(T obj) {
        return isEmpty(obj, x -> true);
    }

    public static <T> boolean isEmpty(T obj, Predicate<Field> exclude) {
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (exclude.test(field)) {
                    field.setAccessible(true);
                    Object val = field.get(obj);
                    if (val != null) {
                        return false;
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
