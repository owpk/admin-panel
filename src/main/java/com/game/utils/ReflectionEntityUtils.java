package com.game.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ReflectionEntityUtils {

    public static <T> T updateAllFields(T orig, T toUpdate) throws Exception {
            if ((orig == null || toUpdate == null) ||
                (orig.getClass() != toUpdate.getClass())) {
            throw new UnsupportedOperationException();
        }
        Field[] updated = toUpdate.getClass().getDeclaredFields();
        Map<String, Field> originFieldMap =
                Arrays.stream(orig.getClass().getDeclaredFields())
                        .collect(Collectors.toMap(Field::getName, x -> x));
        for (Field field : updated) {
            field.setAccessible(true);
            Object val = field.get(toUpdate);
            if (val != null) {
                Field updatedField = originFieldMap.get(field.getName());
                updatedField.setAccessible(true);
                updatedField.set(toUpdate, val);
            }
        }
        return toUpdate;
    }
}
